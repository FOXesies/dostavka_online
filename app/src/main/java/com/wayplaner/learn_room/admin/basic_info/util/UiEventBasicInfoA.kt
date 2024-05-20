package com.wayplaner.learn_room.admin.basic_info.util

import android.content.Context
import com.wayplaner.learn_room.organization.model.CityOrganization

sealed class UiEventBasicInfoA {
    data class SearchOrg(val idOrg: Long): UiEventBasicInfoA()
    data object UpdateOrg: UiEventBasicInfoA()
    data class RemoveAddresss(val city: String, val address: CityOrganization?): UiEventBasicInfoA()
    data class AddAddresss(val city: String, val address: CityOrganization?): UiEventBasicInfoA()
    data class UpdateImage(val idOrg: Long, val context: Context, val imageBt: ByteArray): UiEventBasicInfoA()
    data class AddCities(val cities: String): UiEventBasicInfoA()
}