package com.wayplaner.learn_room.home.domain.model

import com.wayplaner.learn_room.home.domain.model.CategoryDTO
import okhttp3.ResponseBody

data class OrganizationDTO(
    var idOrganization: Long? = null,
    var name: String,
    var address: String,
    var phoneForUser: String,
    var city: String,
    var idImage: Long? = null,
    var descriptions: String?,
    var category: List<CategoryDTO>,
    var rating: Double?,
    var ratingCount: Int?,
    var images: MutableList<ResponseBody?> = mutableListOf()
)