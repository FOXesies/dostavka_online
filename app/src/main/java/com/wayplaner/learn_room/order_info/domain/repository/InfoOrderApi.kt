package com.wayplaner.learn_room.order_info.domain.repository

import com.wayplaner.learn_room.order_info.domain.data.BasicInfoOrderUser
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Path
import javax.inject.Singleton

@Singleton
interface InfoOrderApi {
    @POST("order/get_full_info_user/{id}/")
    suspend fun getInfo(@Path("id") idUser: Long): Response<BasicInfoOrderUser>
}