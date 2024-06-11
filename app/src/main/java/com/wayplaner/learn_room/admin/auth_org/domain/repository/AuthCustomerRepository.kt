package com.wayplaner.learn_room.auth.domain.repository

import com.wayplaner.learn_room.auth.domain.model.DTO.ResponseOrgAuth
import com.wayplaner.learn_room.auth.domain.model.DTO.SingInOrgRequest
import com.wayplaner.learn_room.auth.domain.model.DTO.SingUpOrgRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import javax.inject.Singleton

@Singleton
interface AuthOrgRepository {
    @GET("organizations/cities")
    suspend fun getCities(): Response<List<String>>
    @POST("admin/auth/sing-in")
    suspend fun sing_in(@Body singInRequest: SingInOrgRequest): Response<ResponseOrgAuth>
    @GET("organizations/get_org_user/{id}")
    suspend fun getOrgByUser(@Path("id") idUser: Long): Response<Long>
    @POST("admin/auth/sing-up")
    suspend fun sing_up(@Body singUpRequest: SingUpOrgRequest): Response<ResponseOrgAuth>
}