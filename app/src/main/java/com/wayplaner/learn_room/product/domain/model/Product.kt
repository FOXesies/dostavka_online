package com.wayplaner.learn_room.product.domain.model

import com.wayplaner.learn_room.home.domain.model.Image

data class Product(
    var idProduct: Long? = null,
    var name: String,
    var price: Double?,
    var weight: Float?,
    var description: String?,
    var images: List<Image>? = null
)

data class ProductDToUpdate(
    var id: Long? = null,
    var name: String,
    var price: Double?,
    var weight: Float?,
    var image: List<Image>? = null,
    var description: String?,
    val category: String
)