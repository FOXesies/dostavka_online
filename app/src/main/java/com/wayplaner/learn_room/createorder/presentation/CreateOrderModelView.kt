package com.wayplaner.learn_room.createorder.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wayplaner.learn_room.basket.util.Basketproduct
import com.wayplaner.learn_room.createorder.data.repository.OrderApiImpl
import com.wayplaner.learn_room.createorder.domain.model.OrderStateFrom
import com.wayplaner.learn_room.createorder.domain.repository.OrderApi
import com.wayplaner.learn_room.createorder.domain.usecase.ValidatePhone
import com.wayplaner.learn_room.createorder.util.OrderFormState
import com.wayplaner.learn_room.createorder.util.OrderRegisterEvent
import com.wayplaner.learn_room.createorder.util.valueOrNull
import com.wayplaner.learn_room.order.data.model.BasketItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.example.order.model.Order
import org.example.order.model.ProductInOrder
import javax.inject.Inject

@HiltViewModel
class CreateOrderModelView
@Inject constructor(
    private val orderApiImpl: OrderApiImpl,
    private val validatephone: ValidatePhone = ValidatePhone()
):ViewModel() {

    private val order_ = mutableStateOf(Order())
    private val order = mutableStateOf(OrderStateFrom(order = order_.value))
    private var hasErrors = mutableListOf(false)

    var comment = mutableStateOf("")
    var phone = mutableStateOf("")

    private val _uiEvent = Channel<OrderRegisterEvent>()
    val event = _uiEvent.receiveAsFlow()

    fun saveProducts(productInBasket: BasketItem){
        viewModelScope.launch {
            order_.value.summ = productInBasket.summ
            order_.value.productOrder = productInBasket.productsPick.map { ProductInOrder(count = it.count, idProduct = it.product!!.idProduct) }
        }
    }

    private fun sendUiEvent(uiEvent: OrderRegisterEvent){
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }
    fun sumbit(){
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
                sumbit()
            }
        }
    }

    private fun sendOrder(){
        order_.value.summ = Basketproduct.summ
        viewModelScope.launch {
            orderApiImpl.sendOrder(order_.value)
            sendUiEvent(OrderRegisterEvent.Success)
        }
    }
}