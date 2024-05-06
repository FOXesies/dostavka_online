package com.wayplaner.learn_room.createorder.data.repository

import com.wayplaner.learn_room.createorder.domain.model.OrderSelfDelivery
import com.wayplaner.learn_room.createorder.domain.repository.OrderApi
import com.wayplaner.learn_room.createorder.domain.model.Order


class OrderApiImpl(private val orderApi: OrderApi) {
    suspend fun sendOrder(order: Order) = orderApi.sendOrder(order)
    suspend fun sendOrderSelf(order: OrderSelfDelivery) = orderApi.sendOrderSelf(order)

}