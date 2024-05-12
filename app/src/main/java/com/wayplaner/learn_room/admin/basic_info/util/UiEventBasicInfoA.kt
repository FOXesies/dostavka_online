package com.wayplaner.learn_room.admin.basic_info.util

import java.io.InputStream

sealed class UiEventBasicInfoA {
    data class SearchOrg(val idOrg: Long): UiEventBasicInfoA()
    data class UpdateOrg(val idOrg: Long): UiEventBasicInfoA()
    data class UpdateImage(val image: InputStream): UiEventBasicInfoA()
}