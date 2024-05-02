package com.wayplaner.learn_room.product.data.repository

import com.wayplaner.learn_room.product.domain.repository.ProductRepository

class ProductRepositoryImpl(private val productRepository: ProductRepository)  {

    suspend fun getProductinfo(idProduct: Long) = productRepository.getProductInfo(idProduct)
    suspend fun checkInBasket(idUser: Long, idProduct: Long) = productRepository.checkInBasket(idUser, idProduct)
}