package com.wayplaner.learn_room.admin.orders.domain.repository

import com.wayplaner.learn_room.admin.orders.domain.model.AdminStatusResponse
import com.wayplaner.learn_room.orderlist.domain.model.CancelOrderPreview
import com.wayplaner.learn_room.orderlist.domain.model.CompleteOrderPreview
import com.wayplaner.learn_room.orderlist.domain.model.OrderPreviewDTO
import com.wayplaner.learn_room.orderlist.domain.model.ResponseCancel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AdminOrderApi {
    @POST("admin/order/switch_status")
    suspend fun switchStatus(@Body statusResponse: AdminStatusResponse)
/*    @POST("admin/order/cancel_order")
    suspend fun cancelOrder(@Body statusResponse: AdminStatusResponse)*/
    @GET("admin/order/active_order/{id}")
    suspend fun getActiveOrders(@Path("id") idOrg: Long): Response<List<OrderPreviewDTO>>
    @GET("admin/order/cancele_order/{id}")
    suspend fun getAllCanceleOrder(@Path("id") idUser: Long): Response<List<CancelOrderPreview>>
    @GET("admin/order/complete_order/{id}")
    suspend fun getAllCompleteOrder(@Path("id") idUser: Long): Response<List<CompleteOrderPreview>>
    @POST("order/cancel/")
    suspend fun cancelOrder(@Body responseCancel: ResponseCancel)

}