package com.wayplaner.learn_room.admin.basic_info.domain.repository

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
interface BasicInfoRepository {

    @Multipart
    @POST("admin/organizations/update_info/")
    fun updateInfo(
        @Part image: List<MultipartBody.Part>,
        @Part("organization") organization: RequestBody
    ): Call<ResponseBody>
    @Multipart
    @POST("admin/organizations/add_info/")
    fun addInfo(
        @Part image: List<MultipartBody.Part>,
        @Part("organization") organization: RequestBody
    ): Call<ResponseBody>

    @GET("organizations/get_info/{id}")
    suspend fun getInfo(@Path("id") idOrg: Long): Response<ResponseBody>
}

data class ResponseUpdate(
    val error_message: String? = null
)