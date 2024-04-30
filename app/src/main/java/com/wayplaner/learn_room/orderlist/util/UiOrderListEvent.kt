package com.wayplaner.learn_room.orderlist.util

sealed class UiOrderListEvent {
    data object EmptyActiveOrderList: UiOrderListEvent()
    data object EmptyActiveSelfOrderList: UiOrderListEvent()
    data object NormalActiveOrder: UiOrderListEvent()
    data object NormalActiveSelfOrder: UiOrderListEvent()
    data object EmptyCompleteOrderList: UiOrderListEvent()
    data object NormalCompleteOrder: UiOrderListEvent()
    data object EmptyCanceledOrderList: UiOrderListEvent()
    data object NormalCanceledOrder: UiOrderListEvent()
}