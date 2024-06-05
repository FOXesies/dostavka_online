package com.wayplaner.learn_room.auth.usecase

import com.wayplaner.learn_room.auth.domain.model.ValidationResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ValidatePhone @Inject constructor(){
    fun execute(phone: String): ValidationResult {
        val message = when {
            phone.isEmpty() -> "Пустое поле"
            !phone.replaceFirst("+", "").matches(Regex("[0-9]+")) -> "Должны быть только числа!"
            (phone[0] != '+' && !phone.replaceFirst("+", "").matches(Regex("[0-9]"))) -> "Начните строчку с +7.."
            phone.length - 1 != 11 -> "Должно быть 11 символов"
            else -> null
        }
        return ValidationResult(message == null, message)
    }
}