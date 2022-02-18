package com.manikandan.expensetracker.presentation.screens.home.components.home_screen

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.manikandan.expensetracker.ui.theme.*
import com.manikandan.expensetracker.utils.Constants.RECENT_TRANSACTIONS_PLACEHOLDER_ITEM_COUNT

@Composable
fun HomeScreenShimmer(){
    val transition = rememberInfiniteTransition()
    val alphaAnim by transition.animateFloat(
        initialValue =1f,
        targetValue =0f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 500,
                easing = FastOutLinearInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )
    HomeScreenShimmerDesign(alpha = alphaAnim)
}

@Composable
fun HomeScreenShimmerDesign(
    alpha : Float
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ){

            Surface(
                modifier = Modifier
                    .weight(0.4f)
                    .padding(vertical = LARGE_PADDING)
                    .fillMaxSize()
                    .alpha(alpha = alpha),
                color = MaterialTheme.colors.shimmerContentColor,
                shape = RoundedCornerShape(size = SMALL_PADDING)
            ) {}
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = MEDIUM_PADDING),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(TRANSACTION_TITLE_PLACE_HOLDER_HEIGHT)
                        .alpha(alpha = alpha),
                    color = MaterialTheme.colors.shimmerContentColor,
                    shape = RoundedCornerShape(size = SMALL_PADDING)

                ) {}
                Surface(
                    modifier = Modifier
                        .fillMaxWidth(0.2f)
                        .height(MEDIUM_PADDING)
                        .alpha(alpha = alpha),
                    color = MaterialTheme.colors.shimmerContentColor,
                    shape = RoundedCornerShape(size = SMALL_PADDING)
                ) {}
            }
            LazyColumn {
                items(RECENT_TRANSACTIONS_PLACEHOLDER_ITEM_COUNT) {
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenShimmerPreview() {
    HomeScreenShimmerDesign(alpha = 1f)
}