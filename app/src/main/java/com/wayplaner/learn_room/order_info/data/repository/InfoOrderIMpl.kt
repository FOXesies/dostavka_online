package com.wayplaner.learn_room.order_info.data.repository

import com.wayplaner.learn_room.order_info.domain.repository.InfoOrderApi

class InfoOrderIMpl (private val repository: InfoOrderApi){
    suspend fun getInfoOrder(idOrder: Long) = repository.getInfo(idOrder)
}