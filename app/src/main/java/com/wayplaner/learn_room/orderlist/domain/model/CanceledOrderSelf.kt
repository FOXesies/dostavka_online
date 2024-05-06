package com.wayplaner.learn_room.orderlist.domain.model

import com.wayplaner.learn_room.createorder.domain.model.StatusOrder
import org.example.order.model.ProductInOrder

data class CanceledOrderSelf (
    var idOrderSelf: Long,
    var idUser: Long = 1,
    var idOrganization: String = "",
    var canceled_comment: String? = null,

    var uuid: Long? = null,

    var idLocation: Long? = null,
    var phoneUser: String? = null,
    var toTimeCooling: String? = "now",
    var CanceledTime: String? = "now",

    var productOrder: List<ProductInOrder> = mutableListOf(),
    val status: StatusOrder? = null,

    var summ: Double? = null,
    var comment: String = "",

    )