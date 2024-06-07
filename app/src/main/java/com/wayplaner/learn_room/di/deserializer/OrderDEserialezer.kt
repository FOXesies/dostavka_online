package com.wayplaner.learn_room.di.deserializer

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import com.wayplaner.learn_room.admin.infoorder.domain.model.SendBasicInfoOrder
import com.wayplaner.learn_room.createorder.domain.model.Address
import com.wayplaner.learn_room.createorder.domain.model.StatusOrder
import com.wayplaner.learn_room.organization.domain.model.LocationOrganization
import com.wayplaner.learn_room.organization.domain.model.ResponseProductOrg
import java.lang.reflect.Type

class OrderDEserialezer : JsonDeserializer<SendBasicInfoOrder> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): SendBasicInfoOrder {
        val jsonObject = json.asJsonObject

        val addressType = object : TypeToken<Address>() {}.type
        val addressUser = context.deserialize<Address>(jsonObject.get("addressUser"), addressType)

        val productsType = object : TypeToken<List<ResponseProductOrg>>() {}.type
        val productOrder = context.deserialize<List<ResponseProductOrg>>(jsonObject.get("productOrder"), productsType)

        val locationType = object : TypeToken<LocationOrganization>() {}.type
        val idLocation = context.deserialize<LocationOrganization>(jsonObject.get("idLocation"), locationType)

        return SendBasicInfoOrder(
            orderId = jsonObject.get("orderId")?.asLong ?: 0,
            userName = jsonObject.get("userName")?.asString,
            addressUser = addressUser,
            phoneUser = jsonObject.get("phoneUser")?.asString,
            fromTimeDelivery = jsonObject.get("fromTimeDelivery")?.asString,
            toTimeDelivery = jsonObject.get("toTimeDelivery")?.asString,
            productOrder = productOrder,
            status = context.deserialize(jsonObject.get("status"), StatusOrder::class.java),
            isSelf = jsonObject.get("isSelf")?.asBoolean,
            idLocation = idLocation,
            summ = jsonObject.get("summ")?.asDouble,
            comment = jsonObject.get("comment")?.asString
        )
    }
}