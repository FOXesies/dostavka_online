package com.wayplaner.learn_room.admin.menu.util

import android.content.Context
import com.wayplaner.learn_room.home.domain.model.Image

sealed class UiEventMenuAdd {
    data object ListProducts: UiEventMenuAdd()
    data object GetCategories: UiEventMenuAdd()
    data class UpdateImage(val context: Context, val array: ByteArray): UiEventMenuAdd()
    data class UpdateProduct(val id: Long?, val name: String, val description: String, val price: Double, val weight: Float, val images: List<Image>, val context: Context): UiEventMenuAdd()
    data class PickProduct(val idProduct: Long): UiEventMenuAdd()
    data class AddCategoryInList(val category: String): UiEventMenuAdd()
    data class ChangeCategoryProduct(val category: String): UiEventMenuAdd()
}