package com.wayplaner.learn_room.orderlist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wayplaner.learn_room.orderlist.data.repository.ListOrderImpl
import com.wayplaner.learn_room.orderlist.domain.model.CancelOrderPreview
import com.wayplaner.learn_room.orderlist.domain.model.CompleteOrderPreview
import com.wayplaner.learn_room.orderlist.domain.model.OrderPreviewDTO
import com.wayplaner.learn_room.orderlist.domain.model.ResponseCancel
import com.wayplaner.learn_room.orderlist.util.UiOrderEvent
import com.wayplaner.learn_room.utils.CustomerAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListOrderModelView @Inject constructor(
    private val listOrderImpl: ListOrderImpl
): ViewModel()  {

    init {
        viewModelScope.launch {
            while (CustomerAccount.info != null) {
                loadOrders(CustomerAccount.info!!.profileUUID)
                loadCompledOrder(CustomerAccount.info!!.profileUUID)
                loadCanceledOrder(CustomerAccount.info!!.profileUUID)
                delay(3500)
            }
        }
    }

    private val activeOrder_ = MutableLiveData<List<OrderPreviewDTO>?>(mutableListOf())
    val activeOrder: LiveData<List<OrderPreviewDTO>?> = activeOrder_

    private val cancelOrder_ = MutableLiveData<List<CancelOrderPreview>?>(mutableListOf())
    val cancelOrder: LiveData<List<CancelOrderPreview>?> = cancelOrder_

    private val completeOrder_ = MutableLiveData<List<CompleteOrderPreview>?>(mutableListOf())
    val completeOrder: LiveData<List<CompleteOrderPreview>?> = completeOrder_

    fun onEvent(event: UiOrderEvent){
        when(event){
            is UiOrderEvent.OpenActiveOrder -> {
                //loadOrders(CustomerAccount.info!!.profileUUID)
            }
            is UiOrderEvent.CancelOrder -> {
                cancelOrder(event.idOrder, event.comment)
                return
            }
            is UiOrderEvent.OpenCanceledOrder -> {
                //loadCanceledOrder(CustomerAccount.info!!.profileUUID)
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

    private fun loadCanceledOrder(uid: Long) {
        viewModelScope.launch {
            val response = listOrderImpl.getAllCanceleOrder(uid)
            if(response.isSuccessful) {
                cancelOrder_.postValue(response.body()!!.toMutableList())
            }
        }
    }

    private fun loadCompledOrder(uid: Long) {
        viewModelScope.launch {
            val response = listOrderImpl.getAllCompleteOrder(uid)
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

    fun createFeedback(idOrder: Long, rating: Int, comment: String?) {
        viewModelScope.launch {
            listOrderImpl.createFeedback(FeedbackCreate(idOrder, rating, comment))
        }
    }


}

data class FeedbackCreate(
    val idOrder: Long,
    val rating: Int,
    val comment: String?
)