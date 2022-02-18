package com.manikandan.expensetracker.presentation.screens.allTransactions.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import com.manikandan.expensetracker.ui.theme.*
import com.manikandan.expensetracker.utils.Constants.ALL_TRANSACTIONS_PLACEHOLDER_ITEM_COUNT

@Composable
fun AllTransactionsShimmerEffect() {
    val transition = rememberInfiniteTransition()
    val alphaAnim by transition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 500,
                easing = FastOutLinearInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    AllTransactionsShimmerEffectDesign(alpha = alphaAnim)
}

@Composable
fun AllTransactionsShimmerEffectDesign(
    alpha: Float
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Surface(
            modifier = Modifier
                .padding(vertical = LARGE_PADDING)
                .fillMaxWidth(0.5f)
                .height(TRANSACTION_TITLE_PLACE_HOLDER_HEIGHT)
                .alpha(alpha = alpha),
            color = MaterialTheme.colors.shimmerContentColor,
        ) {}
        Surface(
            modifier = Modifier
                .padding(bottom = SMALL_PADDING)
                .fillMaxWidth(0.15f)
                .height(TRANSACTION_MONTH_YEAR_PLACE_HOLDER_HEIGHT)
                .alpha(alpha = alpha),
            color = MaterialTheme.colors.shimmerContentColor,
            shape = CircleShape
        ) {}
        Surface(
            modifier = Modifier
                .padding(bottom = SMALL_PADDING)
                .fillMaxWidth(0.15f)
                .height(TRANSACTION_MONTH_YEAR_PLACE_HOLDER_HEIGHT)
                .alpha(alpha = alpha),
            color = MaterialTheme.colors.shimmerContentColor,
            shape = CircleShape
        ) {}
        LazyColumn {
            items(ALL_TRANSACTIONS_PLACEHOLDER_ITEM_COUNT) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(RECENT_TRANSACTIONS_SINGLE_ITEM_PLACE_HOLDER_HEIGHT)
                        .alpha(alpha = alpha),
                    color = MaterialTheme.colors.shimmerContentColor,
                    shape = RoundedCornerShape(size = SMALL_PADDING)
                ) {}
                Spacer(modifier = Modifier.height(MEDIUM_PADDING))
            }
        }

    }
}