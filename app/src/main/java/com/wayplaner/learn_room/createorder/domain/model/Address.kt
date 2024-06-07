package com.wayplaner.learn_room.createorder.domain.model

data class Address(
    var displayText: String?,
    var lat: Double?,
    var lon: Double?,

    var podezd: String = "",
    var homephome: String = "",
    var appartamnet: String = "",
    var level: String = "",
)