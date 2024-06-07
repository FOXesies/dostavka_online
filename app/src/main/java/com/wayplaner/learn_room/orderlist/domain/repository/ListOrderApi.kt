package com.wayplaner.learn_room.orderlist.domain.repository

import com.wayplaner.learn_room.orderlist.domain.model.OrderPreviewDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Singleton

@Singleton
interface ListOrderApi {
    @GET("order/all_deliveri_active/{id}")
    suspend fun getAllOrder(@Path("id") idUser: Long): Response<List<OrderPreviewDTO>>

}