package com.wayplaner.learn_room.orderlist.data.repository

import com.wayplaner.learn_room.orderlist.domain.model.ResponseCancel
import com.wayplaner.learn_room.orderlist.domain.repository.ListOrderApi

class ListOrderImpl(private val listOrder: ListOrderApi) {
    suspend fun getAllOrder(uid: Long) = listOrder.getAllOrder(uid)
    suspend fun getAllCanceleOrder(uid: Long) = listOrder.getAllCanceleOrder(uid)
    suspend fun getAllCompleteOrder(uid: Long) = listOrder.getAllCompleteOrder(uid)
    suspend fun cancelOrder(responseCancel: ResponseCancel) = listOrder.cancelOrder(responseCancel)
}