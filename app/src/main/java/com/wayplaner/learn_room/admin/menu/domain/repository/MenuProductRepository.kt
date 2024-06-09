package com.wayplaner.learn_room.admin.menu.domain.repository

import com.wayplaner.learn_room.product.domain.model.Product
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import javax.inject.Singleton

@Singleton
interface MenuProductRepository {
    @GET("organizations/all_products/{id}")
    suspend fun getAllInfo(@Path("id") idOrg: Long): Response<ResponseBody>

    @GET("organizations/categories/{id}")
    suspend fun getCategories(@Path("id") idOrg: Long): Response<List<String>>
    @GET("products/{id}")
    suspend fun getInfo(@Path("id") idOrg: Long): Response<ResponseBody>


    @Multipart
    @POST("admin/products/update/")
    suspend fun updateProduct(
        @Part image: List<MultipartBody.Part>,
        @Part("product") product: Product,
        @Part("category") category: String
    ): Call<Void>

    @Multipart
    @POST("admin/products/upload_foto/")
    fun uploadImage(
        @Part image: List<MultipartBody.Part>,
        @Part("product") product: RequestBody
    ): Call<ResponseBody>

}