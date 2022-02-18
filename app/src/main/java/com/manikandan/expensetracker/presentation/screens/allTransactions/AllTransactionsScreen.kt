package com.manikandan.expensetracker.presentation.screens.allTransactions

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.manikandan.expensetracker.R
import com.manikandan.expensetracker.domain.model.ErrorData
import com.manikandan.expensetracker.navigation.Screen
import com.manikandan.expensetracker.presentation.common.BottomSheetContent
import com.manikandan.expensetracker.presentation.common.ErrorScreen
import com.manikandan.expensetracker.presentation.common.ScreenHeader
import com.manikandan.expensetracker.presentation.common.SwipeToDismissListItem
import com.manikandan.expensetracker.presentation.common.util.connectivityState
import com.manikandan.expensetracker.presentation.screens.allTransactions.components.AllTransactionsShimmerEffect
import com.manikandan.expensetracker.presentation.screens.home.AddUpdateTransactionViewModel
import com.manikandan.expensetracker.presentation.screens.home.HomeEvent
import com.manikandan.expensetracker.presentation.screens.home.HomeViewModel
import com.manikandan.expensetracker.ui.theme.*
import com.manikandan.expensetracker.utils.ConnectionState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun AllTransactionsScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel(),
    addUpdateTransactionViewModel: AddUpdateTransactionViewModel = hiltViewModel()
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = MaterialTheme.colors.screenBackgroundColor
    )
    systemUiController.setNavigationBarColor(
        color = MaterialTheme.colors.screenBackgroundColor
    )

    AllTransactionsScreenDesign(
        homeViewModel = homeViewModel,
        addUpdateTransactionViewModel = addUpdateTransactionViewModel,
        onProfilePictureClicked = {
            navController.navigate(Screen.Profile.route) {
                launchSingleTop = true
            }
        }
    )
}

@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun AllTransactionsScreenDesign(
    homeViewModel: HomeViewModel,
    addUpdateTransactionViewModel: AddUpdateTransactionViewModel,
    onProfilePictureClicked: () -> Unit
) {
    val modelBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = {
            it != ModalBottomSheetValue.HalfExpanded
        }
    )
    val lazyListState = rememberLazyListState()
    val scrollToTopButtonVisibility = lazyListState.firstVisibleItemIndex > 0
    val scope = rememberCoroutineScope()

    val user by homeViewModel.userCredentials
    val allTransactions by homeViewModel.allMonthWiseTransactionsInAYear
    val errorData by homeViewModel.errorData
    val isLoading by homeViewModel.isLoading


    ModalBottomSheetLayout(
        sheetState = modelBottomSheetState,
        sheetContent = {
            BottomSheetContent(addUpdateTransactionViewModel = addUpdateTransactionViewModel)
        },
        sheetElevation = 10.dp,
        sheetShape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(color = MaterialTheme.colors.screenBackgroundColor)
                    .padding(all = LARGE_PADDING)
                    .padding(bottom = 36.dp)
            ) {
                ScreenHeader(
                    userName = user.userName.uppercase(),
                    profilePicture = if (user.gender == "Male") R.drawable.profile_man else R.drawable.profile_woman,
                    onProfilePictureClicked = onProfilePictureClicked
                )
                Text(
                    modifier = Modifier.padding(vertical = LARGE_PADDING),
                    text = "All Transactions",
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    color = MaterialTheme.colors.titleColor
                )
                val allTransactionScreenResult = handleAllTransactionScreen(
                    isLoading = isLoading,
                    errorData = errorData
                )
                if (allTransactionScreenResult) {
                    LazyColumn(
                        modifier = Modifier.padding(EXTRA_SMALL_PADDING),
                        state = lazyListState
                    ) {
                        allTransactions.forEach { (year, yearTransactions) ->
                            stickyHeader {
                                StickyHeaderDesign(value = year.toString())
                            }
                            yearTransactions.forEach { (month, monthTransactions) ->
                                item {
                                    StickyHeaderDesign(value = month)
                                }
                                items(
                                    items = monthTransactions,
                                    key = { singleTransaction ->
                                        singleTransaction.transactionId

                                    }
                                ) { singleTransaction ->
                                    SwipeToDismissListItem(
                                        singleTransaction = singleTransaction,
                                        homeViewModel = homeViewModel,
                                        onItemClicked = { selectedSingleTransaction ->
                                            addUpdateTransactionViewModel.populateTextFields(
                                                singleTransaction = selectedSingleTransaction
                                            )
                                            scope.launch {
                                                modelBottomSheetState.animateTo(
                                                    ModalBottomSheetValue.Expanded
                                                )
                                            }
                                        },
                                        onDismissed = {
                                            val deleteTransactionDetail = mapOf(
                                                "user_id" to user.userId,
                                                "transaction_id" to singleTransaction.transactionId
                                            )
                                            homeViewModel.onEvent(
                                                event = HomeEvent.DeleteTransaction(
                                                    deleteTransactionDetail = deleteTransactionDetail
                                                )
                                            )

                                        }
                                    )
                                }
                                item {
                                    Spacer(modifier = Modifier.height(MEDIUM_PADDING))
                                }
                            }
                        }
                    }
                } else {
                    Text(text = "Noob")
                }
            }
            AnimatedVisibility(
                modifier = Modifier.align(alignment = Alignment.BottomCenter),
                enter = fadeIn(),
                exit = fadeOut(),
                visible = scrollToTopButtonVisibility
            ) {
                ScrollToTopButton(
                    onClick = {
                        scope.launch {
                            lazyListState.scrollToItem(index = 0)
                        }
                    },
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@ExperimentalCoroutinesApi
@Composable
private fun handleAllTransactionScreen(
    errorData: ErrorData?,
    isLoading: Boolean,
): Boolean {
    val connectionState by connectivityState()
    return when {
        connectionState == ConnectionState.Unavailable -> {
            val error = ErrorData(
                errorImage = R.drawable.ic_no_internet,
                errorTitle = "No Internet Available!!",
                solutionText = "Check your internet connection."
            )
            ErrorScreen(errorData = error)
            false
        }
        isLoading -> {
            AllTransactionsShimmerEffect()
            false
        }
        errorData != null -> {
            ErrorScreen(errorData = errorData)
            false
        }
        else -> {
            true
        }
    }
}

@Composable
fun ScrollToTopButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    FloatingActionButton(
        modifier = modifier,
        backgroundColor = Amber,
        contentColor = White90,
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.Default.KeyboardArrowUp,
            contentDescription = stringResource(R.string.arrow_up_icon)
        )
    }
}


@Composable
fun StickyHeaderDesign(value: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.screenBackgroundColor)
    ) {

        Surface(
            modifier = Modifier.padding(bottom = SMALL_PADDING),
            color = MaterialTheme.colors.viewAllButtonColor,
            shape = CircleShape,
            contentColor = MaterialTheme.colors.titleColor,
        ) {
            Text(
                modifier = Modifier.padding(horizontal = SMALL_PADDING),
                text = value,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
