package com.wayplaner.learn_room.orderlist.data.repository

import com.wayplaner.learn_room.orderlist.domain.repository.ListOrderApi

class ListOrderImpl(private val listOrder: ListOrderApi) {
    suspend fun getAllSelfOrder(uid: Long) = listOrder.getAllSelfOrder(uid)
    suspend fun getAllOrder(uid: Long) = listOrder.getAllOrder(uid)
}