package com.wayplaner.learn_room.product.domain.repository

import okhttp3.ResponseBody
import org.example.favorite.entity.DTO.ResponseFavProduct
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import javax.inject.Singleton

@Singleton
interface ProductRepository {

    @GET("products/{id}")
    suspend fun getProductInfo(@Path("id")idProduct: Long): Response<ResponseBody>

    @POST("favorite/like_product/")
    suspend fun likeProduct(@Body responseFavorite: ResponseFavProduct): Response<ResponseBody>
    @POST("favorite/get_like_product/")
    suspend fun getLikeProduct(@Body responseFavorite: ResponseFavProduct): Response<Boolean>
}