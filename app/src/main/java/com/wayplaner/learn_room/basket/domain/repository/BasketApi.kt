package com.wayplaner.learn_room.basket.domain.repository

import com.wayplaner.learn_room.basket.domain.model.SendBasketProduct
import com.wayplaner.learn_room.order.data.model.BasketItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BasketApi {
    @GET("basket/{id_user}")
    suspend fun getBasket(@Path("id_user") isUser: Long): Response<BasketItem>
    @POST("basket/add_product")
    suspend fun addProduct(@Body product: SendBasketProduct)
    @POST("basket/delete_product")
    suspend fun deleteProduct(@Body product: SendBasketProduct)
    @POST("basket/plus_product")
    suspend fun plusProduct(@Body productId: SendBasketProduct)
    @POST("basket/minus_product")
    suspend fun minusProduct(@Body product: SendBasketProduct)

}