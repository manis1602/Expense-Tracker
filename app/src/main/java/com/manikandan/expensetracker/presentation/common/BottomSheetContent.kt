package com.manikandan.expensetracker.presentation.common

import androidx.compose.animation.*
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.manikandan.expensetracker.domain.model.ExpenseDropDownData
import com.manikandan.expensetracker.domain.model.SingleTransaction
import com.manikandan.expensetracker.domain.model.UiEvents
import com.manikandan.expensetracker.presentation.common.util.ExpenseDropDownType
import com.manikandan.expensetracker.presentation.screens.home.AddUpdateTransactionEvent
import com.manikandan.expensetracker.presentation.screens.home.AddUpdateTransactionViewModel
import com.manikandan.expensetracker.presentation.screens.home.components.add_transaction.CustomDatePickerView
import com.manikandan.expensetracker.presentation.screens.home.components.add_transaction.IncomeExpenseRadioButton
import com.manikandan.expensetracker.presentation.screens.home.util.TransactionAddOrUpdateType
import com.manikandan.expensetracker.presentation.screens.home.util.TransactionType
import com.manikandan.expensetracker.ui.theme.*
import com.manikandan.expensetracker.utils.Constants.MAX_AMOUNT_DIGIT_COUNT
import kotlinx.coroutines.flow.collectLatest

@ExperimentalAnimationApi
@Composable
fun BottomSheetContent(
    sheetBackgroundColor: Color = MaterialTheme.colors.surface,
    addUpdateTransactionViewModel: AddUpdateTransactionViewModel
) {
    val addOrUpdateTransactionType by addUpdateTransactionViewModel.addUpdateTransactionType

    val snackBarHostState = remember {
        SnackbarHostState()
    }
    LaunchedEffect(key1 = true) {
        addUpdateTransactionViewModel.eventFlow.collectLatest { uiEvent ->
            when (uiEvent) {
                is UiEvents.ShowSnackBar -> {

                    snackBarHostState.showSnackbar(
                        message = uiEvent.message
                    )

                }
            }

        }
    }
    Box {

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
                .background(color = sheetBackgroundColor)
                .padding(vertical = LARGER_PADDING, horizontal = LARGE_PADDING),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                IncomeExpenseRadioButton(
                    addUpdateTransactionViewModel = addUpdateTransactionViewModel,
                    onValueChange = { transactionType ->
                        if (addOrUpdateTransactionType == TransactionAddOrUpdateType.ADD) {
                            addUpdateTransactionViewModel.onEvent(event = AddUpdateTransactionEvent.ClearAllTextFieldValues)
                        }
                        addUpdateTransactionViewModel.onEvent(
                            event = AddUpdateTransactionEvent.GetCategoriesList(
                                transactionType = transactionType
                            )
                        )
                    })

                IncomeExpenseForm(
//                    modifier = Modifier.weight(1f),
                    addUpdateTransactionViewModel = addUpdateTransactionViewModel,
                )
            }
        }
        SnackbarHost(
            modifier = Modifier.align(alignment = Alignment.BottomCenter),
            hostState = snackBarHostState
        ) { data ->
            Snackbar(
                snackbarData = data,
                backgroundColor = MaterialTheme.colors.snackBarBackgroundColor,
            )
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun IncomeExpenseForm(
    modifier: Modifier = Modifier,
    addUpdateTransactionViewModel: AddUpdateTransactionViewModel,
) {

    val transactionId by addUpdateTransactionViewModel.transactionId
    val transactionTitle by addUpdateTransactionViewModel.transactionTitle
    val transactionCategory by addUpdateTransactionViewModel.transactionCategory
    val transactionDate by addUpdateTransactionViewModel.transactionDate
    val transactionDateInLong by addUpdateTransactionViewModel.transactionDateInLong
    val transactionAmount by addUpdateTransactionViewModel.transactionAmount
    val transactionMode by addUpdateTransactionViewModel.transactionMode
    val transactionType by addUpdateTransactionViewModel.transactionType
    val transactionModeList = addUpdateTransactionViewModel.transactionModeList

    val addUpdateTransactionType by addUpdateTransactionViewModel.addUpdateTransactionType

    val isLoading by addUpdateTransactionViewModel.isLoading
    val user by addUpdateTransactionViewModel.userCredentials
    val categoriesList by addUpdateTransactionViewModel.categoriesList

    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = 10.dp, alignment = Alignment.Top)
    ) {

        CustomTextField(
            title = "Title",
            text = transactionTitle,
            hint = "How did you earn/spend?",
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            onTextChanged = {
                addUpdateTransactionViewModel.onEvent(
                    event = AddUpdateTransactionEvent.UpdateTextFieldValue(
                        textField = "title",
                        value = it
                    )
                )
            }
        )
        ExpenseDropDownBox(
            dropDownData = ExpenseDropDownData(
                dropDownType = ExpenseDropDownType.CATEGORY,
                dropDownList = categoriesList,
                dropDownTitle = "Category",
                hint = "Salary, Personal Care, etc",
                selectedText = transactionCategory
            ),
            scrollState = scrollState,
            valueChange = { category ->
                addUpdateTransactionViewModel.onEvent(
                    event = AddUpdateTransactionEvent.UpdateTextFieldValue(
                        textField = "category",
                        value = category
                    )
                )
            },
            addUpdateTransactionViewModel = addUpdateTransactionViewModel
        )
        CustomDatePickerView(
            title = "Date",
            pickedDate = transactionDate,
            onDateChanged = { date: Long? ->
                addUpdateTransactionViewModel.updatePickedDate(date = date)
            }
        )
        ExpenseDropDownBox(
            dropDownData = ExpenseDropDownData(
                dropDownType = ExpenseDropDownType.TRANSACTION_MODE,
                dropDownList = transactionModeList,
                dropDownTitle = "Mode",
                hint = "Cash",
                selectedText = transactionMode
            ),
            scrollState = scrollState,
            valueChange = { mode ->
                addUpdateTransactionViewModel.onEvent(
                    event = AddUpdateTransactionEvent.UpdateTextFieldValue(
                        textField = "mode",
                        value = mode
                    )
                )
            },
            addUpdateTransactionViewModel = addUpdateTransactionViewModel
        )
        CustomTextField(
            title = "Amount",
            text = if (transactionAmount == 0) "" else transactionAmount.toString(),
            hint = "RS.20000",
            keyboardActions = KeyboardActions(onNext = {
                focusManager.clearFocus()
            }),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            onTextChanged = { amount ->
                if (amount.length <= MAX_AMOUNT_DIGIT_COUNT) {
                    addUpdateTransactionViewModel.onEvent(
                        event = AddUpdateTransactionEvent.UpdateTextFieldValue(
                            textField = "amount",
                            value = amount.ifEmpty {
                                "0"
                            }
                        )
                    )
                }
            }
        )

        AnimatedContent(
            targetState = isLoading,
            contentAlignment = Alignment.Center,
            transitionSpec = {
                fadeIn(animationSpec = tween(150, 150)) with
                        fadeOut(animationSpec = tween(150)) using
                        SizeTransform { initialSize, targetSize ->
                            if (targetState) {
                                keyframes {
                                    // Expand horizontally first.
                                    IntSize(targetSize.width, targetSize.height) at 150
                                    durationMillis = 300
                                }
                            } else {
                                keyframes {
                                    // Shrink vertically first.
                                    IntSize(initialSize.width, initialSize.height) at 150
                                    durationMillis = 300
                                }
                            }
                        }
            }
        ) { isLoading ->
            if (isLoading) {
                CustomCircularBar()
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.buttonColor,
                            contentColor = MaterialTheme.colors.buttonContentColor
                        ),
                        onClick = {
                            val randomTransactionId =
                                addUpdateTransactionViewModel.generateRandomTransactionId()
                            val singleTransaction = SingleTransaction(
                                transactionId = if (transactionId == "") randomTransactionId else transactionId,
                                transactionTitle = transactionTitle,
                                transactionCategory = transactionCategory,
                                transactionIsIncome = transactionType == TransactionType.INCOME,
                                transactionAmount = transactionAmount,
                                transactionDate = transactionDateInLong,
                                transactionMode = transactionMode
                            )
                            val event =
                                if (addUpdateTransactionType == TransactionAddOrUpdateType.ADD) {
                                    AddUpdateTransactionEvent.AddTransaction(
                                        userId = user.userId,
                                        singleTransaction = singleTransaction
                                    )
                                } else {
                                    AddUpdateTransactionEvent.UpdateTransaction(
                                        userId = user.userId,
                                        singleTransaction = singleTransaction
                                    )
                                }
                            addUpdateTransactionViewModel.onEvent(
                                event = event
                            )
                        }
                    ) {

                        Text(
                            text = if (addUpdateTransactionType == TransactionAddOrUpdateType.ADD) "ADD" else "UPDATE",
                        )
                    }
                    if (addUpdateTransactionType == TransactionAddOrUpdateType.ADD) {
                        Spacer(modifier = Modifier.width(10.dp))
                        Button(
                            onClick = {
                                addUpdateTransactionViewModel.onEvent(event = AddUpdateTransactionEvent.ClearAllTextFieldValues)
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.snackBarBackgroundColor
                            ),
                        ) {
                            Icon(
                                modifier = Modifier.size(30.dp),
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = MaterialTheme.colors.screenBackgroundColor
                            )
                        }
                    }
                }
            }

        }

    }
}