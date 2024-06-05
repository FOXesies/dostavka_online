package com.wayplaner.learn_room.auth.usecase

import com.wayplaner.learn_room.auth.domain.model.ValidationResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ValidateCity @Inject constructor(){
    fun execute(info: String): ValidationResult {
        val message = when{
            info.isEmpty() -> "Пустое поле"
            else -> null
        }
        return ValidationResult(message == null, message)
    }
}