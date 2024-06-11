package com.wayplaner.learn_room.auth.domain.model

import com.wayplaner.learn_room.createorder.domain.model.Address

data class OrganizationResponse(
    var orgId: String? = null,
    var city: String? = null,
    var address: Address?,
    var name: String? = null,
    var phoneUser: String? = null,
)