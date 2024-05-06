package com.wayplaner.learn_room.createorder.domain.model

import org.example.order.model.ProductInOrder

data class Order(
    var orderId: Long? = null,
    var idUser: Long = 1,
    var idOrganization: String = "",

    var uuid: UUIDCustom? = null,

    var addressUser: Address? = null,
    var idLocation: Long? = null,
    var phoneUser: String? = null,
    var toTimeDelivery: String? = "now",
    var productOrder: List<ProductInOrder> = mutableListOf(),
    var status: StatusOrder? = null,

    var podezd: String = "",
    var homephome: String = "",
    var appartamnet: String = "",
    var level: String = "",

    var summ: Double? = null,
    var comment: String = ""
)