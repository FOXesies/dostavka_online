package com.wayplaner.learn_room.orderlist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wayplaner.learn_room.createorder.domain.model.OrderSelfDelivery
import com.wayplaner.learn_room.orderlist.data.repository.ListOrderImpl
import com.wayplaner.learn_room.orderlist.util.UiOrderEvent
import com.wayplaner.learn_room.orderlist.util.UiOrderListEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.example.order.model.Order
import javax.inject.Inject

@HiltViewModel
class ListOrderModelView @Inject constructor(
    private val listOrderImpl: ListOrderImpl
): ViewModel()  {

    private val eventActive_ = MutableLiveData<UiOrderListEvent>()
    val eventActive: LiveData<UiOrderListEvent> = eventActive_

    private val eventActiveSelf_ = MutableLiveData<UiOrderListEvent>()
    val eventActiveSelf: LiveData<UiOrderListEvent> = eventActiveSelf_

    private val eventComplete_ = MutableLiveData<UiOrderListEvent>()
    val eventComplete: LiveData<UiOrderListEvent> = eventComplete_

    private val eventCanceled_ = MutableLiveData<UiOrderListEvent>()
    val eventCanceled: LiveData<UiOrderListEvent> = eventCanceled_

    private val order_ = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>> = order_

    private val orderSelf_ = MutableLiveData<List<OrderSelfDelivery>>()
    val ordersSelf: LiveData<List<OrderSelfDelivery>> = orderSelf_

    fun onEvent(event: UiOrderEvent){
        when(event){
            is UiOrderEvent.OpenActiveOrder -> {
                loadOrders(1)
            }
            is UiOrderEvent.OpenActiveOrderSelf -> {
                loadOrdersSelf(1)
            }
            else -> {}
        }
    }

    private fun loadOrders(uid: Long) {
        viewModelScope.launch {
            val response = listOrderImpl.getAllOrder(uid)
            if(response.isSuccessful) {
                order_.value = listOrderImpl.getAllOrder(uid).body()
                setUiValueActive(UiOrderListEvent.NormalActiveOrder)
            }
            setUiValueActive(UiOrderListEvent.EmptyActiveOrderList)
        }
    }

    private fun loadOrdersSelf(uid: Long) {
        viewModelScope.launch {
            val response = listOrderImpl.getAllSelfOrder(uid)
            if(response.isSuccessful) {
                orderSelf_.value = listOrderImpl.getAllSelfOrder(uid).body()
                setUiValueActive(UiOrderListEvent.NormalActiveSelfOrder)
            }
            setUiValueActive(UiOrderListEvent.EmptyActiveSelfOrderList)
        }
    }

    fun setUiValueActive(event: UiOrderListEvent){
        eventActive_.value = event
    }
    fun setUiValueComplete(event: UiOrderListEvent){
        eventComplete_.value = event
    }
    fun setUiValueCanceled(event: UiOrderListEvent){
        eventCanceled_.value = event
    }

}