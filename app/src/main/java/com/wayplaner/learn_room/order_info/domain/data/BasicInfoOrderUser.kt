package com.wayplaner.learn_room.order_info.domain.data

import com.wayplaner.learn_room.createorder.domain.model.Address
import com.wayplaner.learn_room.createorder.domain.model.StatusOrder
import com.wayplaner.learn_room.organization.domain.model.LocationOrganization
import com.wayplaner.learn_room.organization.domain.model.ResponseProductOrg

data class BasicInfoOrderUser(
    val orderId: Long = 0,

    var orgName: String? = null,
    var orgPhone: String? = null,

    var addressUser: Address? = null,
    var phoneUser: String? = null,
    var fromTimeDelivery: String? = null,
    var toTimeDelivery: String? = null,
    var productOrder: List<ResponseProductOrg> = mutableListOf(),
    var status: StatusOrder? = null,
    var isSelf: Boolean? = null,
    var idLocation: LocationOrganization? = null,

    var canceledInfo: String? = null,
    var canceledTime: String? = null,

    var feedBacksRating: Int? = null,
    var feedBacksComment: String? = null,
    var timeComment: String? = null,

    var summ: Double? = null,
    var comment: String? = null
)