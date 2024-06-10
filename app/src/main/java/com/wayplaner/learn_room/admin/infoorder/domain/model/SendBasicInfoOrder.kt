package com.wayplaner.learn_room.admin.infoorder.domain.model

import com.wayplaner.learn_room.createorder.domain.model.Address
import com.wayplaner.learn_room.createorder.domain.model.StatusOrder
import com.wayplaner.learn_room.organization.domain.model.LocationOrganization
import com.wayplaner.learn_room.organization.domain.model.ResponseProductOrg
import org.example.order.model.StatusPayment

data class SendBasicInfoOrder(
    val orderId: Long = 0,

    var userName: String? = null,

    var addressUser: Address? = null,
    var phoneUser: String? = null,
    var fromTimeDelivery: String? = null,
    var toTimeDelivery: String? = null,
    var productOrder: List<ResponseProductOrg> = mutableListOf(),
    var counts: List<Int> = mutableListOf(),
    var status: StatusOrder? = null,
    var isSelf: Boolean? = null,
    var idLocation: LocationOrganization? = null,
    var payment: StatusPayment? = null,

    var canceledInfo: String? = null,
    var canceledTime: String? = null,

    var feedBacksRating: Int? = null,
    var feedBacksComment: String? = null,
    var timeComment: String? = null,

    var summ: Double? = null,
    var comment: String? = null
)