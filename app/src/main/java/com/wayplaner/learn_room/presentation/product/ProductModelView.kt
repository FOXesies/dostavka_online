package com.wayplaner.learn_room.presentation.product

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wayplaner.learn_room.data.repository.ProductRepositoryImpl
import com.wayplaner.learn_room.domain.model.Product
import com.wayplaner.learn_room.navigation.AppDestinations.PRODUCT_ID_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProductModelView @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val productRepositoryImpl: ProductRepositoryImpl
): ViewModel() {
    private val productId: Long = checkNotNull(savedStateHandle[PRODUCT_ID_KEY])

    private val product = MutableLiveData<Product>()
    fun getProduct() = product;

    init {
        loadProductById(productId)
    }

    private fun loadProductById(productId: Long) {
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
}