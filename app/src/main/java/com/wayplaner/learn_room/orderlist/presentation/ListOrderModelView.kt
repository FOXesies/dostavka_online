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

    private val combineOrder_ = MutableLiveData<List<Any>?>(mutableListOf())
    val combineOrder: LiveData<List<Any>?> = combineOrder_

    fun onEvent(event: UiOrderEvent){
        when(event){
            is UiOrderEvent.OpenActiveOrder -> {
                loadOrders(1)
            }
            is UiOrderEvent.OpenActiveOrderSelf -> {
            }
            else -> {}
        }
    }

    private fun loadOrders(uid: Long) {
        viewModelScope.launch {
            val mutList = mutableListOf<Any>()

            val response = listOrderImpl.getAllOrder(uid)
            if(response.isSuccessful) {
                mutList.addAll(response.body()!!.toMutableList())
                //combineOrder_.value!!.addAll(response.body()?: listOf())
            }

            val responseSelf = listOrderImpl.getAllSelfOrder(uid)
            if(responseSelf.isSuccessful) {
                mutList.addAll(responseSelf.body()!!.toMutableList())
            }

            if(response.isSuccessful || responseSelf.isSuccessful){
                setUiValueActive(UiOrderListEvent.NormalActiveOrder)
            }

            sort(mutList)
            //setUiValueActive(UiOrderListEvent.EmptyActiveOrderList)
        }
    }

    private fun loadOrdersSelf(uid: Long) {
        viewModelScope.launch {
            val response = listOrderImpl.getAllSelfOrder(uid)
            if(response.isSuccessful) {/*
                orderSelf_.value = listOrderImpl.getAllSelfOrder(uid).body()*/
                //combineOrder_.value!!.addAll(listOrderImpl.getAllSelfOrder(uid).body()?: listOf())
                //combineOrder_.value!!.addAll(orderSelf_.value!!)
                //sort(mutList)
                setUiValueActive(UiOrderListEvent.NormalActiveSelfOrder)
            }
            setUiValueActive(UiOrderListEvent.EmptyActiveSelfOrderList)
        }
    }

    private fun sort(mutList: MutableList<Any>) {
        val sortedList = mutList.sortedBy {
            when (it) {
                is OrderSelfDelivery -> it.idOrder
                is Order -> it.orderId ?: 0
                else -> 0
            }
        }

        combineOrder_.postValue(sortedList)
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