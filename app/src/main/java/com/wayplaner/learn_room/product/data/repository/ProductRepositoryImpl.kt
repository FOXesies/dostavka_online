package com.wayplaner.learn_room.product.data.repository

import com.google.gson.Gson
import com.wayplaner.learn_room.product.domain.model.Product
import com.wayplaner.learn_room.product.domain.repository.ProductRepository
import org.example.favorite.entity.DTO.ResponseFavProduct
import retrofit2.Response

class ProductRepositoryImpl(
    private val productRepository: ProductRepository,
    private val gson: Gson)  {

    suspend fun getProductinfo(idProduct: Long): Response<Product> {
        val response = productRepository.getProductInfo(idProduct)
        val responseBody = response.body()?.string()
        val product = gson.fromJson(responseBody, Product::class.java)
        return Response.success(product)
    }

    suspend fun getLike(response: ResponseFavProduct) = productRepository.getLikeProduct(response)
    suspend fun like(response: ResponseFavProduct) = productRepository.likeProduct(response)
}