package com.manikandan.expensetracker.presentation.screens.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.manikandan.expensetracker.R
import com.manikandan.expensetracker.domain.model.OnBoardingPage
import com.manikandan.expensetracker.navigation.Screen
import com.manikandan.expensetracker.ui.theme.*
import com.manikandan.expensetracker.utils.Constants.ON_BOARDING_PAGE_COUNT

@ExperimentalPagerApi
@Composable
fun WelcomeScreen(
    navController: NavHostController,
    welcomeViewModel: WelcomeViewModel = hiltViewModel()
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = MaterialTheme.colors.screenBackgroundColor
    )
    systemUiController.setNavigationBarColor(
        color = MaterialTheme.colors.screenBackgroundColor
    )
    val pages = listOf(
        OnBoardingPage.First,
        OnBoardingPage.Second,
        OnBoardingPage.Third
    )

    val pagerState = rememberPagerState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.screenBackgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            modifier = Modifier
                .align(alignment = Alignment.Start)
                .padding(all = MEDIUM_PADDING)
                .size(50.dp),
            painter = painterResource(id = R.drawable.ic_logo), contentDescription = stringResource(
                id = R.string.app_logo
            ),
            tint = MaterialTheme.colors.secondaryColor
        )

        HorizontalPager(
            state = pagerState,
            count = ON_BOARDING_PAGE_COUNT,
            modifier = Modifier
                .weight(weight = 10f),
            verticalAlignment = Alignment.Top
        ) { position ->
            SinglePageScreen(onBoardingPage = pages[position])
        }

        HorizontalPagerIndicator(
            modifier = Modifier.weight(weight = 1f),
            pagerState = pagerState,
            activeColor = MediumPurple,
            inactiveColor = LightGray,
            indicatorWidth = PAGER_INDICATOR_WIDTH,
            spacing = PAGER_INDICATOR_SPACING
        )
        GetStartedButton(modifier = Modifier.padding(all = MEDIUM_PADDING), pagerState = pagerState) {
            navController.popBackStack()
            navController.navigate(Screen.Login.route)
            welcomeViewModel.saveOnBoardingState(completed = true)
        }
    }
}