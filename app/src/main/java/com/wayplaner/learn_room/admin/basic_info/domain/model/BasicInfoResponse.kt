package com.wayplaner.learn_room.admin.basic_info.domain.model

import com.wayplaner.learn_room.organization.model.CityOrganization

data class BasicInfoResponse(
    val idOrg: Long, val name: String, val phone: String, val description: String, val locationAll: Map<String, List<CityOrganization>>
)