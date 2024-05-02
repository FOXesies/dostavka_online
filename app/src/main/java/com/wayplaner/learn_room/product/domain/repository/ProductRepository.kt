package com.wayplaner.learn_room.product.domain.repository

import com.wayplaner.learn_room.product.domain.model.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Singleton

@Singleton
interface ProductRepository {

    @GET("products/{id}")
    suspend fun getProductInfo(@Path("id")idProduct: Long): Response<Product>
    @GET("basket/{idUser}/check_basket_product/{idProduct}")
    suspend fun checkInBasket(@Path("idUser") idUser: Long, @Path("idProduct") idProduct: Long): Response<Boolean>
}