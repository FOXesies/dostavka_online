package com.wayplaner.learn_room.admin.basic_info.data.repository

import com.google.gson.Gson
import com.wayplaner.learn_room.admin.basic_info.domain.model.BasicInfoResponse
import com.wayplaner.learn_room.admin.basic_info.domain.repository.BasicInfoRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class BasicInfoImpl(private val repository: BasicInfoRepository, private val gsonOrdBasic: Gson) {
    suspend fun getInfo(idOrg: Long): Response<BasicInfoResponse> {
        val response = repository.getInfo(idOrg)
        val responseBody = response.body()?.string()
        val info = gsonOrdBasic.fromJson(responseBody, BasicInfoResponse::class.java)
        return Response.success(info)
    }
    suspend fun updateInfo(organization: RequestBody, lists: MutableList<MultipartBody.Part>) = repository.updateInfo(lists, organization)
    suspend fun addInfo(organization: RequestBody, lists: MutableList<MultipartBody.Part>) = repository.addInfo(lists, organization)
}