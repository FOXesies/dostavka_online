package com.wayplaner.learn_room.admin.basic_info.domain.model

import java.io.InputStream

data class OrganizationResponse(
    val idorganization: Long? = null,
    var nameOrganization: String? = null,
    val idImage: InputStream? = null,
)