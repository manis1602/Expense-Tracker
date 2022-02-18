package com.manikandan.expensetracker.presentation.screens.home.components.home_screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.manikandan.expensetracker.R
import com.manikandan.expensetracker.presentation.screens.home.HomeViewModel
import com.manikandan.expensetracker.ui.theme.*
import java.util.*

@Composable
fun ExpenseBarGraph(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel
) {
    val barHeights by homeViewModel.expenseGraphBarHeights
    val yearList by homeViewModel.yearList
    val calendar = Calendar.getInstance()
    Box(
        modifier = modifier
            .padding(vertical = LARGE_PADDING)
            .fillMaxSize()
            .background(
                color = MaterialTheme.colors.buttonColor,
                shape = RoundedCornerShape(
                    SMALL_PADDING
                )
            )
            .padding(start = MEDIUM_PADDING, end = MEDIUM_PADDING, bottom = EXTRA_SMALL_PADDING)
    ) {
        if (yearList.isNotEmpty()) {
            Column(modifier = Modifier.fillMaxSize()) {
                ChangeableYear(
                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                    homeViewModel = homeViewModel
                )
                LazyRow {
                    barHeights.forEach { (month, barHeights) ->
                        item {
                            SingleMonthIncomeExpenseBar(
                                month = month,
                                incomeExpenseBarHeight = barHeights
                            )
                            Spacer(modifier = Modifier.width(SMALL_PADDING))
                        }
                    }
                }
//                Row(modifier = Modifier.fillMaxSize()) {
//                    barHeights.forEach { (month, barHeights) ->
//                        SingleMonthIncomeExpenseBar(
//                            month = month,
//                            incomeExpenseBarHeight = barHeights
//                        )
//                        Spacer(modifier = Modifier.width(SMALL_PADDING))
//                    }
//                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier.size(50.dp),
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = stringResource(R.string.empty_screen_add),
                    tint = White90
                )
                Spacer(modifier = Modifier.height(MEDIUM_PADDING))
                Text(
                    text = "No Transactions Added in ${calendar.get(Calendar.YEAR)}",
                    fontSize = MaterialTheme.typography.body1.fontSize,
                    fontWeight = FontWeight.Bold,
                    color = White90,
                )
            }
        }
    }
}

@Composable
fun ChangeableYear(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel
) {

    val selectedYear by homeViewModel.selectedYear

    Row(
        modifier = modifier
            .padding(vertical = SMALL_PADDING)
            .fillMaxWidth(0.5f),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            modifier = Modifier
                .size(MEDIUM_PADDING)
                .clickable {
                    homeViewModel.updateExpenseBarGraphData(isNext = false)
                },
            painter = painterResource(id = R.drawable.ic_previous),
            contentDescription = "",
            tint = White90
        )

        Text(
            text = selectedYear.toString(),
            fontSize = MaterialTheme.typography.body1.fontSize,
            fontWeight = FontWeight.Bold,
            color = White90
        )
        Icon(
            modifier = Modifier
                .size(MEDIUM_PADDING)
                .clickable {
                    homeViewModel.updateExpenseBarGraphData(isNext = true)
                },
            painter = painterResource(id = R.drawable.ic_next),
            contentDescription = "",
            tint = White90
        )
    }
}


@Composable
fun SingleMonthIncomeExpenseBar(month: String, incomeExpenseBarHeight: List<Float>) {
    val animatedIncomeBarHeight by animateFloatAsState(
        targetValue = incomeExpenseBarHeight[0],
        animationSpec = tween(500)
    )
    val animatedExpenseBarHeight by animateFloatAsState(
        targetValue = incomeExpenseBarHeight[1],
        animationSpec = tween(500)
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.Bottom
        ) {
            Box(
                modifier = Modifier
                    .width(30.dp)
                    .fillMaxHeight(animatedIncomeBarHeight)
                    .background(shape = RoundedCornerShape(10.dp), color = SemiDarkPurple)
            )
            Spacer(modifier = Modifier.width(EXTRA_SMALL_PADDING))
            Box(
                modifier = Modifier
                    .width(30.dp)
                    .fillMaxHeight(animatedExpenseBarHeight)
                    .background(shape = RoundedCornerShape(10.dp), color = Amber)
            )
        }
        Text(
            text = month,
            color = White90
        )
    }
}