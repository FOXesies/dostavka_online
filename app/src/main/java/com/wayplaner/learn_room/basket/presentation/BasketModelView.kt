package com.wayplaner.learn_room.basket.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wayplaner.learn_room.basket.data.repository.BasketApiImpl
import com.wayplaner.learn_room.basket.domain.model.SendBasketProduct
import com.wayplaner.learn_room.basket.util.Basketproduct
import com.wayplaner.learn_room.basket.util.UiBasketEvent
import com.wayplaner.learn_room.order.data.model.BasketItem
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

    private val uiBasketEvent_ = MutableLiveData<UiBasketEvent>(UiBasketEvent.EmptyBasket)
    val uiBasketEvent: LiveData<UiBasketEvent> = uiBasketEvent_

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
            uiBasketEvent_.value = when(basket_.value?.productsPick?.size == 0){
                false -> UiBasketEvent.NormalBasket
                true -> UiBasketEvent.EmptyBasket
            }
        }
    }

    fun addProduct(productId: Long) {
        viewModelScope.launch {
            basketApiImpl.addProduct(SendBasketProduct(productId, 1))
            loadBasket()
        }
    }

    fun deleteProduct(productId: Long) {
        viewModelScope.launch {
            basketApiImpl.deleteProduct(SendBasketProduct(productId, 1))
            productBasketCount.postValue(0)
            loadBasket()
        }
    }

    fun plusProduct(productId: Long) {
        viewModelScope.launch {
            basketApiImpl.plusProduct(SendBasketProduct(productId, 1))
            loadBasket()
        }
    }

    fun minusProduct(productId: Long) {
        viewModelScope.launch {
            basketApiImpl.minusProduct(SendBasketProduct(productId, 1))
            loadBasket()
        }
    }

}