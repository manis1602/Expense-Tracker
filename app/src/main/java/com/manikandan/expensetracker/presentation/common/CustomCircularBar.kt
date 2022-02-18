package com.manikandan.expensetracker.presentation.common

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.manikandan.expensetracker.ui.theme.buttonColor

@Composable
fun CustomCircularBar() {
    CircularProgressIndicator(
        color = MaterialTheme.colors.buttonColor,
        strokeWidth = 5.dp
    )
}