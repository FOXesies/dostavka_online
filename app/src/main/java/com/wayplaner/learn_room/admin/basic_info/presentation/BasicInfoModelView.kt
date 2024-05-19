package com.wayplaner.learn_room.admin.basic_info.presentation

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wayplaner.learn_room.admin.basic_info.data.repository.BasicInfoImpl
import com.wayplaner.learn_room.admin.basic_info.util.StatusBasicInfo
import com.wayplaner.learn_room.admin.basic_info.util.UiEventBasicInfoA
import com.wayplaner.learn_room.createorder.domain.model.Address
import com.wayplaner.learn_room.organization.model.CityOrganization
import com.wayplaner.learn_room.organization.model.OrganizationIdDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.InputStream
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class BasicInfoModelView @Inject constructor(
    private val repositoryBasicInfo: BasicInfoImpl
): ViewModel() {

    private var UiStatus_ = MutableLiveData<StatusBasicInfo>()
    val UiStatus: LiveData<StatusBasicInfo> = UiStatus_

    private val imageStart = mutableStateOf<InputStream?>(null)
    private var address = mutableStateOf<Address?>(null)
    private var infoOrg_ = mutableStateOf<OrganizationIdDTO?>(null)
    private var imageByteArray = mutableStateOf(ByteArray(0))

    private var cities_ = MutableLiveData<Map<String, MutableList<CityOrganization>>>()
    val cities: LiveData<Map<String, MutableList<CityOrganization>>> = cities_

    init {
        getInfoBasic(1)
    }

    fun onEvent(event: UiEventBasicInfoA){
        when(event){
            is UiEventBasicInfoA.SearchOrg -> getInfoBasic(event.idOrg)
            is UiEventBasicInfoA.AddCities -> {
                addCity(event.cities)
            }
            is UiEventBasicInfoA.UpdateImage -> updateImage(event.idOrg, event.context, event.imageBt)
            is UiEventBasicInfoA.UpdateAddress -> address.value = event.address
            is UiEventBasicInfoA.UpdateOrg -> updateProduct(event.idOrg, event.name, event.phone, address.value)
        }
    }

    private fun addCity(city_: String){
        val city = city_.lowercase().capitalize(Locale.ROOT)
        if(!infoOrg_.value!!.locationsAll.keys.contains(city)){
            val info = infoOrg_.value!!.locationsAll.toMutableMap()
            info[city] = mutableListOf()
            cities_.postValue(info)
        }
    }

    private fun getInfoBasic(idOrg: Long){
        viewModelScope.launch {
            infoOrg_.value = repositoryBasicInfo.getInfo(idOrg)
            imageByteArray.value = repositoryBasicInfo.getImage(infoOrg_.value!!.idImage)
        }.invokeOnCompletion {
            if(it == null) {
                cities_.postValue(infoOrg_.value!!.locationsAll)
                UiStatus_.postValue(StatusBasicInfo.FoundInfo(infoOrg_.value!!, imageByteArray.value))
            }
            else {
                UiStatus_.postValue(StatusBasicInfo.NoFoundInfo)
            }
        }
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

    private fun updateProduct(idOrg: Long, name: String, phone: String, address: Address?) {
        if(address == null){
            //TODO
            return
        }
        viewModelScope.launch {
            //repositoryBasicInfo.updateInfo(idOrg, name, phone, address!!)
        }
    }
}