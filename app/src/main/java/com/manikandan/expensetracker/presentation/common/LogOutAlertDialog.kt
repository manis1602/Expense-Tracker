package com.manikandan.expensetracker.presentation.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Dialog
import com.manikandan.expensetracker.ui.theme.*

@Composable
fun LogOutAlertDialog(
    onDismiss: () -> Unit,
    onLogOutClicked: () -> Unit,
    onCancelClicked: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Card(
            modifier = Modifier,
            elevation = EXTRA_SMALL_PADDING,
            shape = RoundedCornerShape(size = SMALL_PADDING)
        ) {
            Column(
                modifier = Modifier.padding(all = MEDIUM_PADDING),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "LOGOUT",
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.h6.fontSize,
                    color = MaterialTheme.colors.titleColorAlternate
                )
                Text(
                    modifier = Modifier.padding(bottom = MEDIUM_PADDING),
                    text = "Do you wish to Logout ?",
                    fontSize = MaterialTheme.typography.body1.fontSize,
                    color = MaterialTheme.colors.titleColorAlternate
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        modifier = Modifier.padding(end = SMALL_PADDING),
                        onClick = onCancelClicked
                    ) {
                        Text(
                            text = "Cancel",
                            fontSize = MaterialTheme.typography.body2.fontSize,
                            color = MediumGray
                        )
                    }
                    TextButton(
                        onClick = onLogOutClicked
                    ) {
                        Text(
                            text = "Logout",
                            fontSize = MaterialTheme.typography.body1.fontSize,
                            color = MaterialTheme.colors.titleColor
                        )
                    }
                }
            }
        }

    }
}