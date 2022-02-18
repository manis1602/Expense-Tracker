package com.manikandan.expensetracker.presentation.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.manikandan.expensetracker.domain.model.UiEvents
import com.manikandan.expensetracker.domain.model.User
import com.manikandan.expensetracker.navigation.Screen
import com.manikandan.expensetracker.presentation.common.LogOutAlertDialog
import com.manikandan.expensetracker.presentation.screens.profile.components.profile_sections.ProfileDetailsSection
import com.manikandan.expensetracker.presentation.screens.profile.components.profile_sections.ProfileScreenHeader
import com.manikandan.expensetracker.presentation.screens.profile.components.profile_sections.stats_section.ProfileStatsSection
import com.manikandan.expensetracker.ui.theme.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest

@ExperimentalCoroutinesApi
@Composable
fun ProfileScreen(
    navController: NavHostController,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        profileViewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvents.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(message = event.message)
                }
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = {
            SnackbarHost(hostState = it) { data ->
                Snackbar(
                    snackbarData = data,
                    backgroundColor = MaterialTheme.colors.snackBarBackgroundColor,
                )
            }
        },
        content = {
            ProfileScreenDesign(
                profileViewModel = profileViewModel,
                onLogOutClicked = {
                    profileViewModel.onEvent(event = ProfileEvent.LogoutUser)
                    profileViewModel.updateLogOutDialogVisibility(visible = false)
                    navController.popBackStack()
                    navController.navigate(Screen.Splash.route) {
                        popUpTo(Screen.Home.route) {
                            inclusive = true
                        }
                    }
                },
                onBackClicked = {
                    navController.popBackStack()
                }
            )
        }
    )

}

@ExperimentalCoroutinesApi
@Composable
fun ProfileScreenDesign(
    profileViewModel: ProfileViewModel,
    onLogOutClicked: () -> Unit,
    onBackClicked: () -> Unit
) {
    val user by profileViewModel.userCredentials
    val isLogOutDialogVisible by profileViewModel.isLogOutDialogVisible
    val overallStats by profileViewModel.overAllStats
    val highLowTransactions by profileViewModel.highAndLowTransaction

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.screenBackgroundColor)
            .padding(all = LARGE_PADDING)
            .padding(bottom = 36.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileScreenHeader(onBackClicked = onBackClicked)
        ProfileDetailsSection(user = user)

        ProfileStatsSection(
            modifier = Modifier.weight(weight = 1f),
            overallStats = overallStats,
            highAndLowTransactions = highLowTransactions,
            profileViewModel = profileViewModel
        )
        LogoutButton(
            onClick = {
                profileViewModel.updateLogOutDialogVisibility(visible = true)
            }
        )

    }
    if (isLogOutDialogVisible) {
        LogOutAlertDialog(
            onDismiss = {
                profileViewModel.updateLogOutDialogVisibility(visible = false)
            },
            onLogOutClicked = onLogOutClicked,
            onCancelClicked = {
                profileViewModel.updateLogOutDialogVisibility(visible = false)
            }
        )
    }
}

@Composable
fun LogoutButton(
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.buttonColor,
            contentColor = MaterialTheme.colors.buttonContentColor
        ),
        onClick = onClick
    ) {
        Text(
            text = "LOGOUT",
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenHeaderPreview() {
    ProfileScreenHeader {

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileDetailSectionPreview() {
    ProfileDetailsSection(
        user = User(
            userId = "",
            userName = "Manikandan Sathiyabal",
            emailAddress = "hello@hello.com",
            password = "",
            gender = "Male"
        )
    )
}