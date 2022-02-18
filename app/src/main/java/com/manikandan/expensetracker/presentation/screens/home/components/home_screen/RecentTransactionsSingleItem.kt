package com.manikandan.expensetracker.presentation.screens.home.components.home_screen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.manikandan.expensetracker.R
import com.manikandan.expensetracker.domain.model.SingleTransaction
import com.manikandan.expensetracker.presentation.screens.home.util.dateFormatter
import com.manikandan.expensetracker.ui.theme.*

@Composable
fun RecentTransactionsSingleItem(
    @DrawableRes categoryImage: Int,
    singleTransaction: SingleTransaction,
    onItemClicked: (SingleTransaction) -> Unit
) {
    val month = dateFormatter(date = singleTransaction.transactionDate, pattern = "MMM") ?: "404"
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = MEDIUM_PADDING, horizontal = EXTRA_SMALL_PADDING)
            .clickable {
                onItemClicked(singleTransaction)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        Image(
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(SMALL_PADDING)),
            painter = painterResource(id = categoryImage),
            contentDescription = stringResource(
                R.string.category_image
            )
        )
        Column(
            modifier = Modifier
                .weight(3f)
                .padding(horizontal = LARGE_PADDING)
        ) {
            Text(
                text = singleTransaction.transactionTitle,
                fontSize = MaterialTheme.typography.body1.fontSize,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.titleColor
            )
            Text(
                text = singleTransaction.transactionCategory,
                fontSize = MaterialTheme.typography.subtitle2.fontSize,
                color = MediumGray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Box(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colors.recentTransactionMonthBackgroundColor,
                    shape = CircleShape
                )
                .padding(horizontal = SMALL_PADDING),
            contentAlignment = Alignment.Center
        ) {
            Text(text = month, color = White90)
        }
        Text(
            modifier = Modifier.weight(2f),
            text = "Rs.${singleTransaction.transactionAmount}",
            fontWeight = FontWeight.Bold,
            color = if (singleTransaction.transactionIsIncome)
                MaterialTheme.colors.incomeAmountTextColor
            else
                MaterialTheme.colors.expenseAmountTextColor,
            textAlign = TextAlign.End
        )
    }
}