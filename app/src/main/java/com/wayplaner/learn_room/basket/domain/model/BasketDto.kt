package com.wayplaner.learn_room.basket.domain.model

import com.wayplaner.learn_room.order.data.model.IdsProductInBasket

data class BasketDto (
    val idRestoraunt: Long? = null,
    var productsPick: MutableList<IdsProductInBasket> = mutableListOf(),
    var summ: Double = 0.0
)