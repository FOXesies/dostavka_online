package com.wayplaner.learn_room.createorder.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wayplaner.learn_room.createorder.data.repository.OrderApiImpl
import com.wayplaner.learn_room.createorder.domain.repository.OrderApi
import com.wayplaner.learn_room.order.data.model.BasketItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.example.order.model.Order
import org.example.order.model.ProductInOrder
import javax.inject.Inject

@HiltViewModel
class CreateOrderModelView
@Inject constructor(
    private val orderApiImpl: OrderApiImpl
):ViewModel() {

    private val order_ = mutableStateOf(Order())

    fun saveProducts(productInBasket: BasketItem){
        viewModelScope.launch {
            order_.value.summ = productInBasket.summ
            order_.value.productOrder = productInBasket.productsPick.map { ProductInOrder(count = it.count, idProduct = it.product!!.idProduct) }
        }
    }

    fun saveInfo(){

    }
}