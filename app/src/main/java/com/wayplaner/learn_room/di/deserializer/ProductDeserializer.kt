package com.wayplaner.learn_room.di.deserializer

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import com.wayplaner.learn_room.home.domain.model.Image
import com.wayplaner.learn_room.product.domain.model.Product
import java.lang.reflect.Type

class ProductDeserializer : JsonDeserializer<Product> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Product {
        val jsonObject = json.asJsonObject

        val imagesType = object : TypeToken<List<Image>>() {}.type
        val images = context.deserialize<List<Image>>(jsonObject.get("images"), imagesType)

        return Product(
            idProduct = jsonObject.get("idProduct")?.asLong,
            name = jsonObject.get("name").asString,
            price = jsonObject.get("price")?.asDouble,
            weight = jsonObject.get("weight")?.asFloat,
            description = jsonObject.get("description")?.asString,
            images = images
        )
    }
}

