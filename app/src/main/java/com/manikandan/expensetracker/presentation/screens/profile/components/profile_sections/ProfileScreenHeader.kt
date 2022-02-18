package com.manikandan.expensetracker.presentation.screens.profile.components.profile_sections

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.manikandan.expensetracker.R
import com.manikandan.expensetracker.ui.theme.titleColor

@Composable
fun ProfileScreenHeader(
    onBackClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
    ) {

        Icon(
            modifier = Modifier
                .size(24.dp)
                .align(alignment = Alignment.CenterStart)
                .clickable {
                    onBackClicked()
                },
            imageVector = Icons.Default.ArrowBack,
            contentDescription = stringResource(R.string.back_arrow),
            tint = MaterialTheme.colors.titleColor
        )

        Text(
            modifier = Modifier.align(alignment = Alignment.Center),
            text = "PROFILE",
            fontSize = MaterialTheme.typography.h6.fontSize,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.titleColor,
        )

    }
}