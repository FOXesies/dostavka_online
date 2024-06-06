package com.wayplaner.learn_room.basket.domain.repository

import com.wayplaner.learn_room.basket.domain.model.SendBasketProduct
import com.wayplaner.learn_room.order.data.model.BasketItem
import com.wayplaner.learn_room.orderlist.domain.model.ResponeInt
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BasketApi {
    @GET("basket/min/{id_user}")
    suspend fun getBasketMin(@Path("id_user") isUser: Long): Response<BasketItem>
    @GET("basket/{id_user}")
    suspend fun getBasket(@Path("id_user") isUser: Long): Response<BasketItem>
    @POST("basket/add_product")
    suspend fun addProduct(@Body product: SendBasketProduct)
    @POST("basket/replace_all")
    suspend fun replaceAll(@Body product: SendBasketProduct)
    @POST("basket/delete_product")
    suspend fun deleteProduct(@Body product: SendBasketProduct)
    @POST("basket/plus_product")
    suspend fun plusProduct(@Body productId: SendBasketProduct)
    @POST("basket/minus_product")
    suspend fun minusProduct(@Body product: SendBasketProduct)
    @GET("basket/{idUser}/check_basket_product/{idProduct}")
    suspend fun checkInBasket(@Path("idUser") idUser: Long, @Path("idProduct") idProduct: Long): Response<ResponeInt>
}