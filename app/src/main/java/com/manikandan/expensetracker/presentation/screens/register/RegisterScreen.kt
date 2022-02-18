package com.manikandan.expensetracker.presentation.screens.register

import androidx.compose.animation.*
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import com.manikandan.expensetracker.domain.model.ExpenseDropDownData
import com.manikandan.expensetracker.domain.model.UiEvents
import com.manikandan.expensetracker.domain.model.User
import com.manikandan.expensetracker.navigation.Screen
import com.manikandan.expensetracker.presentation.common.CustomCircularBar
import com.manikandan.expensetracker.presentation.common.CustomTextField
import com.manikandan.expensetracker.presentation.common.ExpenseDropDownBox
import com.manikandan.expensetracker.presentation.common.util.ExpenseDropDownType
import com.manikandan.expensetracker.ui.theme.*
import kotlinx.coroutines.flow.collectLatest

@ExperimentalAnimationApi
@Composable
fun RegisterScreen(
    navController: NavHostController,
    registerViewModel: RegisterViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()

    val isUserRegistered by registerViewModel.isUserRegistered

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = MaterialTheme.colors.screenBackgroundColor
    )
    systemUiController.setNavigationBarColor(
        color = MaterialTheme.colors.screenBackgroundColor
    )

    LaunchedEffect(key1 = true) {
        registerViewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvents.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short
                    )
                }
            }
            if (isUserRegistered) {
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
            RegisterScreenDesign(
                onLoginTextClicked = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Login.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    )

}

@ExperimentalAnimationApi
@Composable
fun RegisterScreenDesign(
    onLoginTextClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.screenBackgroundColor)
            .padding(vertical = 30.dp, horizontal = EXTRA_LARGE_PADDING),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Greetings",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.titleColor
        )
        Text(
            text = "Delighted to  have you\n" +
                    "on board.",
            fontSize = 20.sp,
            color = MaterialTheme.colors.titleColor,
            textAlign = TextAlign.Center
        )
        RegisterForm(
            modifier = Modifier.weight(1f),
        )
        Text(
            text = "Already have an account ?",
            fontSize = MaterialTheme.typography.subtitle1.fontSize,
            color = MediumGray
        )
        Text(
            modifier = Modifier
                .padding(top = 10.dp)
                .clickable {
                    onLoginTextClicked()
                },
            text = "Login",
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.subtitle1.fontSize,
            color = MaterialTheme.colors.secondaryColor
        )
    }
}

@ExperimentalAnimationApi
@Composable
fun RegisterForm(
    modifier: Modifier = Modifier,
    registerViewModel: RegisterViewModel = hiltViewModel(),
) {
    var name by remember {
        mutableStateOf("")
    }
    var emailAddress by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var gender by remember {
        mutableStateOf("")
    }
    val isLoading by registerViewModel.isLoading

    val focusManager = LocalFocusManager.current

    val scrollState = rememberScrollState()
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CustomTextField(
            title = "Name",
            text = name,
            hint = "John Doe",
            onTextChanged = {
                name = it
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            })
        )
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
                focusManager.clearFocus()
            })
        )
        ExpenseDropDownBox(
            dropDownData = ExpenseDropDownData(
                dropDownType = ExpenseDropDownType.GENDER,
                dropDownList = listOf("Male", "Female", "Not to mention"),
                dropDownTitle = "Gender",
                hint = "Male",
                selectedText = gender
            ),
            scrollState = scrollState,
            valueChange = {
                gender = it
            }
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
                        val user = User(
                            userName = name.trim(),
                            password = password.trim(),
                            emailAddress = emailAddress.trim().lowercase(),
                            gender = gender.trim()
                        )
                        registerViewModel.onEvent(event = RegisterEvent.RegisterUser(user = user))
                    }
                ) {

                    Text(
                        text = "REGISTER",
                    )
                }
            }
        }

    }
}