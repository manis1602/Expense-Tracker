package com.manikandan.expensetracker.presentation.common

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.manikandan.expensetracker.R
import com.manikandan.expensetracker.domain.model.SingleTransaction
import com.manikandan.expensetracker.presentation.screens.home.HomeViewModel
import com.manikandan.expensetracker.presentation.screens.home.components.home_screen.RecentTransactionsSingleItem
import com.manikandan.expensetracker.ui.theme.*

@ExperimentalMaterialApi
@Composable
fun SwipeToDismissListItem(
    singleTransaction: SingleTransaction,
    homeViewModel: HomeViewModel,
    onItemClicked: (SingleTransaction) -> Unit,
    onDismissed: () -> Unit
) {
    val dismissState =
        rememberDismissState(confirmStateChange = { dismissValue ->
            if (dismissValue == DismissValue.DismissedToStart) {
                onDismissed()
            }
            dismissValue != DismissValue.DismissedToStart
        }
        )
    SwipeToDismiss(
        state = dismissState,
        directions = setOf(DismissDirection.EndToStart),
        dismissThresholds = {
            FractionalThreshold(0.3f)
        },
        background = {
            val dismissDirection = dismissState.dismissDirection
                ?: return@SwipeToDismiss

            val backgroundColor = when (dismissState.targetValue) {
                DismissValue.DismissedToStart -> MaterialTheme.colors.expenseAmountTextColor
                else -> MediumGray
            }
            /*
                If swipe in both direction is enabled, use when statement to select a icon,
                by passing dismiss direction as parameter to when statement.
             */
            val icon = Icons.Default.Delete
            val scaleIcon by
            animateFloatAsState(targetValue = if (dismissState.targetValue == DismissValue.Default) 0.0f else 1.2f)
            /*
                If swipe in both direction is enabled, use when statement to select a alignment,
                by passing dismiss direction as parameter to when statement.
                For startToEnd -> Alignment.CenterStart
             */
            val iconAlignment = Alignment.CenterEnd
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        backgroundColor, shape = RoundedCornerShape(
                            SMALL_PADDING
                        )
                    )
                    .padding(horizontal = MEDIUM_PADDING),
                contentAlignment = iconAlignment
            ) {
                Icon(
                    modifier = Modifier.scale(scaleIcon),
                    imageVector = icon,
                    contentDescription = stringResource(R.string.delete_icon),
                    tint = White90
                )
            }
        }
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = animateDpAsState(
                targetValue = if (dismissState.dismissDirection != null) EXTRA_SMALL_PADDING else 0.dp
            ).value,
            shape = RoundedCornerShape(SMALL_PADDING)
        ) {
            Column {
                RecentTransactionsSingleItem(
                    singleTransaction = singleTransaction,
                    onItemClicked = {
                        onItemClicked(it)
                    },
                    categoryImage = homeViewModel.getCategoryImage(
                        category = singleTransaction.transactionCategory
                    )
                )
                Divider(
                    color = MediumGray,
                    thickness = 0.5.dp
                )
            }
        }
    }
}