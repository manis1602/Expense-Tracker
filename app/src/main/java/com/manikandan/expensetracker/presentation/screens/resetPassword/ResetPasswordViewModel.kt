package com.manikandan.expensetracker.presentation.screens.resetPassword

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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    private val _isPasswordChanged = mutableStateOf(false)
    val isPasswordChanged: State<Boolean> = _isPasswordChanged

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _isPasswordSame = mutableStateOf(false)
    val isPasswordSame: State<Boolean> = _isPasswordSame

    private val _eventFlow: MutableSharedFlow<UiEvents> = MutableSharedFlow()
    val eventFlow: SharedFlow<UiEvents> = _eventFlow.asSharedFlow()

    private fun generateHashedPassword(password: String): String {
        return PasswordHash.generateHashPassword(password = password)
    }

    fun onEvent(event: ResetPasswordEvents) {
        when (event) {
            is ResetPasswordEvents.ResetPassword -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _isLoading.value = true
                    val inputValidationResponse = AuthenticationValidator.resetPasswordFormValidation(
                        emailAddress = event.emailAddress,
                        password = event.password,
                        confirmPassword = event.confirmPassword
                    )
                    if (inputValidationResponse.success){
                        val hashedPassword = generateHashedPassword(password = event.password)
                        val userCredentials = mapOf(
                            "email_address" to event.emailAddress,
                            "password" to hashedPassword
                        )
                        val result =
                            useCases.resetPasswordUseCase(userCredentials = userCredentials)
                        when (result) {
                            is NetworkResult.Success -> {
                                val data = result.data!!
                                val isRequestSuccessful = data.success
                                if (isRequestSuccessful) {
                                    _isPasswordChanged.value = isRequestSuccessful
                                    _eventFlow.emit(value = UiEvents.ShowSnackBar(message = "Password changed successfully !!"))
                                    _isLoading.value = false
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
                    }else{
                        _isLoading.value = false
                        _eventFlow.emit(value = UiEvents.ShowSnackBar(message = inputValidationResponse.errorMessage))
                    }

                }
            }
        }
    }
}