package com.wayplaner.learn_room.admin.menu.presentation

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wayplaner.learn_room.admin.basic_info.util.StatusMenuChange
import com.wayplaner.learn_room.admin.basic_info.util.UiEventMenuAdd
import com.wayplaner.learn_room.admin.menu.data.repository.MenuProductImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MenuModelView @Inject constructor(
    private val repositoryBasicInfo: MenuProductImpl
): ViewModel() {

    private var UiStatus_ = MutableLiveData<StatusMenuChange>()
    val UiStatus: LiveData<StatusMenuChange> = UiStatus_

    private var imageByteArray = mutableStateOf(ByteArray(0))

    fun onEventAdd(event: UiEventMenuAdd){
        /*when(event){
            is UiEventMenuAdd.AddProductInfo -> {
                addProducInfo()
            }
        }*/
    }

    private fun addProducInfo(){
        var idProduct = null
        viewModelScope.launch {

        }.invokeOnCompletion {
            if(idProduct != null){

            }
        }
    }

    private fun addImageInfo(){

    }

    private fun updateImage(idOrg: Long, applicationContext: Context, imageByteArray: ByteArray) {
        viewModelScope.launch {
            val file = File.createTempFile("tempImage", null, applicationContext.cacheDir)
            file.writeBytes(imageByteArray)

            val requestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
            val body = MultipartBody.Part.createFormData("image", file.name, requestFile)

            val call = repositoryBasicInfo.uploadImage(idOrg, body)
            call.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    // Обработка успешного запроса
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    // Обработка ошибки
                }
            })
        }
    }

    private fun updateName(idOrg: Long, name: String) {
        viewModelScope.launch {
            repositoryBasicInfo.updateInfo(idOrg, name)
        }
    }
}