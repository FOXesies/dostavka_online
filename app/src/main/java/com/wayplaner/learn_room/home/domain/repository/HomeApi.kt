package com.wayplaner.learn_room.home.domain.repository

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface HomeApi{
    @GET("organizations/")
    suspend fun getOrganizations(@Query("city") city: String): Response<ResponseBody>
    @GET("organizations/cities")
    suspend fun getCities(): Response<List<String>>

}