package com.wayplaner.learn_room.admin.basic_info.util

import com.wayplaner.learn_room.product.domain.model.Product

sealed class StatusMenuChange {
    data class FoundInfo(val productResponse: Product, val image: ByteArray): StatusMenuChange()
    data object NoFoundInfo: StatusMenuChange()
    data object SuccessUpdate: StatusMenuChange()
    data class FailedUpdate(val message: String): StatusMenuChange()
}