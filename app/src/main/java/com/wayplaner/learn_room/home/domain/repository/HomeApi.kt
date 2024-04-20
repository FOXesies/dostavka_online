package com.wayplaner.learn_room.home.domain.repository

import com.wayplaner.learn_room.home.domain.model.OrganizationDTO
import retrofit2.Response
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface HomeApi{

    @GET("organizations/")
    suspend fun getOrganizations(): Response<List<OrganizationDTO>>


}