package com.wayplaner.learn_room.basket.data.repository

import com.wayplaner.learn_room.basket.domain.model.SendBasketProduct
import com.wayplaner.learn_room.basket.domain.repository.BasketApi
import com.wayplaner.learn_room.order.data.model.BasketItem

class BasketApiImpl(private val basketApi: BasketApi) {

    suspend fun getBasket(idUser: Long) = basketApi.getBasket(idUser)
    suspend fun addProduct(product: SendBasketProduct) = basketApi.addProduct(product)
    suspend fun deleteProduct(product: SendBasketProduct) = basketApi.deleteProduct(product)
    suspend fun plusProduct(product: SendBasketProduct) = basketApi.plusProduct(product)
    suspend fun minusProduct(product: SendBasketProduct) = basketApi.minusProduct(product)

}