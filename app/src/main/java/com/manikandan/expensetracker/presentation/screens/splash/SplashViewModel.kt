package com.manikandan.expensetracker.presentation.screens.splash

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manikandan.expensetracker.domain.usecases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    private val _isOnBoardingCompleted = MutableStateFlow(false)
    val isOnBoardingCompleted: StateFlow<Boolean> = _isOnBoardingCompleted

    private val _isAlreadyLoggedIn = MutableStateFlow(false)
    val isAlreadyLoggedIn: StateFlow<Boolean> = _isAlreadyLoggedIn

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _isOnBoardingCompleted.value = useCases.readOnBoardingStateUseCase().stateIn(viewModelScope).value
            _isAlreadyLoggedIn.value = useCases.readLoginStateUseCase().stateIn(viewModelScope).value
        }
    }
}