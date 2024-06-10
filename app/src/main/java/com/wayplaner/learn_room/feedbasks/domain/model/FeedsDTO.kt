package com.wayplaner.learn_room.feedbasks.domain.model

data class FeedsDTO(
    val idFeedback: Long,
    val rating: Int,
    val comment: String? = null,
    val userName: String,
    val dateCreate: String
)