package com.manikandan.expensetracker.presentation.screens.splash

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.manikandan.expensetracker.R
import com.manikandan.expensetracker.navigation.Screen
import com.manikandan.expensetracker.ui.theme.MEDIUM_PADDING
import com.manikandan.expensetracker.ui.theme.buttonContentColor
import com.manikandan.expensetracker.ui.theme.splashScreenBackgroundColor
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavHostController,
    splashViewModel: SplashViewModel = hiltViewModel()
) {
    val isOnBoardingCompleted by splashViewModel.isOnBoardingCompleted.collectAsState()
    val isAlreadyLoggedIn by splashViewModel.isAlreadyLoggedIn.collectAsState()

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = MaterialTheme.colors.splashScreenBackgroundColor
    )
    systemUiController.setNavigationBarColor(
        color = MaterialTheme.colors.splashScreenBackgroundColor
    )

    LaunchedEffect(key1 = true) {
        delay(2000)
        navController.popBackStack()
        if (isAlreadyLoggedIn) {
            navController.navigate(Screen.Home.route)
        } else {
            if (isOnBoardingCompleted) {
                navController.navigate(Screen.Login.route)
            } else {
                navController.navigate(Screen.Welcome.route)
            }
        }
    }

    SplashScreenDesign()
}

@Composable
private fun SplashScreenDesign() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colors.splashScreenBackgroundColor
            ),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = stringResource(
                    R.string.app_logo
                )
            )
            Spacer(modifier = Modifier.width(MEDIUM_PADDING))
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colors.buttonContentColor,
                            fontSize = 35.sp,
                            fontWeight = FontWeight.Bold,
                            shadow = Shadow(
                                color = Color.Black.copy(
                                    alpha = 0.16f
                                ),
                                offset = Offset(x = 0f, y = 10f),
                                blurRadius = 6.0f
                            ),
                            letterSpacing = 1.sp
                        )
                    ) {
                        append("EXPENSE\n")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colors.buttonContentColor,
                            fontSize = 35.sp,
                            fontWeight = FontWeight.Bold,
                            shadow = Shadow(
                                color = Color.Black.copy(
                                    alpha = 0.16f
                                ),
                                offset = Offset(x = 0f, y = 10f),
                                blurRadius = 6.0f
                            ),
                            letterSpacing = 0.01.sp

                        )
                    ) {
                        append("TRACKER")
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreenDesign()
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SplashScreenDarkPreview() {
    SplashScreenDesign()
}