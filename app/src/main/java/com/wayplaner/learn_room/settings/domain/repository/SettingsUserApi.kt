package com.wayplaner.learn_room.settings.domain.repository

import com.wayplaner.learn_room.auth.domain.model.DTO.ResponseAuth
import com.wayplaner.learn_room.auth.domain.model.DTO.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import javax.inject.Singleton

@Singleton
interface SettingsUserApi {
    @GET("auth/{id}")
    suspend fun getInfo(@Path("id") idUser: Long): UserResponse
    @POST("auth/update")
    suspend fun updateInfo(@Body response: UserResponse): ResponseAuth

}