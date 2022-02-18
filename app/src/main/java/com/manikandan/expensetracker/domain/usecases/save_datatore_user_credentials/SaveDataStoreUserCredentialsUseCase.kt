package com.manikandan.expensetracker.domain.usecases.save_datatore_user_credentials

import com.manikandan.expensetracker.domain.model.User
import com.manikandan.expensetracker.domain.repository.Repository

class SaveDataStoreUserCredentialsUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(user: User) {
        repository.saveUserCredentials(
            user = user
        )
    }
}