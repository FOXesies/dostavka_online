package com.wayplaner.learn_room.createorder.domain.model

import com.wayplaner.learn_room.utils.CustomerAccount
import org.example.order.model.ProductInOrder

data class OrderSelfDelivery (
    var idOrderSelf: Long? = null,
    var idUser: Long? = CustomerAccount.info!!.profileUUID,
    var idOrganization: String = "",

    var uuid: UUIDCustom? = null,

    var idLocation: Long? = 1,
    var phoneUser: String? = null,
    var fromTimeCooking: String? = null,
    var toTimeCooking: String? = "now",
    var productOrder: List<ProductInOrder> = mutableListOf(),
    var status: StatusOrder? = null,

    var summ: Double? = null,
    var comment: String = ""
)