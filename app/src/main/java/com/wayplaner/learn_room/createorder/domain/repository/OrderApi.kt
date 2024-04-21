package com.wayplaner.learn_room.createorder.domain.repository

import org.example.order.model.Order
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface OrderApi {
    @POST("order/deliveri/add")
    suspend fun sendOrder(@Body order: Order)

}