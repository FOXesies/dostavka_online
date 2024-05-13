package com.wayplaner.learn_room.admin.basic_info.util

import com.wayplaner.learn_room.organization.model.OrganizationIdDTO

sealed class StatusBasicInfo {
    data class FoundInfo(val organizationResponse: OrganizationIdDTO, val image: ByteArray): StatusBasicInfo()
    data object NoFoundInfo: StatusBasicInfo()
    data object SuccessUpdate: StatusBasicInfo()
    data class FailedUpdate(val message: String): StatusBasicInfo()
}