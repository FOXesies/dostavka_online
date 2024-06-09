package com.wayplaner.learn_room.admin.infoorder.domain.repository

import com.wayplaner.learn_room.admin.orders.domain.model.AdminStatusResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AdminInfoOrderApi {
    @GET("order/get_full_info/{id}")
    suspend fun getInfoOrder(@Path("id") id: Long): Response<ResponseBody>
    @POST("admin/order/switch_status")
    suspend fun switchStatus(@Body statusResponse: AdminStatusResponse)
}