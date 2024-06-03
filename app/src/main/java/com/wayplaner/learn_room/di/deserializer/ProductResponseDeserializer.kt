package com.wayplaner.learn_room.di.deserializer

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import com.wayplaner.learn_room.home.domain.model.Image
import com.wayplaner.learn_room.organization.domain.model.ResponseProductOrg
import java.lang.reflect.Type

class ProductResponseDeserializer : JsonDeserializer<ResponseProductOrg> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): ResponseProductOrg? {
        val jsonObject = json.asJsonObject

        val imagesType = object : TypeToken<Image>() {}.type
        val images = context.deserialize<Image>(jsonObject.get("image"), imagesType)

        return ResponseProductOrg(
            id = jsonObject.get("id").asLong,
            name = jsonObject.get("name").asString,
            price = jsonObject.get("price")?.asDouble,
            description = jsonObject.get("description")?.asString,
            image = images
        )
    }
}