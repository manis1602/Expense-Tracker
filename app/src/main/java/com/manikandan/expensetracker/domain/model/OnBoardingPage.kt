package com.manikandan.expensetracker.domain.model

import androidx.annotation.DrawableRes
import com.manikandan.expensetracker.R
import com.manikandan.expensetracker.utils.Constants.PAGE_ONE_DESCRIPTION
import com.manikandan.expensetracker.utils.Constants.PAGE_ONE_TITLE
import com.manikandan.expensetracker.utils.Constants.PAGE_THREE_DESCRIPTION
import com.manikandan.expensetracker.utils.Constants.PAGE_THREE_TITLE
import com.manikandan.expensetracker.utils.Constants.PAGE_TWO_DESCRIPTION
import com.manikandan.expensetracker.utils.Constants.PAGE_TWO_TITLE

sealed class OnBoardingPage(
    @DrawableRes
    val image: Int,
    val title: String,
    val description: String
){
    object First: OnBoardingPage(
        image = R.drawable.on_boarding_image_1,
        title = PAGE_ONE_TITLE,
        description = PAGE_ONE_DESCRIPTION
    )

    object Second: OnBoardingPage(
        image = R.drawable.on_boarding_image_2,
        title = PAGE_TWO_TITLE,
        description = PAGE_TWO_DESCRIPTION
    )

    object Third: OnBoardingPage(
        image = R.drawable.on_boarding_image_3,
        title = PAGE_THREE_TITLE,
        description = PAGE_THREE_DESCRIPTION
    )
}
