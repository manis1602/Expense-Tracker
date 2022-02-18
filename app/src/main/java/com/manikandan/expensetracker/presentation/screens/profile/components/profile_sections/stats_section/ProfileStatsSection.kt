package com.manikandan.expensetracker.presentation.screens.profile.components.profile_sections.stats_section

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.manikandan.expensetracker.R
import com.manikandan.expensetracker.domain.model.ErrorData
import com.manikandan.expensetracker.domain.model.HighAndLowTransaction
import com.manikandan.expensetracker.domain.model.OverallStats
import com.manikandan.expensetracker.presentation.common.ErrorScreen
import com.manikandan.expensetracker.presentation.common.util.connectivityState
import com.manikandan.expensetracker.presentation.screens.profile.ProfileEvent
import com.manikandan.expensetracker.presentation.screens.profile.ProfileViewModel
import com.manikandan.expensetracker.presentation.screens.profile.components.ProfileScreenShimmer
import com.manikandan.expensetracker.ui.theme.MEDIUM_PADDING
import com.manikandan.expensetracker.ui.theme.MediumGray
import com.manikandan.expensetracker.utils.ConnectionState
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun ProfileStatsSection(
    modifier: Modifier = Modifier,
    profileViewModel: ProfileViewModel,
    overallStats: OverallStats,
    highAndLowTransactions: List<HighAndLowTransaction>
) {
    val sectionDivider: @Composable () -> Unit = {
        Divider(
            modifier = Modifier.padding(vertical = MEDIUM_PADDING),
            color = MediumGray,
            thickness = 0.5.dp
        )
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = MEDIUM_PADDING)
    ) {
        val handleTransactionsResult = handleTransactions(
            profileViewModel = profileViewModel
        )
        if (handleTransactionsResult) {
            ProfileStatsOverallSection(overallStats = overallStats)
            sectionDivider()
            ProfileStatsHighAndLowSection(highAndLowTransactions = highAndLowTransactions)
            sectionDivider()

        }
    }

}

@ExperimentalCoroutinesApi
@Composable
fun handleTransactions(
    profileViewModel: ProfileViewModel,
): Boolean {
    val connectionState by connectivityState()
    val user by profileViewModel.userCredentials
    val isLoading by profileViewModel.isLoading
    val errorData by profileViewModel.errorData
    return when {
        connectionState == ConnectionState.Unavailable -> {
            val error = ErrorData(
                errorImage = R.drawable.ic_no_internet,
                errorTitle = "No Internet Available!!",
                solutionText = "Check your internet connection."
            )
            ErrorScreen(
                errorData = error,
                onRefresh = {
                    profileViewModel.onEvent(event = ProfileEvent.GetUsersAllTransactions(userId = user.userId))
                },
            )
            false
        }
        isLoading -> {
            ProfileScreenShimmer()
            false
        }
        errorData != null -> {
            ErrorScreen(
                errorData = errorData!!,
                onRefresh = {
                    profileViewModel.onEvent(event = ProfileEvent.GetUsersAllTransactions(userId = user.userId))
                }
            )
            false
        }
        else -> {
            true
        }
    }
}