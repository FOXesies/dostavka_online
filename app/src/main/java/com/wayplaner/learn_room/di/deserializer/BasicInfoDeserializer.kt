package com.wayplaner.learn_room.di.deserializer

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import com.wayplaner.learn_room.admin.basic_info.domain.model.BasicInfoResponse
import com.wayplaner.learn_room.admin.basic_info.domain.model.ImageDTO
import com.wayplaner.learn_room.home.domain.model.Image
import com.wayplaner.learn_room.organization.model.CityOrganization
import java.lang.reflect.Type

class BasicInfoDeserializer : JsonDeserializer<BasicInfoResponse> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): BasicInfoResponse {
        val jsonObject = json.asJsonObject

        val locationsAllType = object : TypeToken<Map<String, MutableList<CityOrganization>>>() {}.type
        val locationsAll = context.deserialize<Map<String, MutableList<CityOrganization>>>(jsonObject.get("locationAll"), locationsAllType)

        val imagesType = object : TypeToken<List<Image>>() {}.type
        val images = context.deserialize<List<Image>>(jsonObject.get("idImages"), imagesType)

        return BasicInfoResponse(
            idOrg = jsonObject.get("idOrg")?.asLong ?: 0,
            name = jsonObject.get("name").asString,
            phone = jsonObject.get("phone").asString,
            description = jsonObject.get("description").asString,
            locationAll = locationsAll,
            idImages = images.map { ImageDTO(id = it.id, byteArray = it.value) }
        )
    }
}