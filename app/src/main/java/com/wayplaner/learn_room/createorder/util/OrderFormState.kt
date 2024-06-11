package com.wayplaner.learn_room.createorder.util

import com.wayplaner.learn_room.organization.model.CityOrganization
import org.example.order.model.StatusPayment
import java.time.LocalDateTime

sealed class OrderFormState {
    data class AddressChanged(val address: CityOrganization?): OrderFormState()
    data class PhoneChanged(val phone: String): OrderFormState()
    data class PaymentChange(val payment: StatusPayment): OrderFormState()
    data class HomePhoneChanged(val homePhone: String): OrderFormState()
    data class LevelChanged(val level: String): OrderFormState()
    data class AppartementChanged(val appartament: String): OrderFormState()
    data class PodiezdChanged(val podiezd: String): OrderFormState()
    data class ToTimeChaged(val time: LocalDateTime): OrderFormState()
    data class IdAddressChanged(val idAddress: Long): OrderFormState()
    data class Sumbit(val isSelf: Boolean): OrderFormState()
}