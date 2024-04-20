package com.wayplaner.learn_room.order.data.model

import com.wayplaner.learn_room.product.domain.model.Product

data class BasketItem(
    var idUser: Long? = null,
    var idRestoraunt: Long? = null,
    var productsPick: MutableList<ProductInBasket> = mutableListOf(),
    var summ: Double = 0.0
)

data class ProductInBasket(
    var product: Product? = null,
    var count: Int = 0
)