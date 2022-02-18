package com.manikandan.expensetracker.presentation.screens.resetPassword

import androidx.compose.animation.*
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.manikandan.expensetracker.domain.model.UiEvents
import com.manikandan.expensetracker.navigation.Screen
import com.manikandan.expensetracker.presentation.common.CustomCircularBar
import com.manikandan.expensetracker.presentation.common.CustomTextField
import com.manikandan.expensetracker.ui.theme.*
import kotlinx.coroutines.flow.collectLatest

@ExperimentalAnimationApi
@Composable
fun ResetPasswordScreen(
    navController: NavHostController,
    resetPasswordViewModel: ResetPasswordViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()

    val isPasswordChanged by resetPasswordViewModel.isPasswordChanged

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = MaterialTheme.colors.screenBackgroundColor
    )
    systemUiController.setNavigationBarColor(
        color = MaterialTheme.colors.screenBackgroundColor
    )

    LaunchedEffect(key1 = true) {
        resetPasswordViewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvents.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short
                    )
                }
            }
            if (isPasswordChanged) {
                navController.popBackStack()
                navController.navigate(Screen.Login.route)
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = {
            SnackbarHost(hostState = it) { data ->
                Snackbar(
                    snackbarData = data,
                    backgroundColor = MaterialTheme.colors.snackBarBackgroundColor,
                )
            }
        },
        content = {
            ResetPasswordScreenDesign()
        }
    )
}

@ExperimentalAnimationApi
@Composable
fun ResetPasswordScreenDesign(
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.screenBackgroundColor)
            .padding(vertical = 30.dp, horizontal = EXTRA_LARGE_PADDING),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "No Worries!!",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.titleColor
        )
        Text(
            text = "This happens to all\n" +
                    "of us.",
            fontSize = 20.sp,
            color = MaterialTheme.colors.titleColor,
            textAlign = TextAlign.Center
        )
        ResetPasswordForm(
            modifier = Modifier.weight(1f)
        )
    }
}

@ExperimentalAnimationApi
@Composable
fun ResetPasswordForm(
    modifier: Modifier = Modifier,
    resetPasswordViewModel: ResetPasswordViewModel = hiltViewModel(),
) {
    var emailAddress by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var confirmPassword by remember {
        mutableStateOf("")
    }
    val isLoading by resetPasswordViewModel.isLoading

    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CustomTextField(
            title = "Email Address",
            text = emailAddress,
            hint = "johndoe@tracker.com",
            onTextChanged = {
                emailAddress = it
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email
            ),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            })
        )
        CustomTextField(
            title = "Password",
            text = password,
            hint = "password",
            isPasswordTextField = true,
            onTextChanged = {
                password = it
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            })
        )
        CustomTextField(
            title = "Confirm Password",
            text = confirmPassword,
            hint = "confirm password",
            isPasswordTextField = true,
            onTextChanged = {
                confirmPassword = it
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.clearFocus()
            })
        )
        Spacer(modifier = Modifier.height(LARGE_PADDING))
        AnimatedContent(
            targetState = isLoading,
            contentAlignment = Alignment.Center,
            transitionSpec = {
                fadeIn(animationSpec = tween(150, 150)) with
                        fadeOut(animationSpec = tween(150)) using
                        SizeTransform { initialSize, targetSize ->
                            if (targetState) {
                                keyframes {
                                    // Expand horizontally first.
                                    IntSize(targetSize.width, targetSize.height) at 150
                                    durationMillis = 300
                                }
                            } else {
                                keyframes {
                                    // Shrink vertically first.
                                    IntSize(initialSize.width, initialSize.height) at 150
                                    durationMillis = 300
                                }
                            }
                        }
            }
        ) { isLoading ->
            if (isLoading) {
                CustomCircularBar()
            } else {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Amber,
                        contentColor = MaterialTheme.colors.buttonContentColor
                    ),
                    onClick = {
                        resetPasswordViewModel.onEvent(
                            event = ResetPasswordEvents.ResetPassword(
                                emailAddress = emailAddress.trim().lowercase(),
                                password = password.trim(),
                                confirmPassword = confirmPassword.trim()
                            )
                        )
                    }
                ) {

                    Text(
                        text = "RESET",
                    )
                }
            }
        }

    }
}