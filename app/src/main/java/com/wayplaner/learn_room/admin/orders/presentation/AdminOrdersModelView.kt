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
import com.wayplaner.learn_room.orderlist.domain.model.OrderPreviewDTO
import com.wayplaner.learn_room.orderlist.util.UiOrderEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminOrdersModelView @Inject constructor(
    private val listOrderImpl: AdminOrderImpl
): ViewModel()  {

    private val activeOrder_ = MutableLiveData<List<OrderPreviewDTO>?>(mutableListOf())
    val activeOrder: LiveData<List<OrderPreviewDTO>?> = activeOrder_

    fun onEvent(event: UiOrderEvent){
        when(event){
            is UiOrderEvent.OpenActiveOrder -> {
                loadOrders()
            }
            is UiOrderEvent.SwitchOrder -> {
                switchStatus(event.idOrder, event.status)
            }
            is UiOrderEvent.CancelOrder -> {
/*                cancelOrder(event.idOrder)
                return*/
            }
            is UiOrderEvent.OpenCanceledOrder -> {
            }
            else -> {

            }
        }
    }

    private fun loadOrders() {
        viewModelScope.launch {
            val response = listOrderImpl.getActiveOrders(AdminAccount.idOrg)
            if(response.isSuccessful) {
                activeOrder_.postValue(response.body()!!.toMutableList())
            }
        }
    }

    private fun switchStatus(idOrder: Long, status: StatusOrder){
        viewModelScope.launch {
            listOrderImpl.switchStatus(AdminStatusResponse(idOrder, status.getNext()?: StatusOrder.COMPLETE_ORDER))
        }
    }
}