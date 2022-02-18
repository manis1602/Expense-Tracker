package com.manikandan.expensetracker.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val White90 = Color(0xE6FFFFFF)
val DarkGray = Color(0xFF383838)
val SemiDarkGray = Color(0xFF626262)
val MediumGray = Color(0xFFAFAFAF)
val LightGray = Color(0xFFCACACA)
val SemiLightGray = Color(0xFFF8F8F8)
val DarkPurple = Color(0xFF453643)
val SemiDarkPurple = Color(0xFF5B4858)
val DarkPurple15 = Color(0x26453643)
val DarkPurple80 = Color(0xCC453643)
val MediumPurple = Color(0xFF856478)
val Black = Color(0xFF121212)
val Red = Color(0xFFED1C24)
val DarkRed = Color(0xFFBA1B1B)
val MatteRed = Color(0xFF990228)
val Green = Color(0xFF08A045)
val MatteGreen = Color(0xFF0C725A)
val Brown = Color(0xFF72380C)
val Amber = Color(0xFFEF8767)

val ShimmerLightGray = Color(0xFFF1F1F1)
val ShimmerMediumGray = Color(0xFFE3E3E3)
val ShimmerDarkGray = Color(0xFF474747)

val Colors.splashScreenBackgroundColor
    @Composable
    get() = if (isLight) MediumPurple else Black

val Colors.screenBackgroundColor
    @Composable
    get() = if (isLight) Color.White else Black

val Colors.cardBackgroundColor
    @Composable
    get() = if (isLight) Color.White else DarkGray

val Colors.onBoardingTitleColor
    @Composable
    get() = if (isLight) Amber else White90

val Colors.buttonColor
    @Composable
    get() = if (isLight) DarkPurple else MediumPurple

val Colors.buttonContentColor
    @Composable
    get() = if (isLight) Color.White else White90

val Colors.secondaryColor
    @Composable
    get() = if (isLight) MediumPurple else White90

val Colors.titleColor
    @Composable
    get() = if (isLight) DarkPurple else White90

val Colors.textFieldFocusedBorderColor
    @Composable
    get() = if (isLight) DarkPurple80 else White90

val Colors.snackBarBackgroundColor
    @Composable
    get() = if (isLight) DarkRed else White90

val Colors.dropDownBackground
    @Composable
    get() = if (isLight) DarkPurple else SemiLightGray

val Colors.titleColorAlternate
    @Composable
    get() = if (isLight) Black else White90

val Colors.viewAllButtonColor
    @Composable
    get() = if (isLight) DarkPurple15 else SemiDarkGray

val Colors.recentTransactionMonthBackgroundColor
    @Composable
    get() = if (isLight) DarkPurple else DarkGray

val Colors.incomeAmountTextColor
    @Composable
    get() = if (isLight) Green else MatteGreen

val Colors.expenseAmountTextColor
    @Composable
    get() = if (isLight) Red else MatteRed

val Colors.overallStatsIncomeBarColor
    @Composable
    get() = if (isLight) DarkPurple else SemiDarkPurple

val Colors.shimmerBackgroundColor
    @Composable
    get() = if (isLight) ShimmerLightGray else Color.Black

val Colors.shimmerContentColor
    @Composable
    get() = if (isLight) ShimmerMediumGray else ShimmerDarkGray


