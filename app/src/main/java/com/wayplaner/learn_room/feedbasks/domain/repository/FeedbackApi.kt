package com.wayplaner.learn_room.feedbasks.domain.repository

import com.wayplaner.learn_room.feedbasks.domain.model.FeedsDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Singleton

@Singleton
interface FeedbackApi {
    @GET("organizations/feedbacks/{id}")
    suspend fun getFeedbakcsByorg(@Path("id") idOrg: Long): Response<List<FeedsDTO>>
}