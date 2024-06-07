package com.wayplaner.learn_room.orderlist.data.repository

import com.wayplaner.learn_room.orderlist.domain.repository.ListOrderApi

class ListOrderImpl(private val listOrder: ListOrderApi) {
    suspend fun getAllOrder(uid: Long) = listOrder.getAllOrder(uid)
}