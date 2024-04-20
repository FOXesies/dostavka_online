package com.wayplaner.learn_room.createorder.util

sealed class OrderRegisterEvent {
    data object NoChange: OrderRegisterEvent()
    data object NavigateToContinue: OrderRegisterEvent()
    data object NavigateToAddress: OrderRegisterEvent()
    data object CheckValidOrder: OrderRegisterEvent()
    data class Failed(val error: String): OrderRegisterEvent()
    data object Success: OrderRegisterEvent()
}
