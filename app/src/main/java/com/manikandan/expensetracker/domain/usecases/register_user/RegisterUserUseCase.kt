package com.manikandan.expensetracker.domain.usecases.register_user

import com.manikandan.expensetracker.domain.model.AuthenticationResponse
import com.manikandan.expensetracker.domain.model.User
import com.manikandan.expensetracker.domain.repository.Repository
import com.manikandan.expensetracker.utils.NetworkResult

class RegisterUserUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(user: User): NetworkResult<AuthenticationResponse> {
        return repository.registerUser(user = user)
    }
}