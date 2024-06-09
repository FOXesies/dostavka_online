package com.wayplaner.learn_room.admin.basic_info.util

import android.content.Context
import com.wayplaner.learn_room.admin.basic_info.domain.model.ImageDTO
import com.wayplaner.learn_room.organization.model.CityOrganization

sealed class UiEventBasicInfoA {
    data class SearchOrg(val idOrg: Long): UiEventBasicInfoA()
    data class UpdateOrg(val name: String, val phone: String, val description: String, val images: List<ImageDTO>, val context: Context): UiEventBasicInfoA()
    data class RemoveAddresss(val city: String, val address: CityOrganization?): UiEventBasicInfoA()
    data class AddAddresss(val city: String, val address: CityOrganization?): UiEventBasicInfoA()
    data class AddCities(val cities: String): UiEventBasicInfoA()
}