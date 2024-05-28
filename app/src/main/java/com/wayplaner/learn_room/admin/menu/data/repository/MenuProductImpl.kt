package com.wayplaner.learn_room.admin.menu.data.repository

import com.wayplaner.learn_room.admin.menu.domain.repository.MenuProductRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class MenuProductImpl(private val repository: MenuProductRepository) {
    suspend fun getCategories() = repository.getCategories()
    suspend fun getInfo(idProduct: Long) = repository.getInfo(idProduct)
    suspend fun getAllInfo(idOrg: Long) = repository.getAllInfo(idOrg)
    suspend fun updateProducts(image: MultipartBody.Part, product: RequestBody) = repository.updateProducts(image, product)
    //suspend fun updateInfo(idProduct: Long, name: String) = repository.updateInfo(Product(idProduct = idProduct))
    suspend fun getImage(idProduct: Long) = repository.getImage(idProduct).body()?.byteStream()?.readBytes() ?: byteArrayOf()
}