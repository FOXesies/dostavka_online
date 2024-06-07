package com.wayplaner.learn_room.orderlist.util

import com.wayplaner.learn_room.createorder.domain.model.StatusOrder

sealed class UiOrderEvent {

    data object OpenActiveOrder: UiOrderEvent()
    data class SwitchOrder(val idOrder: Long, val status: StatusOrder): UiOrderEvent()
    data object OpenActiveCompleteOrder: UiOrderEvent()
    data class CancelOrder(val idOrder: Long): UiOrderEvent()
    data object OpenCanceledOrder: UiOrderEvent()
}