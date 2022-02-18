package com.manikandan.expensetracker.presentation.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.manikandan.expensetracker.R
import com.manikandan.expensetracker.domain.model.ErrorData
import com.manikandan.expensetracker.ui.theme.*

@Composable
fun ErrorScreen(
    onRefresh: () -> Unit = {},
    errorData: ErrorData
) {
    var isRefreshing by remember {
        mutableStateOf(false)
    }

    val swipeState = rememberSwipeRefreshState(isRefreshing = isRefreshing)


    SwipeRefresh(
        swipeEnabled = true,
        state = swipeState,
        onRefresh = {
            isRefreshing = true
            onRefresh()
            isRefreshing = false
        },
        indicator = { state, distance ->
            SwipeRefreshIndicator(
                state = state,
                refreshTriggerDistance = distance,
                contentColor = MaterialTheme.colors.titleColor,
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
//            modifier = Modifier.size(100.dp),
                painter = painterResource(id = errorData.errorImage),
                contentDescription = stringResource(R.string.empty_screen_add),
                tint = MaterialTheme.colors.textFieldFocusedBorderColor
            )
            Spacer(modifier = Modifier.height(MEDIUM_PADDING))
            Text(
                text = errorData.errorTitle,
                fontSize = MaterialTheme.typography.h6.fontSize,
                color = MaterialTheme.colors.titleColor,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(PAGER_INDICATOR_WIDTH))
            Text(
                text = errorData.solutionText,
                fontSize = MaterialTheme.typography.body1.fontSize,
                color = MediumGray,
            )
        }
    }
}