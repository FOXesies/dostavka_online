package com.wayplaner.learn_room.organization.domain.repository

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface OrganizationApi {
    @GET("organizations/{id}/")
    suspend fun getOrganizationsById(@Path("id") id: Long, @Query("city") city: String): Response<ResponseBody>

}