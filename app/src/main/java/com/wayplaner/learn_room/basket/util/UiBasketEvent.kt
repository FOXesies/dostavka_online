package com.wayplaner.learn_room.basket.util

sealed class UiBasketEvent {
    data object EmptyBasket: UiBasketEvent()
    data class ErrorAction(val error: String): UiBasketEvent()
    data object NormalBasket: UiBasketEvent()
}