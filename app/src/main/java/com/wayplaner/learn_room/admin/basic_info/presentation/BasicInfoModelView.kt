package com.wayplaner.learn_room.admin.basic_info.presentation

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.wayplaner.learn_room.admin.basic_info.data.repository.BasicInfoImpl
import com.wayplaner.learn_room.admin.basic_info.domain.model.BasicInfoResponse
import com.wayplaner.learn_room.admin.basic_info.domain.model.ImageDTO
import com.wayplaner.learn_room.admin.basic_info.util.StatusBasicInfo
import com.wayplaner.learn_room.admin.basic_info.util.UiEventBasicInfoA
import com.wayplaner.learn_room.admin.util.AdminAccount
import com.wayplaner.learn_room.auth.usecase.ValidateCity
import com.wayplaner.learn_room.auth.usecase.ValidateName
import com.wayplaner.learn_room.auth.usecase.ValidatePhone
import com.wayplaner.learn_room.organization.model.CityOrganization
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
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class BasicInfoModelView @Inject constructor(
    private val repositoryBasicInfo: BasicInfoImpl,
    private val validateCity: ValidateCity = ValidateCity(),
    private val validatePhone: ValidatePhone = ValidatePhone(),
    private val validateName: ValidateName = ValidateName(),
): ViewModel() {

    var back = MutableLiveData<Boolean>(false)

    private var UiStatus_ = MutableLiveData<StatusBasicInfo>()
    val UiStatus: LiveData<StatusBasicInfo> = UiStatus_

    val errorMessage = MutableLiveData<String?>(null)

    private var infoOrg_ = MutableLiveData<BasicInfoResponse?>(null)
    var infoOrg: LiveData<BasicInfoResponse?> = infoOrg_

    private var cities_ = MutableLiveData<Map<String, MutableList<CityOrganization>>>()
    val cities: LiveData<Map<String, MutableList<CityOrganization>>> = cities_

    init {
        getInfoBasic(AdminAccount.idOrg)
    }

    fun onEvent(event: UiEventBasicInfoA){
        when(event){
            is UiEventBasicInfoA.SearchOrg -> getInfoBasic(event.idOrg)
            is UiEventBasicInfoA.AddCities -> addCity(event.cities)
            is UiEventBasicInfoA.AddAddresss -> addAddress(event.city, event.address)
            is UiEventBasicInfoA.RemoveAddresss -> removeCity(event.city, event.address)
            is UiEventBasicInfoA.UpdateOrg -> updateBasicInfo(event.name, event.phone, event.description, event.images, event.context)
        }
    }

    private fun removeCity(city: String, address: CityOrganization?) {
        if(infoOrg_.value!!.locationAll!!.keys.contains(city)){
            val info = infoOrg_.value!!.locationAll!!.toMutableMap()
            info[city]!!.remove(address)
            cities_.postValue(info)
        }
    }

    private fun addCity(city_: String){
        val city = city_.lowercase().capitalize(Locale.ROOT)
        if(infoOrg_.value!!.locationAll?.keys?.contains(city) != true){
            val info = infoOrg_.value!!.locationAll!!.toMutableMap()
            info[city] = mutableListOf()
            cities_.postValue(info)
        }
    }

    private fun addAddress(city: String, address: CityOrganization?) {
        val info = infoOrg_.value!!.locationAll.toMutableMap()
        info[city]!!.add(address!!)
        cities_.postValue(info)
    }

    private fun getInfoBasic(idOrg: Long){
        viewModelScope.launch {
            val response = repositoryBasicInfo.getInfo(idOrg)
            if(response.isSuccessful){
                infoOrg_.postValue(response.body())
            }
            //imageByteArray.value = repositoryBasicInfo.getImage(infoOrg_.value!!.idImage)
        }.invokeOnCompletion {
            if(it != null) {
                cities_.postValue(infoOrg_.value!!.locationAll)
            }
        }
    }

    private fun updateBasicInfo(name: String, phone: String, description: String, images: List<ImageDTO>, context: Context){
        val nameValid = validateName.executeOrg(name)
        val phoneValid = validatePhone.execute(phone)

        if(!nameValid.successful){
            errorMessage.postValue(nameValid.errormessage)
            return
        }

        if(!phoneValid.successful){
            errorMessage.postValue(phoneValid.errormessage)
            return
        }

        viewModelScope.launch {
            val lists = mutableListOf<MultipartBody.Part>()
            images.forEach {
                val file = File.createTempFile("tempImage", null, context.cacheDir)
                file.writeBytes(it.byteArray!!.clone())

                val requestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
                val body = MultipartBody.Part.createFormData("image", file.name, requestFile)
                lists.add(body)
                it.byteArray = null
            }

            val org = BasicInfoResponse(
                idOrg = AdminAccount.idOrg,
                name = name,
                phone = phone,
                idImages = images.toMutableList(),
                description = description,
                locationAll = cities.value ?: emptyMap()
            )
            val prosuctDTODataJson = Gson().toJson(org)
            val prosuctDTORequestBody = prosuctDTODataJson.toRequestBody("application/json".toMediaTypeOrNull())

            val call: Call<ResponseBody> = repositoryBasicInfo.updateInfo(prosuctDTORequestBody, lists)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    Toast.makeText(context, "Сохранено", Toast.LENGTH_LONG).show()
                    back.postValue(true)
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    // Обработка ошибки
                }
            })
        }
    }
}