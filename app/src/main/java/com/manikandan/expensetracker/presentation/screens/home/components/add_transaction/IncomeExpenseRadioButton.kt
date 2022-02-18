package com.manikandan.expensetracker.presentation.screens.home.components.add_transaction

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.manikandan.expensetracker.presentation.screens.home.AddUpdateTransactionViewModel
import com.manikandan.expensetracker.presentation.screens.home.util.TransactionType
import com.manikandan.expensetracker.ui.theme.LARGER_PADDING
import com.manikandan.expensetracker.ui.theme.LARGE_PADDING
import com.manikandan.expensetracker.ui.theme.MediumGray
import com.manikandan.expensetracker.ui.theme.titleColor

@Composable
fun IncomeExpenseRadioButton(
    addUpdateTransactionViewModel: AddUpdateTransactionViewModel,
    onValueChange: (TransactionType) -> Unit
) {
    Row(
        modifier = Modifier.padding(bottom = LARGER_PADDING),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val transactionType by addUpdateTransactionViewModel.transactionType

        Text(
            text = "Income",
            fontSize = 24.sp,
            color = if (transactionType == TransactionType.INCOME) MaterialTheme.colors.titleColor else MediumGray,
            fontWeight = if (transactionType == TransactionType.INCOME) FontWeight.Bold else FontWeight.Normal
        )
        CustomRadioButton(
            modifier = Modifier.padding(horizontal = LARGE_PADDING),
            addUpdateTransactionViewModel = addUpdateTransactionViewModel,
            onValueChange = { type ->
                onValueChange(type)
            })
        Text(
            text = "Expense",
            fontSize = 24.sp,
            color = if (transactionType == TransactionType.EXPENSE) MaterialTheme.colors.titleColor else MediumGray,
            fontWeight = if (transactionType == TransactionType.EXPENSE) FontWeight.Bold else FontWeight.Normal
        )
    }
}