package com.wayplaner.learn_room.createorder.data.repository

import com.wayplaner.learn_room.createorder.domain.model.OrderCreateDTO
import com.wayplaner.learn_room.createorder.domain.repository.OrderApi


class OrderApiImpl(private val orderApi: OrderApi) {
    suspend fun sendOrder(order: OrderCreateDTO) = orderApi.sendOrder(order)
    suspend fun getAddressesByOrg(idOrg: Long, city: String) = orderApi.getAddressesByOrg(idOrg, city)

}