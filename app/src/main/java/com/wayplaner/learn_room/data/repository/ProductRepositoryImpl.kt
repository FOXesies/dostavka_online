package com.wayplaner.learn_room.data.repository

import com.wayplaner.learn_room.domain.repository.ProductRepository

class ProductRepositoryImpl(private val productRepository: ProductRepository)  {

    suspend fun getProductinfo(idProduct: Long) = productRepository.getProductInfo(idProduct)
}