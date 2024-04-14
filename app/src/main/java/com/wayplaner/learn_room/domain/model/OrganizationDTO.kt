package com.wayplaner.learn_room.domain.model

import java.io.InputStream

data class OrganizationDTO(
    var idOrganization: Long? = null,
    var name: String,
    var address: String,
    var phoneForUser: String,
    var city: String,
    var idImage: Long,
    var descriptions: String?,
    var category: MutableList<CategoryDTO>,
    var rating: Double?,
    var ratingCount: Int?,
    var images: MutableList<InputStream?> = mutableListOf()
)