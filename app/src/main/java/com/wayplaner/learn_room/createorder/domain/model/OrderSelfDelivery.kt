package com.wayplaner.learn_room.createorder.domain.model

import org.example.order.model.ProductInOrder

data class OrderSelfDelivery (
    var idOrderSelf: Long? = null,
    var idUser: Long? = null,
    var idOrganization: String = "",

    var uuid: UUIDCustom? = null,

    var idLocation: Long? = null,
    var phoneUser: String? = null,
    var toTimeCooling: String? = "now",
    var productOrder: List<ProductInOrder> = mutableListOf(),
    var status: StatusOrder? = null,

    var summ: Double? = null,
    var comment: String = ""
)