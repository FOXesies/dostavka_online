package com.wayplaner.learn_room.createorder.domain.repository

import com.wayplaner.learn_room.createorder.domain.model.Order
import com.wayplaner.learn_room.createorder.domain.model.OrderSelfDelivery
import com.wayplaner.learn_room.organization.domain.model.LocationOrganization
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface OrderApi {
    @POST("order/deliveri/add")
    suspend fun sendOrder(@Body order: Order)
    @POST("order/self_deliveri/add")
    suspend fun sendOrderSelf(@Body order: OrderSelfDelivery)
    @GET("organizations/addresses/{id}/")
    suspend fun getAddressesByOrg(@Path("id") idOrg: Long, @Query("city") city: String): Response<List<LocationOrganization>>

}