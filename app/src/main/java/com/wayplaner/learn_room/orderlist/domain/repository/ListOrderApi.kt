package com.wayplaner.learn_room.orderlist.domain.repository

import com.wayplaner.learn_room.createorder.domain.model.OrderSelfDelivery
import org.example.order.model.Order
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Singleton

@Singleton
interface ListOrderApi {

    @GET("order/all_self_deliveri/{id}")
    suspend fun getAllSelfOrder(@Path("id") idUser: Long): Response<List<OrderSelfDelivery>>
    @GET("order/all_deliveri/{id}")
    suspend fun getAllOrder(@Path("id") idUser: Long): Response<List<Order>>

}