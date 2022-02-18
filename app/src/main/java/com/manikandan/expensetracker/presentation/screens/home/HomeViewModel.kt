package com.manikandan.expensetracker.presentation.screens.home

import android.icu.util.Calendar
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manikandan.expensetracker.R
import com.manikandan.expensetracker.domain.model.ErrorData
import com.manikandan.expensetracker.domain.model.SingleTransaction
import com.manikandan.expensetracker.domain.model.UiEvents
import com.manikandan.expensetracker.domain.model.User
import com.manikandan.expensetracker.domain.usecases.UseCases
import com.manikandan.expensetracker.presentation.common.util.roundToTwoDecimalPlace
import com.manikandan.expensetracker.presentation.screens.home.util.DateComparator
import com.manikandan.expensetracker.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    private val calendar = Calendar.getInstance()
    private val month = mapOf(
        0 to "Jan",
        1 to "Feb",
        2 to "Mar",
        3 to "Apr",
        4 to "May",
        5 to "Jun",
        6 to "Jul",
        7 to "Aug",
        8 to "Sep",
        9 to "Oct",
        10 to "Nov",
        11 to "Dec"
    )

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _eventFlow: MutableSharedFlow<UiEvents> = MutableSharedFlow()
    val eventFlow: SharedFlow<UiEvents> = _eventFlow.asSharedFlow()

    private val _allTransactions = mutableStateOf<List<SingleTransaction>>(listOf())
    val allTransactions: State<List<SingleTransaction>> = _allTransactions

    private val _allMonthWiseTransactionsInAYear =
        mutableStateOf<Map<Int, Map<String, List<SingleTransaction>>>>(
            mapOf()
        )
    val allMonthWiseTransactionsInAYear: State<Map<Int, Map<String, List<SingleTransaction>>>> =
        _allMonthWiseTransactionsInAYear

    private val _expenseGraphBarHeights = mutableStateOf<Map<String, List<Float>>>(mapOf())
    val expenseGraphBarHeights: State<Map<String, List<Float>>> = _expenseGraphBarHeights

    private val _selectedYear = mutableStateOf(0)
    val selectedYear: State<Int> = _selectedYear

    private val _yearList = mutableStateOf<List<Int>>(listOf())
    val yearList: State<List<Int>> = _yearList

    private val _index = mutableStateOf(0)


    private val _userCredentials =
        mutableStateOf(User(userName = "", password = "", emailAddress = "", gender = ""))
    val userCredentials: State<User> = _userCredentials

    private val _errorData = mutableStateOf<ErrorData?>(
        null
    )
    val errorData: State<ErrorData?> = _errorData


    init {
        getUserDetails()
        onEvent(event = HomeEvent.GetUsersAllTransactions(userId = _userCredentials.value.userId))
    }


    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.GetUsersAllTransactions -> {
                _isLoading.value = true
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        val result = useCases.getUsersAllTransactionsUseCase(userId = event.userId)
                        when (result) {
                            is NetworkResult.Success -> {
                                val data = result.data!!
                                data.collect { singleTransactions ->
                                    if (singleTransactions.isEmpty()) {
                                        _errorData.value = ErrorData(
                                            errorImage = R.drawable.ic_add,
                                            errorTitle = "No Transactions Found!",
                                            solutionText = "Click \"+\" to add new"
                                        )
                                    } else {
                                        _allTransactions.value = singleTransactions
                                        getGroupedTransactions(singleTransactions)

                                        // Update year list and selected year before getting the bar graph of the selected year.
                                        _yearList.value =
                                            getYearList(allTransactions = singleTransactions)
                                        _index.value = 0
                                        _selectedYear.value = _yearList.value[_index.value]

                                        // Get selected years bar graph.
                                        getCurrentYearMonthWiseTransactionBarHeights(_selectedYear.value)
                                        _errorData.value = null
                                    }
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
                        _isLoading.value = false
                        _eventFlow.emit(value = UiEvents.ShowSnackBar(message = exception.message!!))
                    }
                }
            }
            is HomeEvent.DeleteTransaction -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _isLoading.value = false
                    val result =
                        useCases.deleteTransactionUseCase(deleteTransactionDetail = event.deleteTransactionDetail)
                    when (result) {
                        is NetworkResult.Success -> {
                            val data = result.data!!
                            _eventFlow.emit(value = UiEvents.ShowSnackBar(message = data.message))
                            _isLoading.value = false
                        }
                        is NetworkResult.Error -> {
                            val errorData = result.errorData!!
                            _eventFlow.emit(value = UiEvents.ShowSnackBar(message = errorData.errorTitle))
                            _isLoading.value = false
                        }
                    }

                }
            }

            is HomeEvent.UpdateSelectedYear -> {
                _selectedYear.value = event.year
                getCurrentYearMonthWiseTransactionBarHeights(_selectedYear.value)
            }

        }

    }

    fun updateExpenseBarGraphData(isNext: Boolean) {
        if (isNext) {
            if (_index.value > 0) {
                //Since yearList has recent year (ie) in descending order,
                // so decreasing the index value when next is pressed.
                _index.value -= 1
            }
        } else {
            if (_index.value < _yearList.value.size - 1) {
                _index.value += 1
            }
        }
        _selectedYear.value = _yearList.value[_index.value]
        getCurrentYearMonthWiseTransactionBarHeights(year = _selectedYear.value)
    }

    fun getCategoryImage(category: String): Int {
        return when (category) {
            "Allowances/Pocket Money" -> R.drawable.allowances
            "Gifts" -> R.drawable.gift
            "Investments" -> R.drawable.investments
            "Salary" -> R.drawable.salary
            "Sell" -> R.drawable.sell
            "Bills & Utilities" -> R.drawable.bill
            "Education" -> R.drawable.education
            "Food & Dining" -> R.drawable.food
            "Personal Care" -> R.drawable.personal_care
            "Travel" -> R.drawable.travel
            else -> R.drawable.other
        }
    }

    private fun getGroupedTransactions(transactions: List<SingleTransaction>) {
        val sortedTransaction = transactions.sortedWith(DateComparator())
        val groupByYear = sortedTransaction.groupBy { transaction ->
            calendar.timeInMillis = transaction.transactionDate
            calendar.get(Calendar.YEAR)
        }
        val yearMonthWiseTransaction: MutableMap<Int, Map<String, List<SingleTransaction>>> =
            mutableMapOf()
        groupByYear.forEach { (year, monthTransactions) ->
            val result = monthTransactions.groupBy { transaction ->
                calendar.timeInMillis = transaction.transactionDate
                calendar.get(Calendar.MONTH)
            }.mapKeys {
                month[it.key] ?: "Unknown"
            }
            yearMonthWiseTransaction[year] = result
        }
        _allMonthWiseTransactionsInAYear.value = yearMonthWiseTransaction
    }

    private fun getYearList(allTransactions: List<SingleTransaction>): List<Int> {
        val sortedTransaction = allTransactions.sortedWith(DateComparator())
        val groupByYear = sortedTransaction.groupBy { transaction ->
            calendar.timeInMillis = transaction.transactionDate
            calendar.get(Calendar.YEAR)
        }
        return groupByYear.keys.toList()
    }

    private fun getUserDetails() {
        viewModelScope.launch {
            _userCredentials.value = useCases.readDataStoreUserCredentialsUseCase()
        }
    }

    private fun getCurrentYearMonthWiseTransactionBarHeights(year: Int) {
        viewModelScope.launch(Dispatchers.Default) {
            val sortedAllTransactions =
                this@HomeViewModel._allTransactions.value.sortedWith(comparator = DateComparator())
            val allExpensesInASpecificYear = allExpensesInASpecificYear(
                year = year,
                transactionsList = sortedAllTransactions
            )
            val allMonthWiseTransactionsInAYear = allMonthWiseTransactionsInAYear(
                transactionsList = allExpensesInASpecificYear
            )
            _expenseGraphBarHeights.value = expenseGraphMonthWiseTransactionsBarHeight(
                monthWiseTransactions = allMonthWiseTransactionsInAYear
            )
        }

    }

    private fun allExpensesInASpecificYear(
        year: Int,
        transactionsList: List<SingleTransaction>
    ): List<SingleTransaction> {
        val calendar = Calendar.getInstance()
        val listOfExpenses = transactionsList.filter { expense ->
            calendar.timeInMillis = expense.transactionDate
            calendar.get(Calendar.YEAR) == year
        }
        return listOfExpenses
    }

    private fun allMonthWiseTransactionsInAYear(transactionsList: List<SingleTransaction>): Map<String, List<SingleTransaction>> {
        val calendar = java.util.Calendar.getInstance()
        val result = transactionsList.groupBy {
            calendar.timeInMillis = it.transactionDate
            calendar.get(java.util.Calendar.MONTH)
        }.mapKeys {
            month[it.key] ?: "Unknown"
        }
        return result
    }

    private fun expenseGraphMonthWiseTransactionsBarHeight(monthWiseTransactions: Map<String, List<SingleTransaction>>): Map<String, List<Float>> {
        val result: MutableMap<String, List<Float>> = mutableMapOf()
        monthWiseTransactions.forEach {
            var income = 0f
            var expense = 0f
            it.value.forEach { singleTransaction ->
                if (singleTransaction.transactionIsIncome) {
                    income += singleTransaction.transactionAmount
                } else {
                    expense += singleTransaction.transactionAmount
                }
            }
            val incomeHeight = income / (income + expense)
            val expenseHeight = expense / (income + expense)
            result[it.key] = listOf(
                incomeHeight.roundToTwoDecimalPlace(),
                expenseHeight.roundToTwoDecimalPlace()
            )
        }
        return result
    }

}

