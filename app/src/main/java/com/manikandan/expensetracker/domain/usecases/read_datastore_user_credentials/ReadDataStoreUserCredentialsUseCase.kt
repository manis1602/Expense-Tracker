package com.manikandan.expensetracker.domain.usecases.read_datastore_user_credentials

import com.manikandan.expensetracker.domain.model.User
import com.manikandan.expensetracker.domain.repository.Repository

class ReadDataStoreUserCredentialsUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(): User {
        return repository.readUserCredentials()
    }
}