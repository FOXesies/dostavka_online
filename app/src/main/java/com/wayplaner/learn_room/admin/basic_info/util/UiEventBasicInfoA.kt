package com.wayplaner.learn_room.admin.basic_info.util

import android.content.Context
import com.wayplaner.learn_room.createorder.domain.model.Address

sealed class UiEventBasicInfoA {
    data class SearchOrg(val idOrg: Long): UiEventBasicInfoA()
    data class UpdateAddress(val address: Address): UiEventBasicInfoA()
    data class UpdateOrg(val idOrg: Long, val name: String, val phone: String): UiEventBasicInfoA()
    data class UpdateImage(val idOrg: Long, val context: Context, val imageBt: ByteArray): UiEventBasicInfoA()
}