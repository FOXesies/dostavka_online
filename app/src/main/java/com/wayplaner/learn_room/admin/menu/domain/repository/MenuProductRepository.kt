package com.wayplaner.learn_room.admin.menu.domain.repository

import com.wayplaner.learn_room.admin.basic_info.domain.repository.ResponseUpdate
import com.wayplaner.learn_room.admin.menu.data.model.ResponseProduct
import com.wayplaner.learn_room.product.domain.model.Product
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Streaming
import javax.inject.Singleton

@Singleton
interface MenuProductRepository {
    @Multipart
    @POST("admin/products/add_product/")
    suspend fun updateProducts(
        @Part("product") product: ResponseProduct
    ): Response<Void>
    @GET("organizations/all_products/{id}")
    suspend fun getAllInfo(@Path("id") idOrg: Long): Response<ResponseBody>

    @GET("organizations/categories/{id}")
    suspend fun getCategories(@Path("id") idOrg: Long): Response<List<String>>
    @GET("products/{id}")
    @Streaming
    suspend fun getInfo(@Path("id") idOrg: Long): Response<ResponseBody>
    @GET("admin/products/update")
    suspend fun updateProduct(@Body product: Product): ResponseUpdate

}