package com.wayplaner.learn_room.home.domain.model

data class OrganizationDTO(
    var idOrganization: Long? = null,
    var name: String,
    var phoneForUser: String,
    var cities: Map<String?, List<String?>>,
    var descriptions: String?,
    var category: List<CategoryDTO>,
    var rating: Double?,
    var ratingCount: Int?,
    var idImages: List<Image?>?
)

data class Image(val id: Long, val value: ByteArray)