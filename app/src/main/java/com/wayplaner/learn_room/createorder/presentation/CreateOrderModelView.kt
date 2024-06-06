package com.wayplaner.learn_room.createorder.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wayplaner.learn_room.basket.util.Basketproduct
import com.wayplaner.learn_room.createorder.data.repository.OrderApiImpl
import com.wayplaner.learn_room.createorder.domain.model.Order
import com.wayplaner.learn_room.createorder.domain.model.OrderSelfDelivery
import com.wayplaner.learn_room.createorder.domain.model.OrderStateFrom
import com.wayplaner.learn_room.createorder.domain.usecase.ValidatePhone
import com.wayplaner.learn_room.createorder.util.OrderFormState
import com.wayplaner.learn_room.createorder.util.OrderRegisterEvent
import com.wayplaner.learn_room.organization.domain.model.LocationOrganization
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
@RequiresApi(Build.VERSION_CODES.O)
class CreateOrderModelView
@Inject constructor(
    private val orderApiImpl: OrderApiImpl,
    private val validatephone: ValidatePhone = ValidatePhone()
):ViewModel() {

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    private val order_ = mutableStateOf(Order())
    private val order = mutableStateOf(OrderStateFrom(order = order_.value))
    private val orderSelf_ = mutableStateOf(OrderSelfDelivery())
    private var hasErrors = mutableListOf(false)

    var comment = mutableStateOf("")
    var phone = mutableStateOf("")

    private val _uiEvent = Channel<OrderRegisterEvent>()
    val event = _uiEvent.receiveAsFlow()

    private val address_ = MutableLiveData<List<LocationOrganization>>()
    val address: LiveData<List<LocationOrganization>> = address_

    init {
        viewModelScope.launch {
            val result = orderApiImpl.getAddressesByOrg(Basketproduct.idOrg!!, Basketproduct.city!!)
            address_.postValue(result.body())
        }
    }

    private fun sendUiEvent(uiEvent: OrderRegisterEvent){
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }
    fun sumbit(self: Boolean) {
        if(order_.value.addressUser == null && order_.value.addressUser?.lat == null){
            sendUiEvent(OrderRegisterEvent.Failed("Введён некорректный адрес"))
            order.value.errorAddress = "Введён некорректный адрес"
            return
        }
        if(!hasErrors[0]){
            sendUiEvent(OrderRegisterEvent.Failed("Неверный номер телфона"))
            order.value.errorPhone = "Неверный номер телфона"
            return
        }

        if (self)
            sendOrderSelf()
        else
            sendOrder()
    }

    fun onValidateEvent(eventOrder: OrderFormState){
        when(eventOrder){
            is OrderFormState.AddressChanged -> {
                order_.value.addressUser = eventOrder.address
            }
            is OrderFormState.PhoneChanged -> {
                val checked = validatephone.execute(eventOrder.phone)
                hasErrors[0] = checked.success
                order_.value.phoneUser = eventOrder.phone
                orderSelf_.value.phoneUser = eventOrder.phone
            }
            is OrderFormState.PodiezdChanged -> {
                order_.value.podezd = eventOrder.podiezd
            }
            is OrderFormState.AppartementChanged -> {
                order_.value.appartamnet = eventOrder.appartament
            }
            is OrderFormState.HomePhoneChanged -> {
                order_.value.homephome = eventOrder.homePhone
            }
            is OrderFormState.LevelChanged -> {
                order_.value.level = eventOrder.level
            }
            is OrderFormState.Sumbit -> {
                sendUiEvent(OrderRegisterEvent.CheckValidOrder)
                sumbit(eventOrder.isSelf)
            }
            is OrderFormState.ToTimeChaged -> {
                orderSelf_.value.phoneUser = eventOrder.time.format(formatter)
                order_.value.toTimeDelivery = eventOrder.time.format(formatter)
            }

            is OrderFormState.IdAddressChanged -> orderSelf_.value.idLocation = eventOrder.idAddress
        }
    }

    private fun sendOrder(){
        order_.value.summ = Basketproduct.summ
        order_.value.comment = comment.value
        order_.value.fromTimeDelivery = LocalDateTime.now().format(formatter)
        viewModelScope.launch {
            orderApiImpl.sendOrder(order_.value)
            sendUiEvent(OrderRegisterEvent.Success)
        }
    }
    private fun sendOrderSelf(){
        orderSelf_.value.summ = Basketproduct.summ
        orderSelf_.value.comment = comment.value
        orderSelf_.value.fromTimeCooking = LocalDateTime.now().format(formatter)
        viewModelScope.launch {
            orderApiImpl.sendOrderSelf(orderSelf_.value)
            sendUiEvent(OrderRegisterEvent.Success)
        }
    }
}