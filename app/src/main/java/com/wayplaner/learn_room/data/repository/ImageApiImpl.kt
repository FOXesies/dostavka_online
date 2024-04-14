package com.wayplaner.learn_room.data.repository

import com.wayplaner.learn_room.domain.repository.ImageApi

class ImageApiImpl(private val imageApi: ImageApi) {

    suspend fun getImage(id: Long) = imageApi.getImage(id)

}