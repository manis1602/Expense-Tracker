package com.manikandan.expensetracker.presentation.screens.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.manikandan.expensetracker.R
import com.manikandan.expensetracker.domain.model.OnBoardingPage
import com.manikandan.expensetracker.ui.theme.MediumGray
import com.manikandan.expensetracker.ui.theme.onBoardingTitleColor

@Composable
fun SinglePageScreen(
    onBoardingPage: OnBoardingPage
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(modifier = Modifier.fillMaxHeight(0.4f), contentAlignment = Alignment.BottomCenter){
            Image(
//                modifier = Modifier
//                    .fillMaxHeight(0.7f),
                painter = painterResource(id = onBoardingPage.image),
                contentDescription = stringResource(
                    R.string.on_boarding_images
                ),
            )
        }

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = onBoardingPage.title,
            color = MaterialTheme.colors.onBoardingTitleColor,
            fontSize = MaterialTheme.typography.h5.fontSize,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = onBoardingPage.description,
            color = MediumGray,
            fontSize = MaterialTheme.typography.body1.fontSize,
            textAlign = TextAlign.Center
        )
    }
}