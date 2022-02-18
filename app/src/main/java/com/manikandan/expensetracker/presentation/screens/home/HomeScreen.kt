package com.manikandan.expensetracker.presentation.screens.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.manikandan.expensetracker.R
import com.manikandan.expensetracker.domain.model.ErrorData
import com.manikandan.expensetracker.domain.model.UiEvents
import com.manikandan.expensetracker.domain.model.User
import com.manikandan.expensetracker.navigation.Screen
import com.manikandan.expensetracker.presentation.common.BottomSheetContent
import com.manikandan.expensetracker.presentation.common.ErrorScreen
import com.manikandan.expensetracker.presentation.common.ScreenHeader
import com.manikandan.expensetracker.presentation.common.util.connectivityState
import com.manikandan.expensetracker.presentation.screens.home.components.home_screen.ExpenseBarGraph
import com.manikandan.expensetracker.presentation.screens.home.components.home_screen.HomeScreenShimmer
import com.manikandan.expensetracker.presentation.screens.home.components.home_screen.RecentTransactions
import com.manikandan.expensetracker.ui.theme.*
import com.manikandan.expensetracker.utils.ConnectionState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel(),
    addUpdateTransactionViewModel: AddUpdateTransactionViewModel = hiltViewModel()
) {
    val user by homeViewModel.userCredentials

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = MaterialTheme.colors.screenBackgroundColor
    )
    systemUiController.setNavigationBarColor(
        color = MaterialTheme.colors.screenBackgroundColor
    )

    HomeScreenDesign(
        homeViewModel = homeViewModel,
        addUpdateTransactionViewModel = addUpdateTransactionViewModel,
        user = user,
        onViewAllClicked = {
            navController.navigate(Screen.AllTransactions.route) {
                launchSingleTop = true
            }

        },
        onProfilePictureClicked = {
            navController.navigate(Screen.Profile.route) {
                launchSingleTop = true
            }
        }
    )
}


@ExperimentalFoundationApi
@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun HomeScreenDesign(
    homeViewModel: HomeViewModel,
    addUpdateTransactionViewModel: AddUpdateTransactionViewModel,
    user: User,
    onViewAllClicked: () -> Unit,
    onProfilePictureClicked: () -> Unit
) {
    val modelBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { modelBottomSheetValue ->
            if (modelBottomSheetValue == ModalBottomSheetValue.Hidden) {
                addUpdateTransactionViewModel.onEvent(
                    event = AddUpdateTransactionEvent.ResetAddUpdateTransactionForm
                )
            }
            modelBottomSheetValue != ModalBottomSheetValue.HalfExpanded
        }
    )
    val isLoading by homeViewModel.isLoading
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val errorData by homeViewModel.errorData

    LaunchedEffect(key1 = true) {
        homeViewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvents.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(message = event.message)
                }
            }
        }
    }

    ModalBottomSheetLayout(
        sheetState = modelBottomSheetState,
        sheetContent = {
            BottomSheetContent(addUpdateTransactionViewModel = addUpdateTransactionViewModel)
        },
        sheetElevation = 10.dp,
        sheetShape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
    ) {
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
            floatingActionButton = {
                FloatingActionButton(
                    backgroundColor = Amber,
                    contentColor = White90,
                    onClick = {
                        scope.launch {

                            modelBottomSheetState.animateTo(ModalBottomSheetValue.Expanded)

                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(R.string.add_expense_icon)
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.Center,

            ) {
            Column(
                modifier = Modifier
                    .padding(all = LARGE_PADDING)
                    .padding(bottom = 60.dp)
                    .fillMaxHeight()
            ) {
                ScreenHeader(
                    userName = user.userName.uppercase(),
                    profilePicture = if (user.gender == "Male") R.drawable.profile_man else R.drawable.profile_woman,
                    onProfilePictureClicked = onProfilePictureClicked
                )
                val transactionResult =
                    handleTransactions(
                        homeViewModel = homeViewModel,
                        errorData = errorData,
                        isLoading = isLoading
                    )
                if (transactionResult) {
                    // if no error get all transactions.
                    val allTransactions by homeViewModel.allTransactions

                    ExpenseBarGraph(modifier = Modifier.weight(0.4f), homeViewModel = homeViewModel)
                    RecentTransactions(
                        modifier = Modifier.weight(0.6f),
                        recentTransaction = allTransactions,
                        onViewAllClicked = onViewAllClicked,
                        onItemClicked = {
                            addUpdateTransactionViewModel.populateTextFields(it)
                            scope.launch {
                                modelBottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
                            }
                        },
                        onDismissed = { transactionId ->
                            val deleteTransactionDetail = mapOf(
                                "user_id" to user.userId,
                                "transaction_id" to transactionId
                            )
                            homeViewModel.onEvent(
                                event = HomeEvent.DeleteTransaction(
                                    deleteTransactionDetail = deleteTransactionDetail
                                )
                            )
                        },
                        homeViewModel = homeViewModel
                    )
                }
            }
        }
    }
}

@ExperimentalCoroutinesApi
@Composable
fun handleTransactions(
    homeViewModel: HomeViewModel,
    isLoading: Boolean,
    errorData: ErrorData?
): Boolean {
    val connectionState by connectivityState()
    val user by homeViewModel.userCredentials
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
                    homeViewModel.onEvent(event = HomeEvent.GetUsersAllTransactions(userId = user.userId))
                }
            )
            false
        }
        isLoading -> {
            HomeScreenShimmer()
            false
        }
        errorData != null -> {
            ErrorScreen(
                errorData = errorData,
                onRefresh = {
                    homeViewModel.onEvent(event = HomeEvent.GetUsersAllTransactions(userId = user.userId))
                }
            )
            false
        }
        else -> {
            true
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EmptyHomeScreenPreview() {
    ErrorScreen(
        errorData = ErrorData(
            errorImage = R.drawable.ic_add,
            errorTitle = "No Transactions Found!",
            solutionText = "Click \"+\" to add new"
        )
    )
}


