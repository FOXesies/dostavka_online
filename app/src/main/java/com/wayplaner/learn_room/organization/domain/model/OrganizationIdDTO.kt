package com.wayplaner.learn_room.organization.model

import com.wayplaner.learn_room.home.domain.model.Image
import com.wayplaner.learn_room.organization.domain.model.ResponseProductOrg

data class OrganizationIdDTO(
    var idOrganization: Long? = null,
    var name: String,
    var phoneForUser: String,
    var descriptions: String?,
    var category: List<String>,
    var locationsAll: Map<String, MutableList<CityOrganization>> = mutableMapOf(),
    var products: Map<String, List<ResponseProductOrg>>,
    var rating: Double?,
    var ratingCount: Int?,
    var isFavorite: Boolean = false,
    var idImages: List<Image?>?
)