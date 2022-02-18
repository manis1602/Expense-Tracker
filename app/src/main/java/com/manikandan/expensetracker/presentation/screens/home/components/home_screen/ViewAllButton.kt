package com.manikandan.expensetracker.presentation.screens.home.components.home_screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import com.manikandan.expensetracker.ui.theme.EXTRA_SMALL_PADDING
import com.manikandan.expensetracker.ui.theme.titleColor
import com.manikandan.expensetracker.ui.theme.viewAllButtonColor

@ExperimentalMaterialApi
@Composable
fun ViewAllButton(
    onViewAllClicked: () -> Unit
) {
    Surface(
        onClick = onViewAllClicked,
        color = MaterialTheme.colors.viewAllButtonColor,
        shape = CircleShape,
        contentColor = MaterialTheme.colors.titleColor,
        role = Role.Button
    ) {
        Text(
            modifier = Modifier.padding(horizontal = EXTRA_SMALL_PADDING),
            text = "View All"
        )
    }
}