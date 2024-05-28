package com.wayplaner.learn_room.admin.orders.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wayplaner.learn_room.admin.orders.data.repository.AdminOrderImpl
import com.wayplaner.learn_room.admin.orders.domain.model.AdminStatusResponse
import com.wayplaner.learn_room.admin.util.AdminAccount
import com.wayplaner.learn_room.createorder.domain.model.Order
import com.wayplaner.learn_room.createorder.domain.model.OrderSelfDelivery
import com.wayplaner.learn_room.createorder.domain.model.StatusOrder
import com.wayplaner.learn_room.createorder.domain.model.StatusOrder.Companion.getNext
import com.wayplaner.learn_room.orderlist.domain.model.ResponseCancel
import com.wayplaner.learn_room.orderlist.util.UiOrderEvent
import com.wayplaner.learn_room.orderlist.util.UiOrderListEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminOrdersModelView @Inject constructor(
    private val listOrderImpl: AdminOrderImpl
): ViewModel()  {

    private val eventActive_ = MutableLiveData<UiOrderListEvent>()
    val eventActive: LiveData<UiOrderListEvent> = eventActive_

    private val eventActiveSelf_ = MutableLiveData<UiOrderListEvent>()
    val eventActiveSelf: LiveData<UiOrderListEvent> = eventActiveSelf_

    private val eventComplete_ = MutableLiveData<UiOrderListEvent>()
    val eventComplete: LiveData<UiOrderListEvent> = eventComplete_

    private val eventCanceled_ = MutableLiveData<UiOrderListEvent>()
    val eventCanceled: LiveData<UiOrderListEvent> = eventCanceled_

    private val combineOrder_ = MutableLiveData<List<Any>?>(mutableListOf())
    val combineOrder: LiveData<List<Any>?> = combineOrder_

    private val cancelCombineOrder_ = MutableLiveData<List<Any>?>(mutableListOf())
    val cancelCombineOrder: LiveData<List<Any>?> = cancelCombineOrder_

    private val completeCombineOrder_ = MutableLiveData<List<Any>?>(mutableListOf())
    val completeCombineOrder: LiveData<List<Any>?> = completeCombineOrder_

    fun onEvent(event: UiOrderEvent){
        when(event){
            is UiOrderEvent.OpenActiveOrder -> {
                loadOrders(AdminAccount.idOrg)
            }
            is UiOrderEvent.OpenActiveOrderSelf -> {

            }
            is UiOrderEvent.SwitchOrderSelf -> {
                switchStatusSelf(event.idOrder, event.status)
            }
            is UiOrderEvent.SwitchOrder -> {
                switchStatus(event.idOrder, event.status)
            }
            is UiOrderEvent.CancelOrder -> {
                if(event.isDeliviry){
                    cancelOrder(event.idOrder)
                    return
                }
                cancelOrderSelf(event.idOrder)
            }
            is UiOrderEvent.OpenCanceledOrder -> {
                loadAllCanceledOrder(AdminAccount.idOrg)
            }
            else -> {}
        }
    }

    private fun switchStatus(idOrder: Long, status: StatusOrder){
        viewModelScope.launch {
            listOrderImpl.switchStatus(AdminStatusResponse(idOrder, status.getNext()?: StatusOrder.COMPLETE_ORDER))
        }
    }
    private fun switchStatusSelf(idOrder: Long, status: StatusOrder){
        viewModelScope.launch {
            listOrderImpl.switchStatusSelf(AdminStatusResponse(idOrder, status.getNext()?: StatusOrder.COMPLETE_ORDER))
        }
    }

    private fun loadOrders(uid: Long) {
        viewModelScope.launch {
            val mutList = mutableListOf<Any>()

            val response = listOrderImpl.getActiveOrder(uid)
            if(response.isSuccessful) {
                mutList.addAll(response.body()!!.toMutableList())
            }

            val responseSelf = listOrderImpl.getActiveOrderSelf(uid)
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

    private fun sort(mutList: MutableList<Any>) {
        val sortedList = mutList.sortedBy {
            when (it) {
                is OrderSelfDelivery -> it.idOrderSelf
                is Order -> it.orderId ?: 0
                else -> 0
            }
        }

        combineOrder_.postValue(sortedList)
    }

    private fun loadAllCanceledOrder(idUser: Long) {
        viewModelScope.launch {
            val mutList = mutableListOf<Any>()

            val response = listOrderImpl.getCancelOrder(idUser)
            if(response.isSuccessful) {
                mutList.addAll(response.body()!!.toMutableList())
            }

            val responseSelf = listOrderImpl.getCancelOrderSelf(idUser)
            if(responseSelf.isSuccessful) {
                mutList.addAll(responseSelf.body()!!.toMutableList())
            }

            if(response.isSuccessful || responseSelf.isSuccessful){
                cancelCombineOrder_.postValue(mutList)
                return@launch
            }
            cancelCombineOrder_.postValue(listOf())
        }
    }

    private fun cancelOrder(idOrder: Long) {
        viewModelScope.launch {
            listOrderImpl.cancelOrder(ResponseCancel(idOrder, ""))
            val value = combineOrder_.value!!.toMutableList()
            value.removeIf {
                if (it.javaClass == OrderSelfDelivery::class.java)
                    (it as OrderSelfDelivery).idOrderSelf == idOrder
                else
                    false
            }

            combineOrder_.postValue(value)
        }
    }

    private fun cancelOrderSelf(idOrder: Long) {
        viewModelScope.launch {
            listOrderImpl.cancelOrderSelf(ResponseCancel(idOrder, ""))
            val value = combineOrder_.value!!.toMutableList()
            value.removeIf {
                if (it.javaClass == OrderSelfDelivery::class.java)
                    (it as OrderSelfDelivery).idOrderSelf == idOrder
                else
                    false
            }

            combineOrder_.postValue(value)
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