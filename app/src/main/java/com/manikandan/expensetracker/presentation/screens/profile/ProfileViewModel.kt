package com.manikandan.expensetracker.presentation.screens.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manikandan.expensetracker.R
import com.manikandan.expensetracker.domain.model.*
import com.manikandan.expensetracker.domain.usecases.UseCases
import com.manikandan.expensetracker.presentation.common.util.roundToTwoDecimalPlace
import com.manikandan.expensetracker.presentation.screens.home.util.TransactionType
import com.manikandan.expensetracker.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    private val _userCredentials =
        mutableStateOf(User(userName = "", password = "", emailAddress = "", gender = ""))
    val userCredentials: State<User> = _userCredentials

    private val _allTransactions = mutableStateOf<List<SingleTransaction>>(listOf())
    val allTransactions: State<List<SingleTransaction>> = _allTransactions

    private val _overAllStats = mutableStateOf(OverallStats())
    val overAllStats: State<OverallStats> = _overAllStats

    private val _highLowTransactions = mutableStateOf<List<HighAndLowTransaction>>(listOf())
    val highAndLowTransaction: State<List<HighAndLowTransaction>> = _highLowTransactions

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _eventFlow: MutableSharedFlow<UiEvents> = MutableSharedFlow()
    val eventFlow: SharedFlow<UiEvents> = _eventFlow.asSharedFlow()

    private val _isLogOutDialogVisible = mutableStateOf(false)
    val isLogOutDialogVisible: State<Boolean> = _isLogOutDialogVisible

    private val _errorData = mutableStateOf<ErrorData?>(
        null
    )
    val errorData: State<ErrorData?> = _errorData

    init {
        getUserDetails()
        onEvent(event = ProfileEvent.GetUsersAllTransactions(userId = _userCredentials.value.userId))
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.GetUsersAllTransactions -> {
                _isLoading.value = true
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        val result = useCases.getUsersAllTransactionsUseCase(userId = event.userId)
                        when (result) {
                            is NetworkResult.Success -> {
                                val data = result.data!!
                                data.collect {
                                    _allTransactions.value = it
                                    if (_allTransactions.value.isEmpty()) {
                                        _errorData.value = ErrorData(
                                            errorImage = R.drawable.ic_add,
                                            errorTitle = "No Transactions Found!",
                                            solutionText = "Add some transactions to see stats."
                                        )
                                    } else {
                                        getOverallStats(allTransactions = _allTransactions.value)
                                        getHighLowTransactions(allTransactions = _allTransactions.value)
                                        _errorData.value = null
                                    }
                                    delay(3000)
                                    _isLoading.value = false

                                }
                            }
                            is NetworkResult.Error -> {
                                val errorData = result.errorData!!
                                _errorData.value = errorData
                                _eventFlow.emit(value = UiEvents.ShowSnackBar(message = errorData.errorTitle))
                                _isLoading.value = false
                            }
                        }
                    } catch (exception: Exception) {
                        _eventFlow.emit(value = UiEvents.ShowSnackBar(message = exception.message!!))
                        _isLoading.value = false
                    }
                }
            }
            is ProfileEvent.LogoutUser -> {
                _isLoading.value = true
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        useCases.saveLoginStateUseCase(isLoginCompleted = false)
                        _isLogOutDialogVisible.value = false
                        _isLoading.value = false
                    } catch (exception: Exception) {
                        _eventFlow.emit(value = UiEvents.ShowSnackBar(message = exception.localizedMessage!!))
                        _isLoading.value = false
                    }
                }
            }
        }
    }

    fun updateLogOutDialogVisibility(visible: Boolean) {
        _isLogOutDialogVisible.value = visible
    }

    private fun getOverallStats(allTransactions: List<SingleTransaction>) {
        viewModelScope.launch(Dispatchers.Default) {
            _isLoading.value = true
            var totalIncome = 0f
            var totalExpense = 0f
            allTransactions.forEach { singleTransaction ->
                if (singleTransaction.transactionIsIncome) {
                    totalIncome += singleTransaction.transactionAmount
                } else {
                    totalExpense += singleTransaction.transactionAmount
                }
            }
            val profitLossPercent =
                ((totalIncome - totalExpense) / (totalIncome + totalExpense)) * 100
            val fractionalTotalIncome = totalIncome / (totalIncome + totalExpense)
            val fractionalTotalExpense = totalExpense / (totalIncome + totalExpense)

            _overAllStats.value = OverallStats(
                totalIncome = totalIncome,
                totalExpense = totalExpense,
                fractionalTotalIncome = fractionalTotalIncome.roundToTwoDecimalPlace(),
                fractionalTotalExpense = fractionalTotalExpense.roundToTwoDecimalPlace(),
                profitLossPercent = profitLossPercent.roundToTwoDecimalPlace()
            )

            _isLoading.value = false
        }
    }

    private fun getHighLowTransactions(allTransactions: List<SingleTransaction>) {
        viewModelScope.launch(Dispatchers.Default) {
            _isLoading.value = true
            val getAllIncome = allTransactions.filter {
                it.transactionIsIncome
            }
            val getAllExpense = allTransactions.filter {
                !it.transactionIsIncome
            }
            val highestIncome =
                getAllIncome.maxWithOrNull(Comparator.comparingInt { singleTransaction ->
                    singleTransaction.transactionAmount

                })
            val lowestIncome =
                getAllIncome.minWithOrNull(Comparator.comparingInt { singleTransaction ->
                    singleTransaction.transactionAmount

                })
            val highestExpense =
                getAllExpense.maxWithOrNull(Comparator.comparingInt { singleTransaction ->
                    singleTransaction.transactionAmount

                })
            val lowestExpense =
                getAllExpense.minWithOrNull(Comparator.comparingInt { singleTransaction ->
                    singleTransaction.transactionAmount

                })

            _highLowTransactions.value = listOf(
                HighAndLowTransaction(
                    transactionType = TransactionType.INCOME,
                    high = highestIncome,
                    low = lowestIncome
                ),
                HighAndLowTransaction(
                    transactionType = TransactionType.EXPENSE,
                    high = highestExpense,
                    low = lowestExpense
                )
            )
            _isLoading.value = false
        }
    }

    private fun getUserDetails() {
        viewModelScope.launch {
            _userCredentials.value = useCases.readDataStoreUserCredentialsUseCase()
        }
    }
}