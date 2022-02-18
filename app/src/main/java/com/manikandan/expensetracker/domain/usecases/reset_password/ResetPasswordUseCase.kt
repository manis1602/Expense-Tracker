package com.manikandan.expensetracker.domain.usecases.reset_password

import com.manikandan.expensetracker.domain.model.AuthenticationResponse
import com.manikandan.expensetracker.domain.repository.Repository
import com.manikandan.expensetracker.utils.NetworkResult

class ResetPasswordUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(userCredentials: Map<String, String>): NetworkResult<AuthenticationResponse>{
        return repository.resetUserPassword(userCredentials = userCredentials)
    }
}