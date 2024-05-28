package com.wayplaner.learn_room.admin.menu.data.model

import com.wayplaner.learn_room.admin.util.AdminAccount
import com.wayplaner.learn_room.product.domain.model.Product

data class ResponseProduct (
    var product: Product? = null,
    var image: ByteArray? = null,
    var idOrg: Long = AdminAccount.idOrg,
    var category: String = ""
)