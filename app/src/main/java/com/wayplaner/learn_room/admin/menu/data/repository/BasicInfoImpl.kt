package com.wayplaner.learn_room.admin.menu.data.repository

import com.wayplaner.learn_room.admin.menu.domain.model.ProductResponse
import com.wayplaner.learn_room.admin.menu.domain.repository.MenuProductRepository
import okhttp3.MultipartBody

class MenuProductImpl(private val repository: MenuProductRepository) {
    suspend fun getInfo(idProduct: Long) = repository.getInfo(idProduct)
    suspend fun uploadImage(idProduct: Long, image: MultipartBody.Part) = repository.uploadImage(image, idProduct)
    suspend fun updateInfo(idProduct: Long, name: String) = repository.updateInfo(ProductResponse(idProduct = idProduct, name = name))
    suspend fun getImage(idProduct: Long) = repository.getImage(idProduct).body()?.byteStream()?.readBytes() ?: byteArrayOf()
}