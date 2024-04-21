package com.wayplaner.learn_room.createorder.util

import android.graphics.Point
import com.wayplaner.learn_room.createorder.domain.model.Address

sealed class OrderFormState {
    data class AddressChanged(val address: Address?): OrderFormState()
    data class PhoneChanged(val phone: String): OrderFormState()

    data class HomePhoneChanged(val homePhone: String): OrderFormState()

    data class LevelChanged(val level: String): OrderFormState()

    data class AppartementChanged(val appartament: String): OrderFormState()

    data class PodiezdChanged(val podiezd: String): OrderFormState()

    data object Sumbit: OrderFormState()
}