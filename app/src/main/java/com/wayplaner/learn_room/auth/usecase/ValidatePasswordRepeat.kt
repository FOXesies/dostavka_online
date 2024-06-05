package ru.comet.android.auth.domain.usecase

import com.wayplaner.learn_room.auth.domain.model.ValidationResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ValidatePasswordRepeat @Inject constructor() {
    fun execute(password: String, repeatPassword: String): ValidationResult {
        if(password != repeatPassword)
            return ValidationResult(false, "Пароли не совпадают")
        return ValidationResult(true)
    }
}