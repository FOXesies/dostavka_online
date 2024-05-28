package com.wayplaner.learn_room.admin.orders.data.repository

import com.wayplaner.learn_room.admin.orders.domain.model.AdminStatusResponse
import com.wayplaner.learn_room.admin.orders.domain.repository.AdminOrderApi
import com.wayplaner.learn_room.orderlist.domain.model.ResponseCancel

class AdminOrderImpl(private val listOrder: AdminOrderApi) {
    suspend fun getActiveOrderSelf(uid: Long) = listOrder.getActiveOrderSelf(uid)
    suspend fun getActiveOrder(uid: Long) = listOrder.getActiveOrder(uid)
    suspend fun switchStatus(statusinfo: AdminStatusResponse) = listOrder.switchStatus(statusinfo)
    suspend fun switchStatusSelf(statusinfo: AdminStatusResponse) = listOrder.switchStatusSelf(statusinfo)
    suspend fun cancelOrder(responseCancel: ResponseCancel) = listOrder.cancelOrder(responseCancel)
    suspend fun cancelOrderSelf(responseCancel: ResponseCancel) = listOrder.cancelOrderSelf(responseCancel)


    suspend fun getCancelOrderSelf(uid: Long) = listOrder.getCancelOrder(uid)
    suspend fun getCancelOrder(uid: Long) = listOrder.getCancelOrderSelf(uid)
}