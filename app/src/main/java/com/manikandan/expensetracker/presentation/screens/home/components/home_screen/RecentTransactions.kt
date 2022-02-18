package com.manikandan.expensetracker.presentation.screens.home.components.home_screen

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.manikandan.expensetracker.domain.model.SingleTransaction
import com.manikandan.expensetracker.presentation.common.SwipeToDismissListItem
import com.manikandan.expensetracker.presentation.screens.home.AddUpdateTransactionEvent
import com.manikandan.expensetracker.presentation.screens.home.HomeViewModel
import com.manikandan.expensetracker.ui.theme.MEDIUM_PADDING
import com.manikandan.expensetracker.ui.theme.SMALL_PADDING
import com.manikandan.expensetracker.ui.theme.titleColor

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun RecentTransactions(
    modifier: Modifier = Modifier,
    recentTransaction: List<SingleTransaction>,
    onViewAllClicked: () -> Unit,
    onItemClicked: (SingleTransaction) -> Unit,
    homeViewModel: HomeViewModel,
    onDismissed:(transactionId: String) -> Unit
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "TRANSACTIONS",
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.h5.fontSize,
                color = MaterialTheme.colors.titleColor
            )

            ViewAllButton(onViewAllClicked = onViewAllClicked)
        }
        LazyColumn {
            items(items = recentTransaction.take(5), key = { item: SingleTransaction ->
                item.transactionId
            }) { singleTransaction ->
                SwipeToDismissListItem(
                    singleTransaction = singleTransaction,
                    homeViewModel = homeViewModel,
                    onItemClicked = { selectedSingleTransaction ->
                        onItemClicked(selectedSingleTransaction)
                    },
                    onDismissed = {
                        onDismissed(singleTransaction.transactionId)
                    }
                )
            }
        }
    }
}