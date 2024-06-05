package com.wayplaner.learn_room.auth.usecase

import com.wayplaner.learn_room.auth.domain.model.ValidationResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ValidateName @Inject constructor(){
    fun execute(name: String): ValidationResult {
        val errorMessage = when {
            name.length < 2 -> "Имя короткое"
            name.length < 25 -> "Имя слишком длиное"
            else -> null
        }
        return ValidationResult(errorMessage == null, errorMessage ?: "")
    }
}