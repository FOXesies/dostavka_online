package com.wayplaner.learn_room.admin.menu.presentation

import android.content.Context
import android.widget.Toast
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
import com.wayplaner.learn_room.auth.usecase.ValidateName
import com.wayplaner.learn_room.home.domain.model.Image
import com.wayplaner.learn_room.organization.domain.model.ResponseProductOrg
import com.wayplaner.learn_room.product.domain.model.Product
import com.wayplaner.learn_room.product.domain.model.ProductDToUpdate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MenuModelView @Inject constructor(
    private val repository: MenuProductImpl,
    private val validateName: ValidateName = ValidateName()
): ViewModel() {

    var back = MutableLiveData<Boolean>()

    private var UiStatus_ = MutableLiveData<StatusMenuChange>()

    private var category = ""
    val UiStatus: LiveData<StatusMenuChange> = UiStatus_

    private var listProducts_ = MutableLiveData<Map<String, List<ResponseProductOrg>>>()
    val listProducts: LiveData<Map<String, List<ResponseProductOrg>>> = listProducts_

    private var responseProduct_ = MutableLiveData<Product?>(null)
    var responseProduct: LiveData<Product?> = responseProduct_

    private var categories_ = MutableLiveData<List<String>?>(null)
    var categories: LiveData<List<String>?> = categories_

    val errorMessage = MutableLiveData<String?>(null)

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
            is UiEventMenuAdd.UpdateImage -> {
                updateImage(1, event.context, event.array)
            }
            is UiEventMenuAdd.UpdateProduct -> {
                val images = event.images
                updateProduct(Product(
                    event.id,
                    event.name,
                    event.price,
                    event.weight,
                    event.description,
                    event.images
                ),
                    images,
                    event.context
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

    private fun updateProduct(product: Product, ifEmpty: List<Image>, context: Context){

        val errorPhone = validateName.execute(product.name)
        if(!errorPhone.successful){
            errorMessage.postValue(errorPhone.errormessage)
            return
        }

        val errorPrice = (product.price == null)
        if(errorPrice) {
            errorMessage.postValue("Пустое значение стоимости")
            return
        }

        viewModelScope.launch {
            val lists = mutableListOf<MultipartBody.Part>()
            ifEmpty.forEach {
                val file = File.createTempFile("tempImage", null, context.cacheDir)
                file.writeBytes(it.value!!)

                val requestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
                val body = MultipartBody.Part.createFormData("image", file.name, requestFile)
                lists.add(body)
            }

            product.images?.forEach { it.value = null }
            val prosuctDTO = ProductDToUpdate(
                name = product.name,
                description = product.description,
                price = product.price,
                image = product.images,
                category = category,
                weight = product.weight,
                id = product.idProduct)

            val prosuctDTODataJson = Gson().toJson(prosuctDTO)
            val prosuctDTORequestBody = prosuctDTODataJson.toRequestBody("application/json".toMediaTypeOrNull())
            val call = repository.updateProduct(lists, prosuctDTORequestBody)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        Toast.makeText(context, "Сохранено", Toast.LENGTH_LONG).show()
                        back.postValue(true)
                    } else {
                        // Обработка ошибки
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    // Обработка ошибки
                }
           /* repository.updateProduct(ifEmpty,
                product,
                category)*/
        })
    }
    }

    fun createPartFromProduct(product: ProductDToUpdate): MultipartBody.Part {
        val gson = Gson()
        val json = gson.toJson(product)
        val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("product", json, requestBody)
    }

    private fun updateImage(idOrg: Long, applicationContext: Context, imageByteArray: ByteArray) {
        viewModelScope.launch {
            /*val file = File.createTempFile("tempImage", null, applicationContext.cacheDir)
            file.writeBytes(imageByteArray)

            val requestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
            val body = MultipartBody.Part.createFormData("image", file.name, requestFile)

            val call = repository.uploadImage(idOrg, body)
            call.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    // Обработка успешного запроса
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    // Обработка ошибки
                }
            })*/
        }
    }


    private fun createPartFromByteArray(index: String, byteArray: ByteArray, applicationContext: Context): MultipartBody.Part {
        val file = File.createTempFile("tempImage", null, applicationContext.cacheDir)
        file.writeBytes(byteArray)

        val requestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
        return MultipartBody.Part.createFormData("image", file.name, requestFile)
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