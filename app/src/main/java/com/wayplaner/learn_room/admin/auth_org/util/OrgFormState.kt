package com.wayplaner.learn_room.admin.auth_org.util

import com.wayplaner.learn_room.organization.model.CityOrganization

data class OrgFormState(
    var city: String = "",
    var errorCity: String? = null,
    var address: CityOrganization? = null,
    var errorAddress: String? = null,
    var name: String = "",
    var errorName: String? = null,
    var login: String = "",
    var errorLogin: String? = null,
    var phone: String = "",
    var errorPhone: String? = null,
    var password: String = "",
    var passwordError: String? = null,
    var passwordRepeat: String = "",
    var passwordRepeatError: String? = null,

    var baseError: String? = null
)