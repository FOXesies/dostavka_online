package com.wayplaner.learn_room.orderlist.domain.model

import com.wayplaner.learn_room.createorder.domain.model.Address
import org.example.order.model.ProductInOrder

data class CanceledOrder(
    val orderId: Long = 0,
    var canceled_comment: String? = null,

    var idDriver: Long? = null,
    var idUser: Long? = null,
    var idOrganization: String? = null,

    var uuid: Long? = null,

    var addressUser: Address? = null,
    var idLocation: Long? = null,
    var phoneUser: String? = null,
    var toTimeDelivery: String? = "now",
    var CanceledTime: String? = "now",

    var productOrder: List<ProductInOrder> = mutableListOf(),

    var podezd: String? = null,
    var homephome: String? = null,
    var appartamnet: String? = null,
    var level: String? = null,

    var summ: Double? = null,
    var comment: String? = null
)