package com.wayplaner.learn_room.orderlist.domain.repository

import com.wayplaner.learn_room.orderlist.domain.model.CancelOrderPreview
import com.wayplaner.learn_room.orderlist.domain.model.CompleteOrderPreview
import com.wayplaner.learn_room.orderlist.domain.model.OrderPreviewDTO
import com.wayplaner.learn_room.orderlist.domain.model.ResponseCancel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import javax.inject.Singleton

@Singleton
interface ListOrderApi {
    @GET("order/all_deliveri_active/{id}")
    suspend fun getAllOrder(@Path("id") idUser: Long): Response<List<OrderPreviewDTO>>
    @GET("order/all_deliveri_cancele/{id}")
    suspend fun getAllCanceleOrder(@Path("id") idUser: Long): Response<List<CancelOrderPreview>>
    @GET("order/all_deliveri_complete/{id}")
    suspend fun getAllCompleteOrder(@Path("id") idUser: Long): Response<List<CompleteOrderPreview>>
    @POST("order/cancel/")
    suspend fun cancelOrder(@Body responseCancel: ResponseCancel)

}