package com.wayplaner.learn_room.auth.domain.model.DTO

data class UserResponse(
    var profileUUID: Long,
    var city: String,
    var name: String,
    var phone: String,
)