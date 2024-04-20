package com.wayplaner.learn_room.createorder.util

import android.graphics.Point

sealed class OrderFormState {
    data class AddressChanged(val address: Point?): OrderFormState()
    data class PhoneChanged(val phone: String): OrderFormState()

    class Sumbit(val orderRegistEvent: OrderRegisterEvent): OrderFormState()
}