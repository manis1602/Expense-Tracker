package com.manikandan.expensetracker.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.manikandan.expensetracker.presentation.screens.allTransactions.AllTransactionsScreen
import com.manikandan.expensetracker.presentation.screens.home.HomeScreen
import com.manikandan.expensetracker.presentation.screens.login.LoginScreen
import com.manikandan.expensetracker.presentation.screens.profile.ProfileScreen
import com.manikandan.expensetracker.presentation.screens.register.RegisterScreen
import com.manikandan.expensetracker.presentation.screens.resetPassword.ResetPasswordScreen
import com.manikandan.expensetracker.presentation.screens.splash.SplashScreen
import com.manikandan.expensetracker.presentation.screens.welcome.WelcomeScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun NavGraph(
    navController: NavHostController
) {
    val animDuration = 500
    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(
            route = Screen.Splash.route,
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(animDuration))
            },
            enterTransition = {
                slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(animDuration))
            },

            ) {
            SplashScreen(navController = navController)
        }
        composable(
            route = Screen.Login.route,
            enterTransition = {
                slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(animDuration))
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(animDuration))
            },
            popEnterTransition = {
                slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(animDuration))
            }
        ) {
            LoginScreen(navController = navController)
        }
        composable(
            route = Screen.Register.route,
            enterTransition = {
                slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(animDuration))
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(animDuration))
            },
            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(animDuration))
            },
        ) {
            RegisterScreen(navController = navController)
        }
        composable(
            route = Screen.ResetPassword.route,
            enterTransition = {
                slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(animDuration))
            },
            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(animDuration))
            },
        ) {
            ResetPasswordScreen(navController = navController)
        }
        composable(route = Screen.Welcome.route,
            enterTransition = {
                slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(animDuration))
            }, exitTransition = {
                slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(animDuration))
            }) {
            WelcomeScreen(navController = navController)
        }
        composable(
            route = Screen.Profile.route,
            enterTransition = {
                        slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(animDuration))
            },
            exitTransition = {
                        slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(animDuration))
            },
            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(animDuration))
            },
        ) {
            ProfileScreen(navController = navController)
        }
        composable(route = Screen.Home.route,
            enterTransition = {
                slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(animDuration))
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(animDuration))
            },
            popEnterTransition = {
                slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(animDuration))
            }
        ) {
            HomeScreen(navController = navController)
        }
        composable(
            route = Screen.AllTransactions.route,
            enterTransition = {
                slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(animDuration))
            },
            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(animDuration))
            },
            popEnterTransition = {
                slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(animDuration))
            }
        ) {
            AllTransactionsScreen(navController = navController)
        }
    }
}