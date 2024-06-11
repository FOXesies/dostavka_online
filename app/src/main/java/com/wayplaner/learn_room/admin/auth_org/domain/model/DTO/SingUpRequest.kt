package com.wayplaner.learn_room.auth.domain.model.DTO

import com.wayplaner.learn_room.organization.model.CityOrganization

data class SingUpOrgRequest(
    var city: String? = null,
    var address: CityOrganization?,
    var login: String? = null,
    var name: String? = null,
    var phoneUser: String? = null,
    var password: String? = null,
)