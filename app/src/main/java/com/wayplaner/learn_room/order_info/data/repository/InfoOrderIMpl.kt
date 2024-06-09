package com.wayplaner.learn_room.order_info.data.repository

import com.google.gson.Gson
import com.wayplaner.learn_room.order_info.domain.data.BasicInfoOrderUser
import com.wayplaner.learn_room.order_info.domain.repository.InfoOrderApi
import retrofit2.Response

class InfoOrderIMpl (private val repository: InfoOrderApi, private val gson: Gson){
    suspend fun getInfoOrder(idOrder: Long): Response<BasicInfoOrderUser> {
        val response = repository.getInfo(idOrder)
        val responseBody = response.body()?.string()
        val info = gson.fromJson(responseBody, BasicInfoOrderUser::class.java)
        return Response.success(info)
    }
}