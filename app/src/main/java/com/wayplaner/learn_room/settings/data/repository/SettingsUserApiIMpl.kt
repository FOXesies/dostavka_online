package com.wayplaner.learn_room.settings.data.repository

import com.wayplaner.learn_room.auth.domain.model.DTO.UserResponse
import com.wayplaner.learn_room.settings.domain.repository.SettingsUserApi

class SettingsUserApiIMpl(private val settingsUserApi: SettingsUserApi) {
    suspend fun getInfo(id: Long) = settingsUserApi.getInfo(id)

    suspend fun updateInfo(response: UserResponse) = settingsUserApi.updateInfo(response)
}