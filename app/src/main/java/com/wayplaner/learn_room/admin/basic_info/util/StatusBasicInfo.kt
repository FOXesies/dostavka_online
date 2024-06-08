package com.wayplaner.learn_room.admin.basic_info.util

import com.wayplaner.learn_room.admin.basic_info.domain.model.BasicInfoResponse

sealed class StatusBasicInfo {
    data class FoundInfo(val organizationResponse: BasicInfoResponse): StatusBasicInfo()
    data object NoFoundInfo: StatusBasicInfo()
    data object SuccessUpdate: StatusBasicInfo()
    data class FailedUpdate(val message: String): StatusBasicInfo()
}