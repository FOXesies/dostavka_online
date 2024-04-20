package com.wayplaner.learn_room.home.data.repository

import com.wayplaner.learn_room.home.domain.repository.HomeApi

class HomeApiRepositoryImpl(private val mainService: HomeApi) {

    // получение всех ресторанов
    suspend fun getOrganizations() = mainService.getOrganizations()/*

    suspend fun getPhotoByOrganization() = mainService.getPhotoByOrganization()*/

}