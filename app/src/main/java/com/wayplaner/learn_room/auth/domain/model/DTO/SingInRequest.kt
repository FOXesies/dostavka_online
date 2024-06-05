package com.wayplaner.learn_room.auth.domain.model.DTO

data class SingInRequest (
    var phone: String,
    var password: String
)