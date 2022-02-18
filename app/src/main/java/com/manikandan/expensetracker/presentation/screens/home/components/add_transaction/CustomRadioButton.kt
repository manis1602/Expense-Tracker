package com.manikandan.expensetracker.presentation.screens.home.components.add_transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.manikandan.expensetracker.presentation.screens.home.AddUpdateTransactionEvent
import com.manikandan.expensetracker.presentation.screens.home.AddUpdateTransactionViewModel
import com.manikandan.expensetracker.presentation.screens.home.util.TransactionType
import com.manikandan.expensetracker.ui.theme.Green
import com.manikandan.expensetracker.ui.theme.Red
import com.manikandan.expensetracker.ui.theme.titleColor

@Composable
fun CustomRadioButton(
    modifier: Modifier = Modifier,
    addUpdateTransactionViewModel: AddUpdateTransactionViewModel,
    onValueChange: (TransactionType) -> Unit
) {
    val transactionType by addUpdateTransactionViewModel.transactionType
    Box(
        modifier = modifier
            .size(width = 40.dp, height = 28.dp)
            .border(width = 2.dp, color = MaterialTheme.colors.titleColor, shape = CircleShape)
            .clip(CircleShape)
            .padding(horizontal = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .align(alignment = if (transactionType == TransactionType.INCOME) Alignment.CenterStart else Alignment.CenterEnd)
                .size(20.dp)
                .background(color = if (transactionType == TransactionType.INCOME) Green else Red)
                .clickable {
                    if (transactionType == TransactionType.INCOME) {
                        addUpdateTransactionViewModel.onEvent(
                            event = AddUpdateTransactionEvent.UpdateTransactionType(
                                transactionType = TransactionType.EXPENSE
                            )
                        )
                        addUpdateTransactionViewModel.onEvent(
                            event = AddUpdateTransactionEvent.GetCategoriesList(
                                TransactionType.EXPENSE
                            )
                        )
                    } else {
                        addUpdateTransactionViewModel.onEvent(
                            event = AddUpdateTransactionEvent.UpdateTransactionType(
                                transactionType = TransactionType.INCOME
                            )
                        )
                        addUpdateTransactionViewModel.onEvent(
                            event = AddUpdateTransactionEvent.GetCategoriesList(
                                TransactionType.INCOME
                            )
                        )
                    }
                    onValueChange(transactionType)
                }

        )
    }
}