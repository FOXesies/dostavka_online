package com.wayplaner.learn_room.orderlist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wayplaner.learn_room.orderlist.data.repository.ListOrderImpl
import com.wayplaner.learn_room.orderlist.domain.model.OrderPreviewDTO
import com.wayplaner.learn_room.orderlist.util.UiOrderEvent
import com.wayplaner.learn_room.orderlist.util.UiOrderListEvent
import com.wayplaner.learn_room.utils.CustomerAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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

    private val activeOrder_ = MutableLiveData<List<OrderPreviewDTO>?>(mutableListOf())
    val activeOrder: LiveData<List<OrderPreviewDTO>?> = activeOrder_

    private val cancelCombineOrder_ = MutableLiveData<List<Any>?>(mutableListOf())
    val cancelCombineOrder: LiveData<List<Any>?> = cancelCombineOrder_

    private val completeCombineOrder_ = MutableLiveData<List<Any>?>(mutableListOf())
    val completeCombineOrder: LiveData<List<Any>?> = completeCombineOrder_

    fun onEvent(event: UiOrderEvent){
        when(event){
            is UiOrderEvent.OpenActiveOrder -> {
                loadOrders(CustomerAccount.info!!.profileUUID)
            }
            is UiOrderEvent.CancelOrder -> {
                cancelOrder(event.idOrder)
                return
                //cancelOrderSelf(event.idOrder)
            }
            is UiOrderEvent.OpenCanceledOrder -> {
                //loadAllCanceledOrder(CustomerAccount.info!!.profileUUID)
            }
            else -> {}
        }
    }

    private fun loadOrders(uid: Long) {
        viewModelScope.launch {
            val response = listOrderImpl.getAllOrder(uid)
            if(response.isSuccessful) {
                activeOrder_.postValue(response.body()!!.toMutableList())
            }
        }
    }



    private fun cancelOrder(idOrder: Long) {
        /*viewModelScope.launch {
            listOrderImpl.cancelOrder(ResponseCancel(idOrder, ""))
            val value = combineOrder_.value!!.toMutableList()
            value.removeIf {
                if (it.javaClass == OrderSelfDelivery::class.java)
                    (it as OrderSelfDelivery).idOrderSelf == idOrder
                else
                    false
            }

            combineOrder_.postValue(value)
        }*/
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