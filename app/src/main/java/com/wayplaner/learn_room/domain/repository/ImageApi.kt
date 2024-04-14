package com.wayplaner.learn_room.domain.repository

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import java.io.InputStream
import javax.inject.Singleton

@Singleton
interface ImageApi {

    @GET("upload/img/{id}")
    suspend fun getImage(@Path("id") id: Long): Response<InputStream>

}