package com.wayplaner.learn_room.product.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wayplaner.learn_room.product.data.repository.ProductRepositoryImpl
import com.wayplaner.learn_room.product.domain.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProductModelView @Inject constructor(
    private val productRepositoryImpl: ProductRepositoryImpl
): ViewModel() {

    private val product = MutableLiveData<Product>()
    private val inBasket = MutableLiveData<Boolean>()
    fun getProduct() = product
    fun isInBasket() = inBasket

    fun loadProductById(productId: Long) {
        viewModelScope.launch {
            val response = productRepositoryImpl.getProductinfo(productId)
            if(response.isSuccessful)
                product.postValue(response.body())
            else{
                // Обработка ошибки
                val errorBody = response.errorBody()
                if (errorBody != null) {
                    Timber.e(errorBody.string())
                }
            }
        }
    }

    fun getInBasket(userId: Long, productId: Long) {
        viewModelScope.launch {
            val response = productRepositoryImpl.checkInBasket(userId, productId)
            if(response.isSuccessful)
                inBasket.postValue(response.body())
            else{
                // Обработка ошибки
                val errorBody = response.errorBody()
                if (errorBody != null) {
                    Timber.e(errorBody.string())
                }
            }
        }
    }
}