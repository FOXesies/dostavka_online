package com.wayplaner.learn_room.auth.domain.model

data class Customer(
    var profileUUID: String? = null,
    var city: String? = null,
    var email: String? = null,
    var name: String? = null,
    var address: String? = null,
    var phone: String? = null,
)