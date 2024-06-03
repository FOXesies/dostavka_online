package com.wayplaner.learn_room.home.data.repository

import com.google.gson.Gson
import com.wayplaner.learn_room.home.domain.model.OrganizationDTO
import com.wayplaner.learn_room.home.domain.repository.HomeApi
import retrofit2.Response

class HomeApiRepositoryImpl(
    private val mainService: HomeApi,
    private val gson: Gson
) {

    // получение всех ресторанов
    suspend fun getOrganizations(city: String): Response<List<OrganizationDTO>> {
        val response = mainService.getOrganizations(city)
        val responseBody = response.body()?.string()
        val organizations = gson.fromJson(responseBody, Array<OrganizationDTO>::class.java).toList()
        return Response.success(organizations)
    }
    suspend fun getCities() = mainService.getCities()/*

    suspend fun getPhotoByOrganization() = mainService.getPhotoByOrganization()*/

}
