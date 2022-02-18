package com.manikandan.expensetracker.presentation.screens.profile.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import com.manikandan.expensetracker.ui.theme.*

@Composable
fun ProfileScreenShimmer() {
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
    ProfileScreenShimmerDesign(alpha = alphaAnim)
}

@Composable
fun ProfileScreenShimmerDesign(
    alpha: Float
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Surface(
            modifier = Modifier
                .padding(vertical = MEDIUM_PADDING)
                .fillMaxWidth(0.3f)
                .height(TRANSACTION_TITLE_PLACE_HOLDER_HEIGHT)
                .alpha(alpha = alpha),
            color = MaterialTheme.colors.shimmerContentColor,
            shape = RoundedCornerShape(size = SMALL_PADDING)
        ) {}

        Surface(
            modifier = Modifier
                .padding(bottom = MEDIUM_PADDING)
                .fillMaxWidth(0.2f)
                .height(LARGE_PADDING)
                .alpha(alpha = alpha),
            color = MaterialTheme.colors.shimmerContentColor,
            shape = RoundedCornerShape(size = EXTRA_SMALL_PADDING)
        ) {}

        Card(
            modifier = Modifier
                .padding(bottom = LARGE_PADDING)
                .weight(0.2f)
                .fillMaxSize(),
            shape = RoundedCornerShape(size = SMALL_PADDING)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(alpha = alpha),
                color = MaterialTheme.colors.shimmerContentColor,
            ) {}
        }
        Surface(
            modifier = Modifier
                .padding(bottom = MEDIUM_PADDING)
                .fillMaxWidth(0.3f)
                .height(LARGE_PADDING)
                .alpha(alpha = alpha),
            color = MaterialTheme.colors.shimmerContentColor,
            shape = RoundedCornerShape(size = EXTRA_SMALL_PADDING)
        ) {}

        Card(
            modifier = Modifier
                .padding(bottom = LARGE_PADDING)
                .weight(0.4f)
                .fillMaxSize(),
            shape = RoundedCornerShape(size = SMALL_PADDING)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(alpha = alpha),
                color = MaterialTheme.colors.shimmerContentColor,
            ) {}
        }
        Spacer(modifier = Modifier.weight(0.3f))
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ProfileScreenShimmerPreview() {
    ProfileScreenShimmerDesign(alpha = 1f)
}