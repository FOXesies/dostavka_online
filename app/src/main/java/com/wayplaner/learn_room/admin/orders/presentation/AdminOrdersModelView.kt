package com.wayplaner.learn_room.admin.orders.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wayplaner.learn_room.admin.orders.data.repository.AdminOrderImpl
import com.wayplaner.learn_room.admin.orders.domain.model.AdminStatusResponse
import com.wayplaner.learn_room.admin.util.AdminAccount
import com.wayplaner.learn_room.createorder.domain.model.StatusOrder
import com.wayplaner.learn_room.createorder.domain.model.StatusOrder.Companion.getNext
import com.wayplaner.learn_room.orderlist.domain.model.CancelOrderPreview
import com.wayplaner.learn_room.orderlist.domain.model.CompleteOrderPreview
import com.wayplaner.learn_room.orderlist.domain.model.OrderPreviewDTO
import com.wayplaner.learn_room.orderlist.domain.model.ResponseCancel
import com.wayplaner.learn_room.orderlist.util.UiOrderEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminOrdersModelView @Inject constructor(
    private val listOrderImpl: AdminOrderImpl
): ViewModel()  {

    private val activeOrder_ = MutableLiveData<List<OrderPreviewDTO>?>(mutableListOf())
    val activeOrder: LiveData<List<OrderPreviewDTO>?> = activeOrder_

    private val cancelOrder_ = MutableLiveData<List<CancelOrderPreview>?>(mutableListOf())
    val cancelOrder: LiveData<List<CancelOrderPreview>?> = cancelOrder_

    private val completeOrder_ = MutableLiveData<List<CompleteOrderPreview>?>(mutableListOf())
    val completeOrder: LiveData<List<CompleteOrderPreview>?> = completeOrder_


    fun onEvent(event: UiOrderEvent){
        when(event){
            is UiOrderEvent.OpenActiveOrder -> {

            }
            is UiOrderEvent.SwitchOrder -> {
                switchStatus(event.idOrder, event.status)
            }
            is UiOrderEvent.CancelOrder -> {
                cancelOrder(event.idOrder, event.comment)
            }
            is UiOrderEvent.OpenCanceledOrder -> {
            }
            else -> {

            }
        }
    }

    private fun loadOrders(idOrg: Long) {
        viewModelScope.launch {
            val response = listOrderImpl.getActiveOrders(idOrg)
            if(response.isSuccessful) {
                activeOrder_.postValue(response.body()!!.toMutableList())
            }
        }
    }
    init {
        viewModelScope.launch {
            loadOrders(AdminAccount.idOrg)
            loadCompledOrder(AdminAccount.idOrg)
            loadCanceledOrder(AdminAccount.idOrg)
            delay(3500)
        }
    }

    private fun loadCanceledOrder(uid: Long) {
        viewModelScope.launch {
            val response = listOrderImpl.getCanceledORders(uid)
            if(response.isSuccessful) {
                cancelOrder_.postValue(response.body()!!.toMutableList())
            }
        }
    }

    private fun loadCompledOrder(uid: Long) {
        viewModelScope.launch {
            val response = listOrderImpl.getCompleteORders(uid)
            if(response.isSuccessful) {
                completeOrder_.postValue(response.body()!!.toMutableList())
            }
        }
    }

    private fun cancelOrder(idOrder: Long, comment: String) {
        viewModelScope.launch {
            listOrderImpl.cancelOrder(ResponseCancel(idOrder, comment))
            val list = cancelOrder_.value
            list?.toMutableList()?.removeIf { it.orderPreview!!.idOrder == idOrder }
            cancelOrder_.postValue(list)
        }
    }

    private fun switchStatus(idOrder: Long, status: StatusOrder){
        viewModelScope.launch {
            listOrderImpl.switchStatus(AdminStatusResponse(idOrder, status.getNext()?: StatusOrder.COMPLETE))
        }
    }
}