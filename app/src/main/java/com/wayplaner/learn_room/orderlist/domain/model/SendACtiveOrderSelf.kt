package org.example.order.DTO.sen_response

import com.wayplaner.learn_room.createorder.domain.model.StatusOrder
import com.wayplaner.learn_room.createorder.domain.model.UUIDCustom
import com.wayplaner.learn_room.organization.domain.model.LocationOrganization

data class SendACtiveOrderSelf(
    var idOrderSelf: Long? = null,
    var organizationId: Long? = null,
    var organizationName: String? = null,
    var idLocation: LocationOrganization,
    var uuid: UUIDCustom? = null,

    var phoneUser: String? = null,
    var fromTimeCooking: String?,
    var toTimeCooking: String?,

    var status: StatusOrder? = null,

    var summ: Double? = null,
    var comment: String = ""
)