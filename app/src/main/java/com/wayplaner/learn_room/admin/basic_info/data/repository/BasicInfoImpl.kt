package com.wayplaner.learn_room.admin.basic_info.data.repository

import com.wayplaner.learn_room.admin.basic_info.domain.model.OrganizationResponse
import com.wayplaner.learn_room.admin.basic_info.domain.repository.BasicInfoRepository

class BasicInfoImpl(private val repository: BasicInfoRepository) {
    suspend fun getInfo(idOrg: Long) = repository.getInfo(idOrg)
    suspend fun updateInfo(response: OrganizationResponse) = repository.updateInfo(response)
    suspend fun getImage(idOrg: Long) = repository.getImage(idOrg)
}