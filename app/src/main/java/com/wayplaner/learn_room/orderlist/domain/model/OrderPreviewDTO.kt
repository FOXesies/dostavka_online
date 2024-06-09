package com.wayplaner.learn_room.orderlist.domain.model

import com.wayplaner.learn_room.createorder.domain.model.Address
import com.wayplaner.learn_room.createorder.domain.model.StatusOrder
import com.wayplaner.learn_room.organization.domain.model.LocationOrganization

data class OrderPreviewDTO(
    var idOrder: Long? = null,
    var organizationId: Long? = null,
    var organizationName: String? = null,
    var addressUser: Address? = null,
    var idLocation: LocationOrganization? = null,

    var isSelf: Boolean = false,
    var fromTimeCooking: String?,
    var toTimeCooking: String?,

    var status: StatusOrder? = null,

    var summ: Double? = null,
    var comment: String = ""
)
