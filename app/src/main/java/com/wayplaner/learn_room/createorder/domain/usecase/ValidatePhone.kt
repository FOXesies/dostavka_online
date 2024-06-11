package com.wayplaner.learn_room.createorder.domain.usecase

import com.wayplaner.learn_room.auth.domain.model.ValidationResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ValidatePhone @Inject constructor(){
    fun execute(phone: String): ValidationResult {
        val message = when {
            phone.isEmpty() -> "Пустое поле"
            phone.length != 11 -> "Должно быть 11 символов"
            else -> null
        }
        return ValidationResult(message == null, message)
    }
}