package com.wayplaner.learn_room.auth.domain.repository

import com.wayplaner.learn_room.auth.domain.model.DTO.ResponseAuth
import com.wayplaner.learn_room.auth.domain.model.DTO.SingInRequest
import com.wayplaner.learn_room.auth.domain.model.DTO.SingUpRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthCustomerRepository {
    @GET("organizations/cities")
    suspend fun getCities(): Response<List<String>>
    @POST("auth/sing-in")
    suspend fun sing_in(@Body singInRequest: SingInRequest): Response<ResponseAuth>
    @GET("organizations/get_org_user/{id}")
    suspend fun getOrgByUser(@Path("id") idUser: Long): Response<Long>
    @POST("auth/sing-up")
    suspend fun sing_up(@Body singUpRequest: SingUpRequest): Response<ResponseAuth>
}