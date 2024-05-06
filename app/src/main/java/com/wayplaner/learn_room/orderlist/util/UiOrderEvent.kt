package com.wayplaner.learn_room.orderlist.util

sealed class UiOrderEvent {
    data object OpenActiveOrder: UiOrderEvent()
    data object OpenActiveOrderSelf: UiOrderEvent()
    data object OpenActiveCompleteOrder: UiOrderEvent()
    data class CancelOrder(val isDeliviry: Boolean, val idOrder: Long): UiOrderEvent()
    data object OpenCanceledOrder: UiOrderEvent()
}