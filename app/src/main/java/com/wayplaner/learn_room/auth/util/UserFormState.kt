package com.wayplaner.learn_room.auth.util

data class UserFormState(
    var city: String = "",
    var errorCity: String? = null,
    var name: String = "",
    var errorName: String? = null,
    var phone: String = "",
    var errorPhone: String? = null,
    var password: String = "",
    var passwordError: String? = null,
    var passwordRepeat: String = "",
    var passwordRepeatError: String? = null,

    var baseError: String? = null
)