package com.wayplaner.learn_room.basket.domain.model

import com.wayplaner.learn_room.utils.CustomerAccount

data class SendBasketProduct(
    var city: String? = null,
    var organziationId: Long? = null,
    var productId: Long? = null,
    val UserId: Long = CustomerAccount.info!!.profileUUID
)