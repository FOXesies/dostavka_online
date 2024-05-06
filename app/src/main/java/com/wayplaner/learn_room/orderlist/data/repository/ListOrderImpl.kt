package com.wayplaner.learn_room.orderlist.data.repository

import com.wayplaner.learn_room.orderlist.domain.model.ResponseCancel
import com.wayplaner.learn_room.orderlist.domain.repository.ListOrderApi

class ListOrderImpl(private val listOrder: ListOrderApi) {
    suspend fun getAllSelfOrder(uid: Long) = listOrder.getAllSelfOrder(uid)
    suspend fun getAllOrder(uid: Long) = listOrder.getAllOrder(uid)
    suspend fun getAllCanceledOrder(idUser: Long) = listOrder.getAllCanceledOrder(idUser)
    suspend fun getAllCanceledOrderSelf(idUser: Long) = listOrder.getAllCanceledOrderSelf(idUser)
    suspend fun cancelOrderSelf(order: ResponseCancel) = listOrder.cancelOrderSelf(order)
    suspend fun cancelOrder(order: ResponseCancel) = listOrder.cancelOrder(order)
}