package com.wayplaner.learn_room.admin.basic_info.domain.model

import com.wayplaner.learn_room.organization.model.CityOrganization

data class BasicInfoResponse(
    val idOrg: Long,
    val name: String,
    val phone: String,
    val description: String? = null,
    val locationAll: Map<String, MutableList<CityOrganization>> = mutableMapOf(),
    var idImages: MutableList<ImageDTO>? = null
)

data class ImageDTO(
    val id: Long? = null,
    var byteArray: ByteArray? = null,
    var main: Boolean = false
)