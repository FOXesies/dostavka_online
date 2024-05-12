package com.wayplaner.learn_room.admin.basic_info.domain.repository

import com.wayplaner.learn_room.admin.basic_info.domain.model.OrganizationResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path
import java.io.InputStream
import javax.inject.Singleton

@Singleton
interface BasicInfoRepository {
    @GET("organizations/update_info/")
    suspend fun updateInfo(@Body response: OrganizationResponse): Boolean
    @GET("organizations/get_info/{id}")
    suspend fun getInfo(@Path("id") idOrg: Long): OrganizationResponse
    @GET("organizations/get_info/img/{id}")
    suspend fun getImage(@Path("id") idOrg: Long): InputStream

}