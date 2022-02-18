package com.manikandan.expensetracker.presentation.screens.profile.components.profile_sections.stats_section

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.manikandan.expensetracker.domain.model.OverallStats
import com.manikandan.expensetracker.ui.theme.*

@Composable
fun ProfileStatsOverallSection(overallStats: OverallStats) {
    Text(
        text = "STATS",
        fontSize = MaterialTheme.typography.h6.fontSize,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colors.titleColor
    )
    Text(
        modifier = Modifier.padding(vertical = SMALL_PADDING),
        text = "Overall",
        fontSize = MaterialTheme.typography.body1.fontSize,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colors.titleColor
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = SMALL_PADDING),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OverallStatsLabelAndValue(label = "Income", value = overallStats.totalIncome)
        OverAllStatsBarGraph(
            modifier = Modifier
                .padding(horizontal = MEDIUM_PADDING)
                .weight(weight = 1f),
            fractionalIncome = overallStats.fractionalTotalIncome
        )
        OverallStatsLabelAndValue(label = "Expense", value = overallStats.totalExpense)

    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = SMALL_PADDING), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Income/Expense %",
            fontSize = MaterialTheme.typography.body1.fontSize,
            color = MaterialTheme.colors.titleColor
        )
        Text(
            text = "${overallStats.profitLossPercent} %",
            fontSize = MaterialTheme.typography.body1.fontSize,
            color = if (overallStats.profitLossPercent >= 0)
                MaterialTheme.colors.incomeAmountTextColor
            else
                MaterialTheme.colors.expenseAmountTextColor
        )
    }
}

@Composable
fun OverAllStatsBarGraph(
    modifier: Modifier = Modifier,
    fractionalIncome: Float,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(height = 30.dp)
            .clip(shape = RoundedCornerShape(size = EXTRA_SMALL_PADDING))
            .background(color = Amber)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(fractionalIncome)
                .fillMaxHeight()
                .background(
                    color = MaterialTheme.colors.overallStatsIncomeBarColor
                )
        )
    }
}

@Composable
fun OverallStatsLabelAndValue(label: String, value: Float) {
    Column(modifier = Modifier.width(intrinsicSize = IntrinsicSize.Min)) {
        Text(
            text = label,
            fontSize = MaterialTheme.typography.body1.fontSize,
            color = MaterialTheme.colors.titleColor
        )
        Text(
            text = "Rs. ${value.toInt()}",
            fontSize = MaterialTheme.typography.caption.fontSize,
            color = MediumGray,
        )
    }

}