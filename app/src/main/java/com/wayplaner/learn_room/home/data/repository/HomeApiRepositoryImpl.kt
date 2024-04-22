package com.wayplaner.learn_room.home.data.repository

import com.wayplaner.learn_room.home.domain.repository.HomeApi

class HomeApiRepositoryImpl(private val mainService: HomeApi) {

    // получение всех ресторанов
    suspend fun getOrganizations(category: String) = mainService.getOrganizations(category)
    suspend fun getCities() = mainService.getCities()/*

    suspend fun getPhotoByOrganization() = mainService.getPhotoByOrganization()*/

}