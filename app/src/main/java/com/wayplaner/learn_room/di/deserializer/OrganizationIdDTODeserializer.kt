package com.wayplaner.learn_room.di.deserializer

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import com.wayplaner.learn_room.home.domain.model.Image
import com.wayplaner.learn_room.organization.domain.model.ResponseProductOrg
import com.wayplaner.learn_room.organization.model.CityOrganization
import com.wayplaner.learn_room.organization.model.OrganizationIdDTO
import java.lang.reflect.Type

class OrganizationIdDTODeserializer : JsonDeserializer<OrganizationIdDTO> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): OrganizationIdDTO {
        val jsonObject = json.asJsonObject

        val locationsAllType = object : TypeToken<Map<String, MutableList<CityOrganization>>>() {}.type
        val locationsAll = context.deserialize<Map<String, MutableList<CityOrganization>>>(jsonObject.get("locationsAll"), locationsAllType)

        val productsType = object : TypeToken<Map<String, List<ResponseProductOrg>>>() {}.type
        val products = context.deserialize<Map<String, List<ResponseProductOrg>>>(jsonObject.get("products"), productsType)

        val imagesType = object : TypeToken<List<Image>>() {}.type
        val images = context.deserialize<List<Image>>(jsonObject.get("idImages"), imagesType)

        return OrganizationIdDTO(
            idOrganization = jsonObject.get("idOrganization")?.asLong,
            name = jsonObject.get("name").asString,
            phoneForUser = jsonObject.get("phoneForUser").asString,
            descriptions = jsonObject.get("descriptions")?.asString,
            category = context.deserialize(jsonObject.get("category"), List::class.java),
            locationsAll = locationsAll,
            products = products,
            rating = jsonObject.get("rating")?.asDouble,
            ratingCount = jsonObject.get("ratingCount")?.asInt,
            isFavorite = jsonObject.get("isFavorite")?.asBoolean ?: false,
            idImages = images
        )
    }
}