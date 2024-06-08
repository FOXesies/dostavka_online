package com.wayplaner.learn_room.admin.menu.data.model

import com.wayplaner.learn_room.admin.basic_info.domain.model.ImageDTO

data class ResponseProduct (
    var id: Long? = null,
    var name: String? = null,
    var price: Double? = null,
    var image: List<ImageDTO?> = listOf(),
    var description: String? = null,
)