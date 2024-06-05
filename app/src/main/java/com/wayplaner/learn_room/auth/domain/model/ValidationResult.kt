package com.wayplaner.learn_room.auth.domain.model

data class ValidationResult(
    val successful: Boolean,
    val errormessage: String? = null
)