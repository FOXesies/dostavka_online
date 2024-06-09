package com.wayplaner.learn_room.orderlist.domain.model

data class CancelOrderPreview (
    val comment: String? = null,
    val timeCandeled: String? = null,
    val orderPreview: OrderPreviewDTO? = null
)