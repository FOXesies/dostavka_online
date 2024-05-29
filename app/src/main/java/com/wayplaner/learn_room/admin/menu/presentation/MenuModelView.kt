package com.wayplaner.learn_room.admin.menu.presentation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.wayplaner.learn_room.admin.basic_info.util.StatusMenuChange
import com.wayplaner.learn_room.admin.menu.data.model.ResponseProduct
import com.wayplaner.learn_room.admin.menu.data.repository.MenuProductImpl
import com.wayplaner.learn_room.admin.menu.util.UiEventMenuAdd
import com.wayplaner.learn_room.admin.util.AdminAccount
import com.wayplaner.learn_room.product.domain.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MenuModelView @Inject constructor(
    private val repository: MenuProductImpl
): ViewModel() {

    private var UiStatus_ = MutableLiveData<StatusMenuChange>()
    val UiStatus: LiveData<StatusMenuChange> = UiStatus_

    private var listProducts_ = MutableLiveData<Map<String, List<Product>>>()
    val listProducts: LiveData<Map<String, List<Product>>> = listProducts_

    private var categories_ = MutableLiveData<MutableList<String>>()
    val categories: LiveData<MutableList<String>> = categories_

    private var responseProduct_ = MutableLiveData<ResponseProduct?>(null)
    var responseProduct: LiveData<ResponseProduct?> = responseProduct_

    private var imageProduct_ = MutableLiveData<ByteArray?>(null)
    var imageProduct: LiveData<ByteArray?> = imageProduct_

    init {
        viewModelScope.launch {
            categories_.postValue(repository.getCategories().toMutableList())
        }
    }

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
                getProduct()
            }
            is UiEventMenuAdd.ChangeCategoryProduct -> {
                responseProduct_.value!!.category = event.category
            }
            is UiEventMenuAdd.ChangeImageProduct -> {
                responseProduct_.value!!.image = event.imageBt
            }
            is UiEventMenuAdd.Sumbit -> {
                responseProduct_.value!!.product = Product(name = event.name, description = event.description, price = event.price, weight = event.weight)
                submit(event.context)
            }
        }
    }

    private fun getProduct(){
        viewModelScope.launch {
            responseProduct_.postValue(product)
            categories_.postValue(repository.getCategories().toMutableList())
            imageProduct_.postValue(product.product?.imageProduct?.let {
                repository.getImage(it)
            })
        }
    }

    private fun getAllProducts(){
        viewModelScope.launch {
            listProducts_.postValue(repository.getAllInfo(AdminAccount.idOrg))
        }
    }

    private fun addCategory(category: String){
        if(!categories.value!!.contains(category)) {
            val items = categories_.value?: mutableListOf()
            items.add(category)
            categories_.postValue(items)
        }
    }

    private fun submit(applicationContext: Context) {
        viewModelScope.launch {
            val file = File.createTempFile("tempImage", null, applicationContext.cacheDir)
            file.writeBytes(imageProduct_.value?: ByteArray(0))

            val requestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
            val body = MultipartBody.Part.createFormData("image", file.name, requestFile)

            // Сериализация объекта в JSON
            val gson = Gson()
            val json = gson.toJson(responseProduct_.value)
            val jsonRequestBody = RequestBody.create("application/json".toMediaTypeOrNull(), json)

            try {
                val response = repository.updateProducts(body, jsonRequestBody)
                if (response.isSuccessful) {
                    // Обработка успешного запроса
                } else {
                    // Обработка ошибки
                }
            } catch (e: Exception) {
                // Обработка исключения
            }
        }
    }

    /*private fun sumbit(applicationContext: Context) {
        viewModelScope.launch {
            val file = File.createTempFile("tempImage", null, applicationContext.cacheDir)
            file.writeBytes(imageProduct_.value)

            val requestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
            val body = MultipartBody.Part.createFormData("image", file.name, requestFile)

            val call = repository.updateProducts(responseProduct_.value, body)
            call.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    // Обработка успешного запроса
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    // Обработка ошибки
                }
            })
        }
    }*/
}