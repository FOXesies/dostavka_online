package com.wayplaner.learn_room.auth.usecase

import com.wayplaner.learn_room.auth.domain.model.ValidationResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ValidateLogin @Inject constructor(){
    fun execute(login: String): ValidationResult {
        val message = when {
            login.isEmpty() -> "Пустое поле"
            login.length < 3 -> "Логин должен быть не менее 3 символов"
            login.length > 20 -> "Логин должен быть не более 20 символов"
            !(login.any { it.isDigit() } && login.any { it.isLetter() }) -> "Пароль должен содержать как минимум одну букву и цифру"
            else -> null
        }
        return ValidationResult(message == null, message)
    }
}