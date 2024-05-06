package com.wayplaner.learn_room.createorder.domain.model

data class OrderStateFrom(
    var order: Order? = null,
    var errorPhone: String? = null,
    var errorAddress: String? = null
)