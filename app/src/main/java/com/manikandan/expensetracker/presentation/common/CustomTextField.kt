package com.manikandan.expensetracker.presentation.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manikandan.expensetracker.R
import com.manikandan.expensetracker.ui.theme.*

@Composable
fun CustomTextField(
    title: String,
    text: String,
    hint: String,
    isPasswordTextField: Boolean = false,
    keyboardActions: KeyboardActions,
    keyboardOptions: KeyboardOptions,

    onTextChanged: (String) -> Unit
) {
    var passwordVisibility by remember {
        mutableStateOf(false)
    }
    Column {
        Text(
            modifier = Modifier,
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = MaterialTheme.colors.secondaryColor
        )
        if (!isPasswordTextField) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                onValueChange = {
                    onTextChanged(it)
                },
                placeholder = {
                    Text(
                        text = hint,
                        style = MaterialTheme.typography.body1.copy(
                            color = MediumGray
                        )
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = MediumGray,
                    focusedBorderColor = MaterialTheme.colors.textFieldFocusedBorderColor,
                    textColor = MaterialTheme.colors.titleColor,
                    cursorColor = MaterialTheme.colors.titleColor
                ),
                shape = RoundedCornerShape(10.dp),
                singleLine = true,
                maxLines = 1,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions
                )
        } else {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                onValueChange = {
                    onTextChanged(it)
                },
                placeholder = {
                    Text(
                        text = hint,
                        style = MaterialTheme.typography.body1.copy(
                            color = MediumGray
                        )
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = MediumGray,
                    focusedBorderColor = MaterialTheme.colors.textFieldFocusedBorderColor,
                    textColor = MaterialTheme.colors.titleColor,
                    cursorColor = MaterialTheme.colors.titleColor
                ),
                shape = RoundedCornerShape(10.dp),
                trailingIcon = {
                    IconButton(onClick = {
                        passwordVisibility = !passwordVisibility
                    }) {
                        Icon(
                            painter = painterResource(id = if (!passwordVisibility) R.drawable.ic_password_closed else R.drawable.ic_password_open),
                            contentDescription = stringResource(R.string.password_icon),
                            tint = if (passwordVisibility) MaterialTheme.colors.buttonColor else MediumGray
                        )
                    }
                },
                visualTransformation = if (!passwordVisibility)
                    PasswordVisualTransformation()
                else
                    VisualTransformation.None,
                singleLine = true,
                maxLines = 1,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions
                )
        }
    }
}