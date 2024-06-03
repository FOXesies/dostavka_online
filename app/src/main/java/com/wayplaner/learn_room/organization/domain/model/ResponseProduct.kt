package com.wayplaner.learn_room.organization.domain.model

import com.wayplaner.learn_room.home.domain.model.Image

data class ResponseProductOrg (
    var id: Long,
    var name: String,
    var price: Double?,
    var image: Image? = null,
    var description: String?,
)