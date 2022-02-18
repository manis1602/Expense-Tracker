package com.manikandan.expensetracker.presentation.screens.welcome

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.manikandan.expensetracker.ui.theme.MEDIUM_PADDING
import com.manikandan.expensetracker.ui.theme.buttonColor
import com.manikandan.expensetracker.ui.theme.buttonContentColor
import com.manikandan.expensetracker.utils.Constants.LAST_ON_BOARDING_PAGE_NUMBER

@ExperimentalPagerApi
@Composable
fun GetStartedButton(
    modifier: Modifier,
    pagerState: PagerState,
    onClick: () -> Unit
) {
    AnimatedVisibility(visible = pagerState.currentPage == LAST_ON_BOARDING_PAGE_NUMBER) {
        Button(
            modifier = modifier.fillMaxWidth().height(50.dp),
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.buttonColor,
                contentColor = MaterialTheme.colors.buttonContentColor
            )
        ) {
            Text(
                text = "GET STARTED"
            )
        }
    }
}