package com.wayplaner.learn_room.createorder.data.repository

import com.wayplaner.learn_room.createorder.domain.repository.OrderApi
import org.example.order.model.Order


class OrderApiImpl(private val orderApi: OrderApi) {
    suspend fun sendOrder(order: Order) = orderApi.sendOrder(order)
}