package com.wayplaner.learn_room.home.domain.model

data class OrganizationDTO(
    var idOrganization: Long? = null,
    var name: String,
    var phoneForUser: String,
    var cities: Map<String?, List<String?>>,
    var idImages: Image,
    var descriptions: String?,
    var category: Set<CategoryDTO>,
    var rating: Double?,
    var ratingCount: Int?,
)
data class Image(val id: Long? = null, var value: ByteArray?, var main: Boolean = false)