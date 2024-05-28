package com.wayplaner.learn_room.admin.menu.util

import android.content.Context

sealed class UiEventMenuUpdate {
    data class AddCategoryInList(val category: String): UiEventMenuAdd()
    data class SearchProduct(val idProduct: Long): UiEventMenuUpdate()
    data class UpdateProduct(val idProduct: Long, val name: String, val price: String, val description: String): UiEventMenuUpdate()
    data class UpdateImageProduct(val idProduct: Long, val context: Context, val imageBt: ByteArray): UiEventMenuUpdate()
}
sealed class UiEventMenuAdd {
    data class AddCategoryInList(val category: String): UiEventMenuAdd()
    data class ChangeCategoryProduct(val category: String): UiEventMenuAdd()
    data class Sumbit(val context: Context, val name: String, val description: String, val price: Double, val weight: Float): UiEventMenuAdd()
    data class ChangeImageProduct(val imageBt: ByteArray): UiEventMenuAdd()
}