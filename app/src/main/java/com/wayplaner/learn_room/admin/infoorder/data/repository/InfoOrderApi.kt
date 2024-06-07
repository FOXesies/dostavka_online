package com.wayplaner.learn_room.admin.infoorder.data.repository


import com.google.gson.Gson
import com.wayplaner.learn_room.admin.infoorder.domain.model.SendBasicInfoOrder
import com.wayplaner.learn_room.admin.infoorder.domain.repository.AdminInfoOrderApi
import retrofit2.Response

class InfoOrderApiImpl(private val adminInfoOrderApi: AdminInfoOrderApi, private val gson: Gson) {
    suspend fun getInfoOrder(id: Long): Response<SendBasicInfoOrder>{
        val response = adminInfoOrderApi.getInfoOrder(id)
        val responseBody = response.body()?.string()
        val info = gson.fromJson(responseBody, SendBasicInfoOrder::class.java)
        return Response.success(info)
    }
}