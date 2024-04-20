package com.wayplaner.learn_room.basket.domain.model

import com.wayplaner.learn_room.order.data.model.ProductInBasket
import com.wayplaner.learn_room.product.domain.model.Product

data class BasketDto (
    val idRestoraunt: Long? = null,
    var productsPick: MutableList<ProductInBasket> = mutableListOf(),
    var summ: Double = 0.0
)