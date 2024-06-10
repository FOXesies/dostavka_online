package org.example.order.model

enum class StatusPayment {
    CARD,
    CASH;

    fun getName(status: StatusPayment): String{
        return when(status){
            CARD -> "Картой"
            CASH -> "Наличными"
        }
    }
}