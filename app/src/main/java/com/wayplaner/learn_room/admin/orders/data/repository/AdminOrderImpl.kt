package com.wayplaner.learn_room.admin.orders.data.repository

import com.wayplaner.learn_room.admin.orders.domain.model.AdminStatusResponse
import com.wayplaner.learn_room.admin.orders.domain.repository.AdminOrderApi
import com.wayplaner.learn_room.orderlist.domain.model.ResponseCancel

class AdminOrderImpl(private val listOrder: AdminOrderApi) {
    suspend fun switchStatus(statusinfo: AdminStatusResponse) = listOrder.switchStatus(statusinfo)
    suspend fun getActiveOrders(orgId: Long) = listOrder.getActiveOrders(orgId)
    suspend fun getCanceledORders(orgId: Long) = listOrder.getAllCanceleOrder(orgId)
    suspend fun getCompleteORders(orgId: Long) = listOrder.getAllCompleteOrder(orgId)
    suspend fun cancelOrder(orgId: ResponseCancel) = listOrder.cancelOrder(orgId)
}