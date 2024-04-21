package org.example.order.model

import com.wayplaner.learn_room.createorder.domain.model.Address

data class Order(
    var idUser: Long = 1,
    var idOrganization: String = "",

    var addressUser: Address? = null,
    var phoneUser: String? = null,
    var toTimeDelivery: String? = "now",
    var productOrder: List<ProductInOrder> = mutableListOf(),

    var podezd: String = "",
    var homephome: String = "",
    var appartamnet: String = "",
    var level: String = "",

    var summ: Double? = null,
    var comment: String = ""
)