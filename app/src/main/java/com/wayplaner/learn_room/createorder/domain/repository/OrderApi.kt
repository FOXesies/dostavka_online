package com.wayplaner.learn_room.createorder.domain.repository

import com.wayplaner.learn_room.createorder.domain.model.OrderSelfDelivery
import com.wayplaner.learn_room.createorder.domain.model.Order
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface OrderApi {
    @POST("order/deliveri/add")
    suspend fun sendOrder(@Body order: Order)
    @POST("order/self_deliveri/add")
    suspend fun sendOrderSelf(@Body order: OrderSelfDelivery)

}