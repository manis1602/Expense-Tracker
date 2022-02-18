package com.manikandan.expensetracker.utils


object Constants {

    // Screens route value
    const val SPLASH_ROUTE = "splash_screen"
    const val LOGIN_ROUTE = "login_screen"
    const val REGISTER_ROUTE= "register_screen"
    const val RESET_PASSWORD_ROUTE= "reset_password_screen"
    const val WELCOME_ROUTE= "welcome_screen"
    const val PROFILE_ROUTE= "profile_screen"
    const val HOME_ROUTE= "home_screen"
    const val ALL_TRANSACTIONS_ROUTE= "all_transactions_screen"

    // DataStore
    const val PREFERENCES_NAME = "expenses_preferences"
    const val PREFERENCES_ON_BOARDING_COMPLETED_KEY = "on_boarding_completed"
    const val PREFERENCES_USER_ID_KEY = "preferences_user_id"
    const val PREFERENCES_USER_EMAIL_KEY = "preferences_user_email"
    const val PREFERENCES_USER_PASSWORD_KEY = "preferences_user_password"
    const val PREFERENCES_USER_GENDER_KEY = "preferences_user_gender"
    const val PREFERENCES_USER_NAME_KEY = "preferences_user_name"
    const val PREFERENCES_LOGIN_COMPLETED_KEY = "on_login_completed"

    //Retrofit
    const val BASE_URL = "https://expense-tracker-mani.herokuapp.com"
    const val OK_HTTP_CLIENT_BASIC = "basicOkHttpClient"
    const val OK_HTTP_CLIENT_WITH_BASIC_AUTH_INTERCEPTED = "okHttpClientWithBasicAuthIntercepted"
    const val RETROFIT_INSTANCE_FOR_USER_API = "retrofitInstanceForUserApi"
    const val RETROFIT_INSTANCE_FOR_USER_TRANSACTION_API = "retrofitInstanceWithBasicAuthForUserTransactionApi"

    //App
    const val APP_LOGO_TITLE = "Expense\nTracker"

    //OnBoarding Pages
    const val PAGE_ONE_TITLE = "Manage your own money"
    const val PAGE_ONE_DESCRIPTION = "Easily manage all your daily expenses\n" +
            "just with a single click !!"
    const val PAGE_TWO_TITLE = "Secure and flexible"
    const val PAGE_TWO_DESCRIPTION = "Easy to add, edit and delete your \n" +
            "cashflow in a more secured way. \n" +
            "Chill !! "
    const val PAGE_THREE_TITLE = "Visualize cashflow"
    const val PAGE_THREE_DESCRIPTION = "Visualize all your monthly cashflow \n" + "made within a year !!"

    // Room
    const val USER_TRANSACTIONS_TABLE_NAME = "user_transactions_table"
    const val EXPENSE_TRACKER_DATABASE_NAME = "expense_tracker_db"

    const val LAST_ON_BOARDING_PAGE_NUMBER = 2
    const val ON_BOARDING_PAGE_COUNT = 3

    const val RECENT_TRANSACTIONS_PLACEHOLDER_ITEM_COUNT = 5
    const val ALL_TRANSACTIONS_PLACEHOLDER_ITEM_COUNT = 10

    const val MAX_AMOUNT_DIGIT_COUNT = 10
}