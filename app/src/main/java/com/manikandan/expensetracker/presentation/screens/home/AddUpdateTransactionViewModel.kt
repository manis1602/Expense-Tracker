package com.manikandan.expensetracker.presentation.screens.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manikandan.expensetracker.domain.model.SingleTransaction
import com.manikandan.expensetracker.domain.model.UiEvents
import com.manikandan.expensetracker.domain.model.User
import com.manikandan.expensetracker.domain.usecases.UseCases
import com.manikandan.expensetracker.domain.validators.TransactionFormValidator
import com.manikandan.expensetracker.presentation.common.util.ExpenseDropDownType
import com.manikandan.expensetracker.presentation.screens.home.util.TransactionAddOrUpdateType
import com.manikandan.expensetracker.presentation.screens.home.util.TransactionType
import com.manikandan.expensetracker.presentation.screens.home.util.dateFormatter
import com.manikandan.expensetracker.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.lang.NumberFormatException
import javax.inject.Inject
import kotlin.Exception

@HiltViewModel
class AddUpdateTransactionViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    private val categories: Map<TransactionType, List<String>> = mapOf(
        TransactionType.INCOME to listOf(
            "Allowances/Pocket Money",
            "Gifts",
            "Investments",
            "Salary",
            "Sell",
            "Other"
        ),
        TransactionType.EXPENSE to listOf(
            "Bills & Utilities",
            "Education",
            "Food & Dining",
            "Personal Care",
            "Travel",
            "Other"
        )
    )

    val transactionModeList = listOf(
        "Cash",
        "Cheque",
        "Net Banking",
        "Credit / Debit Card",
        "UPI",
        "Other"
    )

    private val _userCredentials =
        mutableStateOf(User(userName = "", password = "", emailAddress = "", gender = ""))
    val userCredentials: State<User> = _userCredentials

    private val _addUpdateTransactionType =
        mutableStateOf(TransactionAddOrUpdateType.ADD)
    val addUpdateTransactionType: State<TransactionAddOrUpdateType> = _addUpdateTransactionType

    private val _transactionId = mutableStateOf("")
    val transactionId: State<String> = _transactionId

    private val _transactionTitle = mutableStateOf("")
    val transactionTitle: State<String> = _transactionTitle

    private val _transactionCategory = mutableStateOf("")
    val transactionCategory: State<String> = _transactionCategory

    private val _transactionDate = mutableStateOf("")
    val transactionDate: State<String> = _transactionDate

    private val _transactionDateInLong = mutableStateOf(0L)
    val transactionDateInLong: State<Long> = _transactionDateInLong

    private val _transactionAmount = mutableStateOf(0)
    val transactionAmount: State<Int> = _transactionAmount

    private val _transactionMode = mutableStateOf("")
    val transactionMode: State<String> = _transactionMode

    private val _transactionType = mutableStateOf(TransactionType.INCOME)
    val transactionType: State<TransactionType> = _transactionType

    private val _isCategoryReadOnly = mutableStateOf(true)
    val isCategoryReadOnly: State<Boolean> = _isCategoryReadOnly

    private val _isTransactionModeReadOnly = mutableStateOf<Boolean>(true)
    val isTransactionModeReadOnly: State<Boolean> = _isTransactionModeReadOnly

    private val _categoriesList = mutableStateOf(categories[TransactionType.INCOME]!!)
    val categoriesList: State<List<String>> = _categoriesList

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _eventFlow: MutableSharedFlow<UiEvents> = MutableSharedFlow()
    val eventFlow: SharedFlow<UiEvents> = _eventFlow.asSharedFlow()

    init {
        getUserDetails()
    }

    fun onEvent(event: AddUpdateTransactionEvent) {
        when (event) {
            is AddUpdateTransactionEvent.AddTransaction -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _isLoading.value = true
                    val inputValidationResponse =
                        TransactionFormValidator.addTransactionFormValidation(
                            singleTransaction = event.singleTransaction
                        )
                    if (inputValidationResponse.success) {
                        val result = useCases.addTransactionUseCase(
                            userId = event.userId,
                            singleTransaction = event.singleTransaction
                        )
                        when (result) {
                            is NetworkResult.Success -> {
                                val data = result.data!!
                                if (data.success) {
                                    onEvent(event = AddUpdateTransactionEvent.ClearAllTextFieldValues)
                                }
                                _eventFlow.emit(value = UiEvents.ShowSnackBar(message = data.message))
                                _isLoading.value = false
                            }
                            is NetworkResult.Error -> {
                                val errorData = result.errorData!!
                                _eventFlow.emit(value = UiEvents.ShowSnackBar(message = errorData.errorTitle))
                                _isLoading.value = false
                            }
                        }
                    } else {
                        _eventFlow.emit(value = UiEvents.ShowSnackBar(message = inputValidationResponse.errorMessage))
                        _isLoading.value = false
                    }

                }
            }

            is AddUpdateTransactionEvent.UpdateTransaction -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _isLoading.value = true
                    val inputValidationResponse =
                        TransactionFormValidator.addTransactionFormValidation(
                            singleTransaction = event.singleTransaction
                        )
                    if (inputValidationResponse.success) {
                        val result = useCases.updateTransactionUseCase(
                            userId = event.userId,
                            singleTransaction = event.singleTransaction
                        )
                        when (result) {
                            is NetworkResult.Success -> {
                                val data = result.data!!
                                if (data.success) {
                                    onEvent(event = AddUpdateTransactionEvent.ResetAddUpdateTransactionForm)
                                }
                                _eventFlow.emit(value = UiEvents.ShowSnackBar(message = data.message))
                                _isLoading.value = false
                            }
                            is NetworkResult.Error -> {
                                val errorData = result.errorData!!
                                _eventFlow.emit(value = UiEvents.ShowSnackBar(message = errorData.errorTitle))
                                _isLoading.value = false
                            }
                        }
                    } else {
                        _eventFlow.emit(value = UiEvents.ShowSnackBar(message = inputValidationResponse.errorMessage))
                        _isLoading.value = false
                    }

                }
            }

            is AddUpdateTransactionEvent.UpdateTransactionType -> {
                _transactionType.value = event.transactionType
            }
            is AddUpdateTransactionEvent.UpdateTextFieldValue -> {
                viewModelScope.launch {
                    try {
                        when (event.textField) {
                            "title" -> {
                                _transactionTitle.value = event.value
                            }
                            "category" -> {
                                _transactionCategory.value = event.value
                            }
                            "date" -> {
                                _transactionDate.value = event.value
                            }
                            "amount" -> {
                                _transactionAmount.value = event.value.toInt()
                            }
                            "mode" -> {
                                _transactionMode.value = event.value
                            }
                        }
                    } catch (exception: NumberFormatException) {
                        _eventFlow.emit(
                            value = UiEvents.ShowSnackBar(message = "Invalid field values!")
                        )
                    } catch (exception: Exception) {
                        _eventFlow.emit(
                            value = UiEvents.ShowSnackBar(message = exception.localizedMessage!!)
                        )
                    }
                }
            }

            is AddUpdateTransactionEvent.GetCategoriesList -> {
                _categoriesList.value = categories[event.transactionType]!!
            }

            is AddUpdateTransactionEvent.ClearAllTextFieldValues -> {
                _transactionId.value = ""
                _transactionTitle.value = ""
                _transactionCategory.value = ""
                _transactionDate.value = ""
                _transactionDateInLong.value = 0L
                _transactionAmount.value = 0
                _transactionTitle.value = ""
                _transactionMode.value = ""
                _isCategoryReadOnly.value = true
                _isTransactionModeReadOnly.value = true
            }
            is AddUpdateTransactionEvent.ResetAddUpdateTransactionForm -> {
                _transactionType.value = TransactionType.INCOME
                _categoriesList.value = categories[TransactionType.INCOME]!!
                _transactionId.value = ""
                _transactionTitle.value = ""
                _transactionCategory.value = ""
                _transactionDate.value = ""
                _transactionDateInLong.value = 0L
                _transactionAmount.value = 0
                _transactionTitle.value = ""
                _transactionMode.value = ""
                _addUpdateTransactionType.value = TransactionAddOrUpdateType.ADD
                _isCategoryReadOnly.value = true
                _isTransactionModeReadOnly.value = true
            }
        }
    }

    fun updatePickedDate(date: Long?) {
        _transactionDateInLong.value = date ?: 0L
        _transactionDate.value = dateFormatter(date = date, pattern = "dd-MMMM-yyyy") ?: ""
    }

    fun updateCategoryAndModeReadMode(dropDownType: ExpenseDropDownType, isReadOnly: Boolean) {
        when (dropDownType) {
            ExpenseDropDownType.CATEGORY -> {
                _isCategoryReadOnly.value = isReadOnly
            }
            ExpenseDropDownType.TRANSACTION_MODE -> {
                _isTransactionModeReadOnly.value = isReadOnly
            }
            else -> {}
        }
    }

    fun populateTextFields(singleTransaction: SingleTransaction) {
        _addUpdateTransactionType.value = TransactionAddOrUpdateType.UPDATE
        _transactionId.value = singleTransaction.transactionId
        _transactionTitle.value = singleTransaction.transactionTitle
        _transactionCategory.value = singleTransaction.transactionCategory
        _transactionDate.value =
            dateFormatter(date = singleTransaction.transactionDate, pattern = "dd-MMMM-yyyy") ?: ""
        _transactionDateInLong.value = singleTransaction.transactionDate
        _transactionAmount.value = singleTransaction.transactionAmount
        _transactionMode.value = singleTransaction.transactionMode
        _transactionType.value =
            if (singleTransaction.transactionIsIncome) TransactionType.INCOME else TransactionType.EXPENSE
    }

    fun generateRandomTransactionId(): String {
        val id = System.currentTimeMillis().toString()
        return id.substring(5)
    }

    private fun getUserDetails() {
        viewModelScope.launch {
            _userCredentials.value = useCases.readDataStoreUserCredentialsUseCase()
        }
    }
}