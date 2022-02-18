package com.manikandan.expensetracker.utils.password_hash

import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

object PasswordHash{
    fun generateHashPassword(password: String): String{
        val salt = ("Expense" + password + "Tracker").toByteArray(Charsets.UTF_8)

        val keySpec = PBEKeySpec(password.toCharArray(), salt, 5000, 64)
        val secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")

        val hashPassword = secretKeyFactory.generateSecret(keySpec).encoded

        return hashPassword.toHex()

    }

    private fun ByteArray.toHex(): String{
        return this.joinToString(separator = ""){
            "%02x".format(it)
        }
    }
}