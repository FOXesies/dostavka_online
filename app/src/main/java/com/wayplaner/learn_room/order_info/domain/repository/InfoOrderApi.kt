package com.wayplaner.learn_room.order_info.domain.repository

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Singleton

@Singleton
interface InfoOrderApi {
    @GET("order/get_full_info_user/{id}")
    suspend fun getInfo(@Path("id") idUser: Long): Response<ResponseBody>
}