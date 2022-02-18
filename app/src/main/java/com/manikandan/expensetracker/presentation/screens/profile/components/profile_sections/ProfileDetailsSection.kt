package com.manikandan.expensetracker.presentation.screens.profile.components.profile_sections

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import com.manikandan.expensetracker.R
import com.manikandan.expensetracker.domain.model.User
import com.manikandan.expensetracker.ui.theme.*

@Composable
fun ProfileDetailsSection(
    user: User
) {
    val profilePicture =
        if (user.gender == "Male") R.drawable.profile_man else R.drawable.profile_woman
    Column(
        modifier = Modifier
            .padding(top = LARGE_PADDING),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(10.dp)),
            painter = painterResource(id = profilePicture),
            contentDescription = stringResource(R.string.profile_picture),
        )
        Text(
            modifier = Modifier.padding(top = MEDIUM_PADDING, bottom = EXTRA_SMALL_PADDING),
            text = user.userName,
            fontSize = MaterialTheme.typography.body1.fontSize,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.titleColor
        )
        Text(
            text = user.emailAddress,
            fontSize = MaterialTheme.typography.body2.fontSize,
            color = MediumGray
        )
    }
}