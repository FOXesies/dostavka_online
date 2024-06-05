package com.wayplaner.learn_room.auth.domain.model.DTO

data class SingUpRequest(
    var phone: String,
    var name: String,
    var city: String,
    var password: String,
)