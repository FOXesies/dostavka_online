package com.wayplaner.learn_room.favotitea.data.reposirory

import com.google.gson.Gson
import com.wayplaner.learn_room.favotitea.domain.repository.FavoriteApi
import com.wayplaner.learn_room.home.domain.model.OrganizationDTO
import com.wayplaner.learn_room.organization.domain.model.ResponseProductOrg
import retrofit2.Response

class FavoriteApiImpl(
    private val favoriteApi: FavoriteApi,
    private val gsonOrd: Gson,
    private val gsonProductsAdmin: Gson
) {
    suspend fun getOrgs(idUser: Long): Response<List<OrganizationDTO>> {
        val response = favoriteApi.getOrgs(idUser)
        val responseBody = response.body()?.string()
        val organizations = gsonOrd.fromJson(responseBody, Array<OrganizationDTO>::class.java).toList()
        return Response.success(organizations)
    }

    suspend fun getProducts(idOrg: Long): Response<List<ResponseProductOrg>> {
        val response = favoriteApi.getProducts(idOrg)
        val responseBody = response.body()?.string()
        val organizations = gsonProductsAdmin.fromJson(responseBody, Array<ResponseProductOrg>::class.java).toList()
        return Response.success(organizations)
    }

}