package com.example.vrades.utils

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import com.example.vrades.utils.UIUtils.toast
import com.google.android.material.textfield.TextInputEditText

data class LoginValidator(
    val context: Context,
    val email: TextInputEditText,
    val password: TextInputEditText? = null,
    val repeatPassword: TextInputEditText? = null,
    val name: TextInputEditText? = null
) {
    lateinit var emailInput: String
    lateinit var passwordInput: String
    lateinit var userInput: String
    lateinit var repeatPasswordInput: String

    fun validateEmail(): Boolean {
        emailInput = email.text.toString().trim()
        if (emailInput.isEmpty()) {
            Toast.makeText(context, "Please enter your e-mail address.", Toast.LENGTH_SHORT).show()
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            Toast.makeText(context, "Invalid e-mail address.", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    fun validateEmailResend(): Boolean {
        emailInput = email.text.toString().trim()
        if (emailInput.isEmpty()) {
            Toast.makeText(context, "Please enter your e-mail address.", Toast.LENGTH_SHORT).show()
            return false
        } else if (emailInput.length < 4) {
            toast(context, "E-mail address too short! Try a valid address.")
        }
        return true
    }

    fun validatePassword(): Boolean {
        passwordInput = password!!.text.toString().trim()
        if (passwordInput.isEmpty()) {
            Toast.makeText(context, "Please enter your password.", Toast.LENGTH_SHORT).show()
            return false
        } else if (passwordInput.length < 6) {
            Toast.makeText(context, "Password too short", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    fun validateName(): Boolean {
        userInput = name?.text.toString().trim()
        if (userInput.isEmpty()) {
            Toast.makeText(context, "Please enter an email.", Toast.LENGTH_SHORT).show()
            return false
        } else if (userInput.length < 2) {
            Toast.makeText(context, "Username too short", Toast.LENGTH_SHORT).show()
            return false
        }
        return true

    }

    fun validateRepeatPassword(): Boolean {
        repeatPasswordInput = repeatPassword?.text.toString().trim()
        if (repeatPasswordInput.isEmpty()) {
            Toast.makeText(context, "You have to fill this field.", Toast.LENGTH_SHORT).show()
            return false
        } else if (repeatPasswordInput != passwordInput) {
//            println("Passwords: $repeatPasswordInput, $passwordInput")
            Toast.makeText(context, "Passwords don't match", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }
}