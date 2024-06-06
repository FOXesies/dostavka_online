package com.wayplaner.learn_room.createorder.data.repository

import com.wayplaner.learn_room.createorder.domain.model.Order
import com.wayplaner.learn_room.createorder.domain.model.OrderSelfDelivery
import com.wayplaner.learn_room.createorder.domain.repository.OrderApi


class OrderApiImpl(private val orderApi: OrderApi) {
    suspend fun sendOrder(order: Order) = orderApi.sendOrder(order)
    suspend fun sendOrderSelf(order: OrderSelfDelivery) = orderApi.sendOrderSelf(order)
    suspend fun getAddressesByOrg(idOrg: Long, city: String) = orderApi.getAddressesByOrg(idOrg, city)

}