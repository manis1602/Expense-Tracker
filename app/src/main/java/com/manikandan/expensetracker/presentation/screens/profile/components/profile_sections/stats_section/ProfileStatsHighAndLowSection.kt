package com.manikandan.expensetracker.presentation.screens.profile.components.profile_sections.stats_section

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.manikandan.expensetracker.domain.model.HighAndLowTransaction
import com.manikandan.expensetracker.presentation.screens.home.util.TransactionType
import com.manikandan.expensetracker.presentation.screens.home.util.dateFormatter
import com.manikandan.expensetracker.ui.theme.*

@Composable
fun ProfileStatsHighAndLowSection(highAndLowTransactions: List<HighAndLowTransaction>) {

    Column {

        Text(
            modifier = Modifier.padding(bottom = SMALL_PADDING),
            text = "High and Low",
            fontSize = MaterialTheme.typography.body1.fontSize,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.titleColor
        )
        ProfileStatsHighAndLowLayout(highAndLowTransactions = highAndLowTransactions)
    }

}

@Composable
fun ProfileStatsHighAndLowLayout(highAndLowTransactions: List<HighAndLowTransaction>) {
    val rowDivider: @Composable () -> Unit = {
        Divider(thickness = 0.5.dp, color = MediumGray)
    }

    val columnDivider: @Composable () -> Unit = {
        Divider(
            color = MediumGray,
            modifier = Modifier
                .fillMaxHeight()
                .width(0.5.dp)
        )
    }
    LazyColumn {

        item {
            rowDivider()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(intrinsicSize = IntrinsicSize.Min)
            ) {
                columnDivider()

                TableHeaderText(value = "Type")

                columnDivider()

                TableHeaderText(value = "High")

                columnDivider()

                TableHeaderText(value = "Low")

                columnDivider()
            }
            rowDivider()
        }
        items(highAndLowTransactions) { highAndLowTransaction ->

            val highTransactionMonth =
                dateFormatter(
                    date = highAndLowTransaction.high?.transactionDate,
                    pattern = "MMM-yyyy"
                ) ?: "-"
            val highTransactionAmount = highAndLowTransaction.high?.transactionAmount?.let {
                "Rs. $it"
            } ?: "-"

            val lowTransactionMonth =
                dateFormatter(
                    date = highAndLowTransaction.low?.transactionDate,
                    pattern = "MMM-yyyy"
                ) ?: "-"
            val lowTransactionAmount = highAndLowTransaction.low?.transactionAmount?.let {
                "Rs. $it"
            } ?: "-"

            Row(modifier = Modifier.height(intrinsicSize = IntrinsicSize.Max)) {
                columnDivider()

                TableCell {
                    Text(
                        text = if (highAndLowTransaction.transactionType == TransactionType.INCOME) "Income" else "Expense",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.titleColor,
                        textAlign = TextAlign.Center
                    )

                }

                columnDivider()

                TableCell {
                    TableDataText(
                        month = highTransactionMonth,
                        amount = highTransactionAmount,
                        transactionType = highAndLowTransaction.transactionType
                    )
                }

                columnDivider()

                TableCell {
                    TableDataText(
                        month = lowTransactionMonth,
                        amount = lowTransactionAmount,
                        transactionType = highAndLowTransaction.transactionType
                    )
                }

                columnDivider()
            }
            rowDivider()
        }
    }
}


@Composable
fun RowScope.TableHeaderText(value: String) {
    Text(
        modifier = Modifier
            .weight(1f),
        text = value,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colors.titleColor,
        textAlign = TextAlign.Center
    )
}

@Composable
fun TableDataText(month: String, amount: String, transactionType: TransactionType) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = month,
            color = MaterialTheme.colors.titleColor,
            fontSize = MaterialTheme.typography.body2.fontSize
        )
        Text(
            text = amount,
            color = if (transactionType == TransactionType.INCOME) MaterialTheme.colors.incomeAmountTextColor else MaterialTheme.colors.expenseAmountTextColor,
            fontSize = MaterialTheme.typography.body2.fontSize
        )
    }
}

@Composable
fun RowScope.TableCell(
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .weight(weight = 1f)
            .height(50.dp),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}