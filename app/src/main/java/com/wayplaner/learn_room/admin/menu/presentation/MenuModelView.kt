package com.wayplaner.learn_room.admin.menu.presentation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wayplaner.learn_room.admin.basic_info.util.StatusMenuChange
import com.wayplaner.learn_room.admin.menu.data.model.ResponseProduct
import com.wayplaner.learn_room.admin.menu.data.repository.MenuProductImpl
import com.wayplaner.learn_room.admin.menu.util.UiEventMenuAdd
import com.wayplaner.learn_room.admin.util.AdminAccount
import com.wayplaner.learn_room.organization.domain.model.ResponseProductOrg
import com.wayplaner.learn_room.product.domain.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuModelView @Inject constructor(
    private val repository: MenuProductImpl
): ViewModel() {

    private var UiStatus_ = MutableLiveData<StatusMenuChange>()

    private var category = ""
    val UiStatus: LiveData<StatusMenuChange> = UiStatus_

    private var listProducts_ = MutableLiveData<Map<String, List<ResponseProductOrg>>>()
    val listProducts: LiveData<Map<String, List<ResponseProductOrg>>> = listProducts_

    private var responseProduct_ = MutableLiveData<Product?>(null)
    var responseProduct: LiveData<Product?> = responseProduct_

    private var categories_ = MutableLiveData<List<String>?>(null)
    var categories: LiveData<List<String>?> = categories_

    companion object {
        private var product = ResponseProduct()
        fun setPickProduct(product: ResponseProduct){
            this.product = product
        }
    }

    fun onEvent(event: UiEventMenuAdd){
        when (event) {
            is UiEventMenuAdd.AddCategoryInList -> {
                addCategory(event.category)
            }
            is UiEventMenuAdd.ListProducts -> {
                getAllProducts()
            }
            is UiEventMenuAdd.PickProduct -> {
                getProduct(event.idProduct)
            }
            is UiEventMenuAdd.ChangeCategoryProduct -> {
                category = event.category
            }
            is UiEventMenuAdd.UpdateProduct -> {
                updateProduct(Product(
                    event.id,
                    event.name,
                    event.price,
                    event.weight,
                    event.description,
                    event.images.ifEmpty { null }
                )
                )
            }
            is UiEventMenuAdd.GetCategories -> {
                getCategories()
            }
        }
    }

    private fun getCategories() {
        viewModelScope.launch {
            categories_.postValue(repository.getCategories(AdminAccount.idOrg).body())
        }
    }

    private fun getProduct(idProduct: Long){
        viewModelScope.launch {
            responseProduct_.postValue(repository.getProductinfo(idProduct).body())
        }
    }

    private fun updateProduct(product: Product){
        viewModelScope.launch {
            repository.updateProduct(product)
        }
    }

    private fun getAllProducts(){
        viewModelScope.launch {
            listProducts_.postValue(repository.getAllInfo(AdminAccount.idOrg).body())
        }
    }

    private fun addCategory(category: String){
        /*if(!categories.value!!.contains(category)) {
            val items = categories_.value?: mutableListOf()
            items.add(category)
            categories_.postValue(items)
        }*/
    }

    private fun submit(applicationContext: Context) {
        viewModelScope.launch {

        }
    }
}