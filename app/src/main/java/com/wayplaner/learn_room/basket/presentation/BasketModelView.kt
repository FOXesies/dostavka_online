package com.wayplaner.learn_room.basket.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wayplaner.learn_room.basket.data.repository.BasketApiImpl
import com.wayplaner.learn_room.basket.domain.model.SendBasketProduct
import com.wayplaner.learn_room.basket.util.Basketproduct
import com.wayplaner.learn_room.order.data.model.BasketItem
import com.wayplaner.learn_room.order.data.model.ProductInBasket
import com.wayplaner.learn_room.product.domain.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BasketModelView @Inject constructor(
    private val basketApiImpl: BasketApiImpl
): ViewModel() {

    private val basket_ = MutableLiveData(BasketItem())
    val basketItem: LiveData<BasketItem?> = basket_

    private val productBasketCount = MutableLiveData<Int>()

    fun setCountInProducts(count: Int) {
        productBasketCount.postValue(count)
    }
    fun isInBasket() = productBasketCount

    fun getInBasket(userId: Long, productId: Long) {
        viewModelScope.launch {
            val response = basketApiImpl.checkInBasket(userId, productId)
            if(response.isSuccessful)
                productBasketCount.postValue(response.body()?.valueInt?: 0)
            else{
                // Обработка ошибки
                val errorBody = response.errorBody()
                if (errorBody != null) {
                    Timber.e(errorBody.string())
                }
            }
        }
    }

    init {
        loadBasket()
    }

    fun saveInfoInOrder(){
        Basketproduct.summ = basketItem.value?.summ
    }

    private fun loadBasket(){
        viewModelScope.launch {
            basket_.value = basketApiImpl.getBasket(1).body()
        }
    }

    fun addProduct(product: Product) {
        viewModelScope.launch {
            basketApiImpl.addProduct(SendBasketProduct(product.idProduct, 1))

            val item = basket_.value!!.copy()
            item.summ += product.price!!
            item.productsPick.add(ProductInBasket(product, 1))
            basket_.value = item
        }
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            basketApiImpl.deleteProduct(SendBasketProduct(product.idProduct, 1))
            productBasketCount.postValue(0)

            val item = basket_.value!!.copy()
            val removedItem = item.productsPick.find { it.product!!.idProduct == product.idProduct }
            item.summ -= (product.price!! * removedItem!!.count)
            item.productsPick.remove(removedItem)
            basket_.value = item
        }
    }

    fun plusProduct(product: Product) {
        viewModelScope.launch {
            basketApiImpl.plusProduct(SendBasketProduct(product.idProduct, 1))

            val item = basket_.value!!.copy()
            item.summ += product.price!!
            item.productsPick.find { it.product!!.idProduct == product.idProduct }!!.count++
            basket_.value = item
        }
    }

    fun minusProduct(product: Product) {
        viewModelScope.launch {
            basketApiImpl.minusProduct(SendBasketProduct(product.idProduct, 1))

            val item = basket_.value!!.copy()
            item.summ -= product.price!!
            item.productsPick.find { it.product!!.idProduct == product.idProduct }!!.count--
            basket_.value = item
        }
    }

}