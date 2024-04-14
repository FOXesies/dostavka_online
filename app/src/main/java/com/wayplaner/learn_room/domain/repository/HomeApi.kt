package com.wayplaner.learn_room.domain.repository

import com.wayplaner.learn_room.domain.model.OrganizationDTO
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Streaming
import javax.inject.Singleton

@Singleton
interface HomeApi{

    @GET("organizations/")
    suspend fun getOrganizations(): Response<List<OrganizationDTO>>


}