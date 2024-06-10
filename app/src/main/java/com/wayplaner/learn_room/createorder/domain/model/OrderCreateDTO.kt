package com.wayplaner.learn_room.createorder.domain.model

import com.wayplaner.learn_room.utils.CustomerAccount
import org.example.order.model.StatusPayment

data class OrderCreateDTO(
    var idUser: Long? = CustomerAccount.info!!.profileUUID,

    var addressUser: Address? = null,
    var phoneUser: String? = null,
    var fromTimeDelivery: String? = null,
    var toTimeDelivery: String? = null,
    var isSelf: Boolean = false,
    var idLocation: Long? = null,
    var payment: StatusPayment? = null,

    var summ: Double? = null,
    var comment: String = ""
)
