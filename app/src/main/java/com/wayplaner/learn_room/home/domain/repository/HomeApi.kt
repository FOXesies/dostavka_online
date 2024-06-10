package com.wayplaner.learn_room.home.domain.repository

import com.wayplaner.learn_room.home.domain.model.UpdateCityDTO
import com.wayplaner.learn_room.organization.domain.model.FiltercategoryOrg
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface HomeApi{
    @GET("organizations/")
    suspend fun getOrganizations(@Query("city") city: String): Response<ResponseBody>
    @POST("organizations/filter")
    suspend fun getOrganizations(@Body filerCategory: FiltercategoryOrg): Response<List<Long>>
    @GET("organizations/cities")
    suspend fun getCities(): Response<List<String>>
    @GET("organizations/categories")
    suspend fun getCategoriesCity(@Query("city") city: String): Response<List<String>>
    @POST("auth/update_city")
    suspend fun updateCity(@Body city: UpdateCityDTO)

}