package com.wayplaner.learn_room.admin.orders.domain.repository

import com.wayplaner.learn_room.admin.orders.domain.model.AdminStatusResponse
import com.wayplaner.learn_room.createorder.domain.model.Order
import com.wayplaner.learn_room.createorder.domain.model.OrderSelfDelivery
import com.wayplaner.learn_room.orderlist.domain.model.ResponseCancel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AdminOrderApi {
    @GET("admin/order/all_deliveri/{id}")
    suspend fun getActiveOrder(@Path("id") idOrg: Long): Response<List<Order>>
    @GET("admin/order/all_self_deliveri/{id}")
    suspend fun getActiveOrderSelf(@Path("id") idOrg: Long): Response<List<OrderSelfDelivery>>
    @POST("admin/order/switch_status")
    suspend fun switchStatus(@Body statusinfo: AdminStatusResponse)
    @POST("admin/order/switch_statusSelf")
    suspend fun switchStatusSelf(@Body statusinfo: AdminStatusResponse)
    @POST("canceled_order/delivery/cancel/")
    suspend fun cancelOrder(@Body order: ResponseCancel)
    @POST("canceled_order/self_delivery/cancel/")
    suspend fun cancelOrderSelf(@Body order: ResponseCancel)


    @GET("admin/order/all_self_deliveri/{id}")
    suspend fun getCancelOrder(@Path("id") idOrg: Long): Response<List<Order>>
    @GET("admin/order/all_self_deliveri/{id}")
    suspend fun getCancelOrderSelf(@Path("id") idOrg: Long): Response<List<OrderSelfDelivery>>
}