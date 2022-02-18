package com.manikandan.expensetracker.presentation.screens.login

import androidx.compose.animation.*
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.tooling.preview.Preview
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
fun LoginScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel = hiltViewModel()
) {

    val isUserLoggedIn by loginViewModel.isUserLoggedIn.collectAsState()
    val scaffoldState = rememberScaffoldState()

    if (isUserLoggedIn) {
        navController.navigate(Screen.Home.route){
            launchSingleTop = true
            popUpTo(Screen.Login.route){
                inclusive = true
            }
        }
    }

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = MaterialTheme.colors.screenBackgroundColor
    )
    systemUiController.setNavigationBarColor(
        color = MaterialTheme.colors.screenBackgroundColor
    )

    LaunchedEffect(key1 = true) {
        loginViewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvents.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(message = event.message)
                }
            }
            // If you want to show snack bar for successful login
//            if (isUserLoggedIn) {
//                navController.popBackStack()
//                navController.navigate(Screen.Home.route)
//            }
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
            LoginScreenDesign(
                onRegisterTextClicked = {
                    navController.navigate(Screen.Register.route)
                },
                onForgotPasswordClicked = {
                    navController.navigate(Screen.ResetPassword.route)
                }
            )

        }
    )

}

@ExperimentalAnimationApi
@Composable
fun LoginScreenDesign(
    onRegisterTextClicked: () -> Unit,
    onForgotPasswordClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.screenBackgroundColor)
            .padding(vertical = 60.dp, horizontal = EXTRA_LARGE_PADDING),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Hello Again !!",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.titleColor
        )
        Text(
            text = "Welcome back, you've\n" +
                    "been missed.",
            fontSize = 20.sp,
            color = MaterialTheme.colors.titleColor,
            textAlign = TextAlign.Center
        )
        LoginForm(
            modifier = Modifier.weight(1f),
            onForgotPasswordClicked = onForgotPasswordClicked
        )
        Text(
            text = "Don't have an account ?",
            fontSize = MaterialTheme.typography.subtitle1.fontSize,
            color = MediumGray
        )
        Text(
            modifier = Modifier
                .padding(top = 10.dp)
                .clickable {
                    onRegisterTextClicked()
                },
            text = "Register",
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.subtitle1.fontSize,
            color = MaterialTheme.colors.onBoardingTitleColor
        )
    }
}

@ExperimentalAnimationApi
@Composable
fun LoginForm(
    modifier: Modifier,
    loginViewModel: LoginViewModel = hiltViewModel(),
    onForgotPasswordClicked: () -> Unit
) {
    var emailAddress by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    val isLoading by loginViewModel.isLoading

    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomTextField(
            title = "Email Address",
            text = emailAddress,
            hint = "expense@tracker.com",
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
            hint = "Password",
            isPasswordTextField = true,
            onTextChanged = {
                password = it
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
                        backgroundColor = MaterialTheme.colors.buttonColor,
                        contentColor = MaterialTheme.colors.buttonContentColor
                    ),
                    onClick = {
                        loginViewModel.onEvent(
                            event = LoginEvent.LoginUser(
                                emailAddress = emailAddress.trim().lowercase(),
                                password = password.trim()
                            )
                        )
                    }
                ) {

                    Text(
                        text = "LOGIN",
                    )
                }
            }
        }
        Text(
            modifier = Modifier
                .padding(top = LARGE_PADDING)
                .clickable {
                    onForgotPasswordClicked()
                },
            text = "Forgot Password",
            fontSize = MaterialTheme.typography.subtitle1.fontSize,
            color = MaterialTheme.colors.secondaryColor
        )
    }
}


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
}

//@Preview(showBackground = true)
//@Composable
//fun CustomTextFieldPreview() {
//    CustomTextField(title = "Email Address", text = "", hint = "Email Address", onTextChanged = {})
//}