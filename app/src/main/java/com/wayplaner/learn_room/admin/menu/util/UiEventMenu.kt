package com.wayplaner.learn_room.admin.menu.util

import android.content.Context

sealed class UiEventMenuAdd {
    data object ListProducts: UiEventMenuAdd()
    data object PickProduct: UiEventMenuAdd()
    data class AddCategoryInList(val category: String): UiEventMenuAdd()
    data class ChangeCategoryProduct(val category: String): UiEventMenuAdd()
    data class Sumbit(val context: Context, val name: String, val description: String, val price: Double, val weight: Float): UiEventMenuAdd()
    data class ChangeImageProduct(val imageBt: ByteArray): UiEventMenuAdd()
}