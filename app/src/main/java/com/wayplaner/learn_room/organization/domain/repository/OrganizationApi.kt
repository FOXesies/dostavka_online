package com.wayplaner.learn_room.organization.domain.repository

import com.wayplaner.learn_room.organization.model.OrganizationIdDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Singleton

@Singleton
interface OrganizationApi {

    @GET("organizations/{id}")
    suspend fun getOrganizationsById(@Path("id") id: Long): Response<OrganizationIdDTO>

}