package com.wayplaner.learn_room.orderlist.util

sealed class UiOrderEvent {
    data object OpenActiveOrder: UiOrderEvent()
    data object OpenActiveOrderSelf: UiOrderEvent()
    data object OpenActiveCompleteOrder: UiOrderEvent()
}