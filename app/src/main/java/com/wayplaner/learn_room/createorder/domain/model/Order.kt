package org.example.order.model
data class Order(
    var addressUser: String? = null,
    var idDriver: Long? = null,
    var idUser: Long? = null,
    var idOrganization: String? = null,
    var toTimeDelivery: String? = "now",
    var isSelfDelifery: Boolean = false,
    var productOrder: List<ProductInOrder> = mutableListOf(),
    var summ: Double? = null,
    var comment: String? = null
)