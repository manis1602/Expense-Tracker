package com.manikandan.expensetracker.domain.validators

import android.util.Patterns
import com.manikandan.expensetracker.domain.model.InputValidationResponse
import com.manikandan.expensetracker.domain.model.User

object AuthenticationValidator {

    fun loginFormValidation(emailAddress: String, password: String): InputValidationResponse {
        val errorMessage = when {
            emailAddress.isEmpty() || password.isEmpty() -> "Email Address or Password field is empty!"
            !Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches() -> "Invalid email address!"
            password.contains(" ") -> "Password should not contain empty spaces!"
            password.length < 8 -> "Password Should be minimum 8 characters!"
            else -> ""
        }
        return if (errorMessage.isEmpty()) {
            InputValidationResponse(success = true)
        } else {
            InputValidationResponse(success = false, errorMessage = errorMessage)
        }
    }

    fun registerFormValidation(user: User): InputValidationResponse {
        val errorMessage = when {
            user.userName.isEmpty() || user.emailAddress.isEmpty() || user.password.isEmpty() || user.gender.isEmpty() -> "Some field(s) are empty!"
            user.userName.length < 2 -> "Name should contain at least 2 characters!"
            !Patterns.EMAIL_ADDRESS.matcher(user.emailAddress).matches() -> "Invalid email address!"
            user.password.contains(" ") -> "Password should not contain empty spaces!"
            user.password.length < 8 -> "Password Should be minimum 8 characters!"
            else -> ""
        }
        return if (errorMessage.isEmpty()) {
            InputValidationResponse(success = true)
        } else {
            InputValidationResponse(success = false, errorMessage = errorMessage)
        }
    }

    fun resetPasswordFormValidation(
        emailAddress: String,
        password: String,
        confirmPassword: String
    ): InputValidationResponse {
        val errorMessage = when {
            emailAddress.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() -> "Some field(s) are empty!"
            !Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches() -> "Invalid email address!"
            password.contains(" ") -> "Password should not contain empty spaces!"
            password.length < 8 -> "Password Should be minimum 8 characters!"
            password != confirmPassword -> "Passwords do not match."
            else -> ""
        }
        return if (errorMessage.isEmpty()) {
            InputValidationResponse(success = true)
        } else {
            InputValidationResponse(success = false, errorMessage = errorMessage)
        }
    }
}