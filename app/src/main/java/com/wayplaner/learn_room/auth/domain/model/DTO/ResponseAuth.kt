package com.wayplaner.learn_room.auth.domain.model.DTO

data class ResponseAuth(
    var message: String? = null,
    var userResponse: UserResponse? = null
)