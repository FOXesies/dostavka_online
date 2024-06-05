package ru.comet.android.auth.domain.usecase

import com.wayplaner.learn_room.auth.domain.model.ValidationResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ValidatePassword @Inject constructor() {
    fun execute(password: String): ValidationResult {
        if(password.length < 8) {
            return ValidationResult(
                false,
                "Пароль должен быть длиннее 8 символов"
            )
        }
        val containsLettersAndDigits = password.any { it.isDigit() } &&
                password.any { it.isLetter() }
        if(!containsLettersAndDigits) {
            return ValidationResult(
                false,
                "Пароль должен содержать как минимум одну букву и цифру"
            )
        }
        return ValidationResult(
            true
        )
    }

}