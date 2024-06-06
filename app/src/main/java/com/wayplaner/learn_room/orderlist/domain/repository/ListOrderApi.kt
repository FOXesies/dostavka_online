package com.wayplaner.learn_room.orderlist.domain.repository

import com.wayplaner.learn_room.createorder.domain.model.Order
import com.wayplaner.learn_room.orderlist.domain.model.CanceledOrder
import com.wayplaner.learn_room.orderlist.domain.model.CanceledOrderSelf
import com.wayplaner.learn_room.orderlist.domain.model.ResponseCancel
import org.example.order.DTO.sen_response.SendACtiveOrderSelf
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import javax.inject.Singleton

@Singleton
interface ListOrderApi {

    @GET("order/all_self_deliveri/{id}")
    suspend fun getAllSelfOrder(@Path("id") idUser: Long): Response<List<SendACtiveOrderSelf>>
    @GET("order/all_deliveri/{id}")
    suspend fun getAllOrder(@Path("id") idUser: Long): Response<List<Order>>

    @GET("canceled_order/all_deliveri/{id}")
    suspend fun getAllCanceledOrder(@Path("id") idUser: Long): Response<List<CanceledOrder>>
    @GET("canceled_order/all_self_deliveri/{id}")
    suspend fun getAllCanceledOrderSelf(@Path("id") idUser: Long): Response<List<CanceledOrderSelf>>
    @POST("canceled_order/self_delivery/cancel/")
    suspend fun cancelOrderSelf(@Body order: ResponseCancel)
    @POST("canceled_order/delivery/cancel/")
    suspend fun cancelOrder(@Body order: ResponseCancel)

}