package com.wayplaner.learn_room.createorder.domain.repository

import org.example.order.model.Order
import retrofit2.http.Body
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface OrderApi {
    @GET("order/self_deliveri")
    suspend fun sendOrder(@Body order: Order)

}