package com.wayplaner.learn_room.domain.model

data class Product(
    var idProduct: Long? = null,
    var name: String,
    var price: Double?,
    var weight: Float?,
    var description: String?,
    var imageProduct: ByteArray?
)
