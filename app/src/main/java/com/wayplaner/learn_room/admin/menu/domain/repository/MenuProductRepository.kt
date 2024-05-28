package com.wayplaner.learn_room.admin.menu.domain.repository

import com.wayplaner.learn_room.product.domain.model.Product
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Streaming
import javax.inject.Singleton

@Singleton
interface MenuProductRepository {
    @GET("organizations/categories")
    suspend fun getCategories(): List<String>
    @Multipart
    @POST("products/add_product/")
    suspend fun updateProducts(
        @Part image: MultipartBody.Part,
        @Part("product") product: RequestBody
    ): Response<Void>
    @GET("products/get_info/{id}")
    suspend fun getInfo(@Path("id") idOrg: Long): Product
    @GET("upload/img/{id}")
    @Streaming
    suspend fun getImage(@Path("id") idOrg: Long): Response<ResponseBody>

}