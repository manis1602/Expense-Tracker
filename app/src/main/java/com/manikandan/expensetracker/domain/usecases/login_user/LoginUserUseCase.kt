package com.manikandan.expensetracker.domain.usecases.login_user

import com.manikandan.expensetracker.domain.model.AuthenticationResponse
import com.manikandan.expensetracker.domain.repository.Repository
import com.manikandan.expensetracker.utils.NetworkResult

class LoginUserUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(userCredentials: Map<String, String>): NetworkResult<AuthenticationResponse>{
        return repository.loginUser(userCredentials = userCredentials)
    }
}