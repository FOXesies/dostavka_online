package com.wayplaner.learn_room.admin.basic_info.data.repository

import com.wayplaner.learn_room.admin.basic_info.domain.model.OrganizationResponse
import com.wayplaner.learn_room.admin.basic_info.domain.repository.BasicInfoRepository
import com.wayplaner.learn_room.createorder.domain.model.Address
import okhttp3.MultipartBody

class BasicInfoImpl(private val repository: BasicInfoRepository) {
    suspend fun getInfo(idOrg: Long) = repository.getInfo(idOrg)
    suspend fun uploadImage(idOrg: Long, image: MultipartBody.Part) = repository.uploadImage(image, idOrg)
    suspend fun updateInfo(idOrg: Long, name: String, phone: String, address: Address) = repository.updateInfo(OrganizationResponse(idorganization = idOrg, nameOrganization = name))
    suspend fun getImage(idOrg: Long) = repository.getImage(idOrg).body()?.byteStream()?.readBytes() ?: byteArrayOf()
}