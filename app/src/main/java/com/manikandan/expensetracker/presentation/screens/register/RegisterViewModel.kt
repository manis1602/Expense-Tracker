package com.manikandan.expensetracker.presentation.screens.register

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
class RegisterViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    private val _isUserRegistered = mutableStateOf(false)
    val isUserRegistered: State<Boolean> = _isUserRegistered

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private var _eventFlow: MutableSharedFlow<UiEvents> = MutableSharedFlow()
    val eventFlow: SharedFlow<UiEvents> = _eventFlow.asSharedFlow()

    private fun generateHashedPassword(password: String): String {
        return PasswordHash.generateHashPassword(password = password)
    }

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.RegisterUser -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _isLoading.value = true
                    val inputValidationResponse =
                        AuthenticationValidator.registerFormValidation(user = event.user)
                    if (inputValidationResponse.success) {
                        val user = event.user.copy(
                            password = generateHashedPassword(event.user.password)
                        )
                        val result = useCases.registerUserUseCase(user = user)
                        when (result) {
                            is NetworkResult.Success -> {
                                val data = result.data!!
                                val isUserRegistered = data.success
                                if (isUserRegistered) {
                                    _isUserRegistered.value = isUserRegistered
                                    _eventFlow.emit(value = UiEvents.ShowSnackBar(message = "Registration Successful !!"))
                                    _isLoading.value = false
                                } else {
                                    _isLoading.value = false
                                    _eventFlow.emit(value = UiEvents.ShowSnackBar(message = data.message))
                                }
                            }
                            is NetworkResult.Error -> {
                                val errorData = result.errorData!!
                                _eventFlow.emit(value = UiEvents.ShowSnackBar(message = errorData.errorTitle!!))
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