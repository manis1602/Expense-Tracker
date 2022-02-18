package com.manikandan.expensetracker.presentation.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.manikandan.expensetracker.R
import com.manikandan.expensetracker.ui.theme.SMALL_PADDING
import com.manikandan.expensetracker.ui.theme.titleColorAlternate

@Composable
fun ScreenHeader(
    userName: String,
    @DrawableRes profilePicture: Int,
    onProfilePictureClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = "HELLO\n$userName",
            fontSize = MaterialTheme.typography.h4.fontSize,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colors.titleColorAlternate,
        )
        Spacer(modifier = Modifier.width(SMALL_PADDING))
        Image(
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(10.dp))
                .clickable {
                           onProfilePictureClicked()
                },
            painter = painterResource(id = profilePicture),
            contentDescription = stringResource(R.string.profile_picture),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ScreenHeaderPreview() {
    ScreenHeader(userName = "MANIKANDAN", profilePicture = R.drawable.profile_woman, onProfilePictureClicked = {})
}