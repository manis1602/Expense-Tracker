package com.manikandan.expensetracker.navigation

import com.manikandan.expensetracker.utils.Constants.ALL_TRANSACTIONS_ROUTE
import com.manikandan.expensetracker.utils.Constants.HOME_ROUTE
import com.manikandan.expensetracker.utils.Constants.LOGIN_ROUTE
import com.manikandan.expensetracker.utils.Constants.PROFILE_ROUTE
import com.manikandan.expensetracker.utils.Constants.REGISTER_ROUTE
import com.manikandan.expensetracker.utils.Constants.RESET_PASSWORD_ROUTE
import com.manikandan.expensetracker.utils.Constants.SPLASH_ROUTE
import com.manikandan.expensetracker.utils.Constants.WELCOME_ROUTE

sealed class Screen(val route: String) {
    object Splash : Screen(SPLASH_ROUTE)
    object Login : Screen(LOGIN_ROUTE)
    object Register : Screen(REGISTER_ROUTE)
    object ResetPassword : Screen(RESET_PASSWORD_ROUTE)
    object Welcome : Screen(WELCOME_ROUTE)
    object Profile : Screen(PROFILE_ROUTE)
    object Home : Screen(HOME_ROUTE)
    object AllTransactions : Screen(ALL_TRANSACTIONS_ROUTE)
}
