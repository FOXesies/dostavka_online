package com.wayplaner.learn_room.product.domain.repository

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Singleton

@Singleton
interface ProductRepository {

    @GET("products/{id}")
    suspend fun getProductInfo(@Path("id")idProduct: Long): Response<ResponseBody>
}