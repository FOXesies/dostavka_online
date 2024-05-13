package com.wayplaner.learn_room.admin.basic_info.domain.repository

import com.wayplaner.learn_room.admin.basic_info.domain.model.OrganizationResponse
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
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
interface BasicInfoRepository {
    @POST("organizations/update_info/")
    suspend fun updateInfo(@Body response: OrganizationResponse)
    @GET("organizations/get_info/{id}")
    suspend fun getInfo(@Path("id") idOrg: Long): OrganizationResponse
    @GET("upload/img/{id}")
    @Streaming
    suspend fun getImage(@Path("id") idOrg: Long): Response<ResponseBody>
    @Multipart
    @POST("upload/img/upload_org/")
    fun uploadImage(
        @Part image: MultipartBody.Part,
        @Part("orgId") orgId: Long
    ): Call<Void>

}