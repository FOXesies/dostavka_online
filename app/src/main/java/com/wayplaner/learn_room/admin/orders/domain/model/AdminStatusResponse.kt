package com.wayplaner.learn_room.admin.orders.domain.model

import com.wayplaner.learn_room.createorder.domain.model.StatusOrder

data class AdminStatusResponse (
    val id: Long,
    val status: StatusOrder
)