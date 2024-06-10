package com.wayplaner.learn_room.favotitea.domain.repository

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Singleton

@Singleton
interface FavoriteApi {
    @GET("favorite/all_favorite_org/{id}")
    suspend fun getOrgs(@Path("id") idUser: Long): Response<ResponseBody>
    @GET("favorite/all_favorite_product/{id}")
    suspend fun getProducts(@Path("id") idUser: Long): Response<ResponseBody>
}