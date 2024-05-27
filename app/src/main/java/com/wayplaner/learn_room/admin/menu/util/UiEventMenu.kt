package com.wayplaner.learn_room.admin.basic_info.util

import android.content.Context

sealed class UiEventMenuUpdate {
    data class AddCategoryInList(val category: String): UiEventMenuAdd()
    data class SearchProduct(val idProduct: Long): UiEventMenuUpdate()
    data class UpdateProduct(val idProduct: Long, val name: String, val price: String, val description: String): UiEventMenuUpdate()
    data class UpdateImageProduct(val idProduct: Long, val context: Context, val imageBt: ByteArray): UiEventMenuUpdate()
}
sealed class UiEventMenuAdd {
    data class AddCategoryInList(val category: String): UiEventMenuAdd()
    data class AddProductInfo(val name: String, val price: String, val description: String, val weight: Float?,): UiEventMenuAdd()
    data class AddImageProduct(val idProduct: Long, val context: Context, val imageBt: ByteArray): UiEventMenuAdd()
}