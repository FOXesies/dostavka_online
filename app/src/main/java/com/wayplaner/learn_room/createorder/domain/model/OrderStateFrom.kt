package com.wayplaner.learn_room.createorder.domain.model

import org.example.order.model.Order
import org.example.order.model.ProductInOrder

data class OrderStateFrom(
    var order: Order? = null,
    var errorPhone: String? = null,
    var errorAddress: String? = null
)