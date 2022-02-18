package com.manikandan.expensetracker.presentation.screens.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manikandan.expensetracker.domain.model.UiEvents
import com.manikandan.expensetracker.domain.usecases.UseCases
import com.manikandan.expensetracker.domain.validators.AuthenticationValidator
import com.manikandan.expensetracker.utils.NetworkResult
import com.manikandan.expensetracker.utils.password_hash.PasswordHash
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {
    private var _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _isUserLoggedIn = MutableStateFlow(false)
    val isUserLoggedIn: StateFlow<Boolean> = _isUserLoggedIn.asStateFlow()

    private val _eventFlow: MutableSharedFlow<UiEvents> = MutableSharedFlow()
    val eventFlow: SharedFlow<UiEvents> = _eventFlow.asSharedFlow()

    private fun generateHashedPassword(password: String): String {
        return PasswordHash.generateHashPassword(password = password)
    }

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.LoginUser -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _isLoading.value = true
                    val inputValidationResponse = AuthenticationValidator.loginFormValidation(
                        emailAddress = event.emailAddress,
                        password = event.password
                    )
                    if (inputValidationResponse.success) {
                        val hashedPassword = generateHashedPassword(password = event.password)
                        val userCredentials = mapOf(
                            "email_address" to event.emailAddress,
                            "password" to hashedPassword
                        )
                        val result = useCases.loginUserUseCase(userCredentials = userCredentials)
                        when (result) {
                            is NetworkResult.Success -> {
                                val data = result.data!!
                                val isLoginSuccess = result.data.success
                                if (isLoginSuccess) {
                                    val loggedInUser = data.user!!
                                    useCases.saveDataStoreUserCredentialsUseCase(
                                        user = loggedInUser
                                    )
                                    useCases.saveLoginStateUseCase(isLoginCompleted = isLoginSuccess)
                                    _isUserLoggedIn.value = isLoginSuccess
                                } else {
                                    _isLoading.value = false
                                    _eventFlow.emit(value = UiEvents.ShowSnackBar(message = data.message))
                                }
                            }
                            is NetworkResult.Error -> {
                                val errorData = result.errorData!!
                                _eventFlow.emit(value = UiEvents.ShowSnackBar(message = errorData.errorTitle))
                                _isLoading.value = false
                            }
                        }
                    } else {
                        _isLoading.value = false
                        _eventFlow.emit(value = UiEvents.ShowSnackBar(message = inputValidationResponse.errorMessage))
                    }
                }
            }
        }
    }
}

