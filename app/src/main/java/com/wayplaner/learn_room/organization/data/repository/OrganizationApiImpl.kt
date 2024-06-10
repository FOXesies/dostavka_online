package com.wayplaner.learn_room.organization.data.repository

import com.google.gson.Gson
import com.wayplaner.learn_room.organization.domain.repository.OrganizationApi
import com.wayplaner.learn_room.organization.model.OrganizationIdDTO
import org.example.favorite.entity.DTO.ResponseFavProduct
import retrofit2.Response

class OrganizationApiImpl(
    private val organizationApi: OrganizationApi,
    private val gson: Gson) {

    // получение ресторана
    suspend fun getOrganizationById(id: Long, city: String): Response<OrganizationIdDTO> {
        val response = organizationApi.getOrganizationsById(id, city)
        val responseBody = response.body()?.string()
        val organizations = gson.fromJson(responseBody, OrganizationIdDTO::class.java)
        return Response.success(organizations)
    }

    suspend fun likeOrg(response: ResponseFavProduct) = organizationApi.likeOrg(response)
    suspend fun getLikeOrg(response: ResponseFavProduct) = organizationApi.getLikeOrg(response)

}