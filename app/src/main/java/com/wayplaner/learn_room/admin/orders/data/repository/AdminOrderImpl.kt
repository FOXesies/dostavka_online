package com.wayplaner.learn_room.admin.orders.data.repository

import com.wayplaner.learn_room.admin.orders.domain.model.AdminStatusResponse
import com.wayplaner.learn_room.admin.orders.domain.repository.AdminOrderApi

class AdminOrderImpl(private val listOrder: AdminOrderApi) {
    suspend fun switchStatus(statusinfo: AdminStatusResponse) = listOrder.switchStatus(statusinfo)
    suspend fun getActiveOrders(orgId: Long) = listOrder.getActiveOrders(orgId)
}