package com.wayplaner.learn_room.admin.basic_info.util

import com.wayplaner.learn_room.admin.menu.domain.model.ProductResponse

sealed class StatusMenuChange {
    data class FoundInfo(val productResponse: ProductResponse, val image: ByteArray): StatusMenuChange()
    data object NoFoundInfo: StatusMenuChange()
    data object SuccessUpdate: StatusMenuChange()
    data class FailedUpdate(val message: String): StatusMenuChange()
}