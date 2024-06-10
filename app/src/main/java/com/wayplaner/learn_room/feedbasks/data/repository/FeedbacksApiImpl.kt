package com.wayplaner.learn_room.feedbasks.data.repository

import com.wayplaner.learn_room.feedbasks.domain.repository.FeedbackApi

class FeedbacksApiImpl(private val feedbacksApi: FeedbackApi) {
    suspend fun feedbacksApi(idOrg: Long) = feedbacksApi.getFeedbakcsByorg(idOrg)

}