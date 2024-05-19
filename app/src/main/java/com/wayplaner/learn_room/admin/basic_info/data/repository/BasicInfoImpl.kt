package com.wayplaner.learn_room.admin.basic_info.data.repository

import com.wayplaner.learn_room.admin.basic_info.domain.repository.BasicInfoRepository
import com.wayplaner.learn_room.organization.model.OrganizationIdDTO
import okhttp3.MultipartBody

class BasicInfoImpl(private val repository: BasicInfoRepository) {
    suspend fun getInfo(idOrg: Long) = repository.getInfo(idOrg)
    suspend fun uploadImage(idOrg: Long, image: MultipartBody.Part) = repository.uploadImage(image, idOrg)
    suspend fun updateInfo(organization: OrganizationIdDTO) = repository.updateInfo(organization)
    suspend fun getImage(idOrg: Long) = repository.getImage(idOrg).body()?.byteStream()?.readBytes() ?: byteArrayOf()
}