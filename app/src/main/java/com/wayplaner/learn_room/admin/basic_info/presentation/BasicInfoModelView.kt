package com.wayplaner.learn_room.admin.basic_info.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class BasicInfoModelView @Inject constructor(
    private val repositoryBasicInfo: BasicInfoImpl,
    private val validateCity: ValidateCity = ValidateCity(),
    private val validatePhone: ValidatePhone = ValidatePhone(),
    private val validateName: ValidateName = ValidateName(),
): ViewModel() {

    private var isChanged = false

    private var UiStatus_ = MutableLiveData<StatusBasicInfo>()
    val UiStatus: LiveData<StatusBasicInfo> = UiStatus_

    val errorMessage = MutableLiveData<String?>(null)

    private var infoOrg_ = mutableStateOf<BasicInfoResponse?>(null)

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
            is UiEventBasicInfoA.UpdateOrg -> updateBasicInfo(event.name, event.phone, event.description, event.images)
        }
    }

    private fun removeCity(city: String, address: CityOrganization?) {
        if(infoOrg_.value!!.locationAll!!.keys.contains(city)){
            val info = infoOrg_.value!!.locationAll!!.toMutableMap()
            info[city]!!.remove(address)
            cities_.postValue(info)
            isChanged = true
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
        isChanged = true
    }

    private fun getInfoBasic(idOrg: Long){
        viewModelScope.launch {
            val response = repositoryBasicInfo.getInfo(idOrg)
            if(response.isSuccessful){
                infoOrg_.value = response.body()
            }
            //imageByteArray.value = repositoryBasicInfo.getImage(infoOrg_.value!!.idImage)
        }.invokeOnCompletion {
            if(it == null) {
                cities_.postValue(infoOrg_.value!!.locationAll)
                UiStatus_.postValue(StatusBasicInfo.FoundInfo(infoOrg_.value!!))
            }
            else {
                UiStatus_.postValue(StatusBasicInfo.NoFoundInfo)
            }
        }
    }

    private fun updateBasicInfo(name: String, phone: String, description: String, images: List<ImageDTO>){
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
            errorMessage.postValue(repositoryBasicInfo.updateInfo(
                BasicInfoResponse(
                    idOrg = AdminAccount.idOrg,
                    name = name,
                    phone = phone,
                    description = description,
                    locationAll = cities.value ?: emptyMap()
                )
            ).error_message)
        }

        viewModelScope.launch {
            if(images != infoOrg_.value?.idImages){
                repositoryBasicInfo.updateImages(images)
            }
        }
    }
}