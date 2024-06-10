package com.wayplaner.learn_room.product.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wayplaner.learn_room.product.data.repository.ProductRepositoryImpl
import com.wayplaner.learn_room.product.domain.model.Product
import com.wayplaner.learn_room.utils.CustomerAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.example.favorite.entity.DTO.ResponseFavProduct
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProductModelView @Inject constructor(
    private val productRepositoryImpl: ProductRepositoryImpl
): ViewModel() {

    private val product = MutableLiveData<Product>()
    fun getProduct() = product

    private var isLike = MutableLiveData(false)
    fun isLike() = isLike

    fun loadProductById(productId: Long) {
        viewModelScope.launch {
            val response = productRepositoryImpl.getProductinfo(productId)
            val like = productRepositoryImpl.getLike(ResponseFavProduct(idUser = CustomerAccount.info!!.profileUUID, idProduct = productId))
            if(response.isSuccessful) {
                product.postValue(response.body())
                isLike.postValue(like.body())
            }
            else{
                // Обработка ошибки
                val errorBody = response.errorBody()
                if (errorBody != null) {
                    Timber.e(errorBody.string())
                }
            }
        }
    }

    fun like(idProduct: Long) {
        viewModelScope.launch {
            productRepositoryImpl.like(ResponseFavProduct(idUser = CustomerAccount.info!!.profileUUID, idProduct = idProduct))
            isLike.postValue(!isLike.value!!)
        }

    }
}