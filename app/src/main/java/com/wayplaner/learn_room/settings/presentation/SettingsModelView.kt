package com.wayplaner.learn_room.settings.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wayplaner.learn_room.admin.util.AdminAccount
import com.wayplaner.learn_room.auth.domain.model.DTO.ResponseAuth
import com.wayplaner.learn_room.auth.domain.model.DTO.UserResponse
import com.wayplaner.learn_room.auth.usecase.ValidateName
import com.wayplaner.learn_room.auth.usecase.ValidatePhone
import com.wayplaner.learn_room.auth.util.deleteUser
import com.wayplaner.learn_room.settings.data.repository.SettingsUserApiIMpl
import com.wayplaner.learn_room.utils.CustomerAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsModelView @Inject constructor(
    private val settingsUserApiIMpl: SettingsUserApiIMpl,
    private val validateName: ValidateName = ValidateName(),
    private val validatePhone: ValidatePhone = ValidatePhone(),
): ViewModel() {

    private val userInfo_ = MutableLiveData<UserResponse?>()
    val userInfo: LiveData<UserResponse?> = userInfo_

    val responseAuth_ = MutableLiveData<ResponseAuth?>()
    fun getInfo(){
        viewModelScope.launch {

            userInfo_.postValue(settingsUserApiIMpl.getInfo(CustomerAccount.info!!.profileUUID) as UserResponse)
        }
    }

    fun logout(){
        viewModelScope.launch {
            CustomerAccount.info!!.deleteUser()
            AdminAccount.idOrg = null
            CustomerAccount.info = null
        }
    }

    fun updateValue(phone: String, name: String) {

        val nameError = validateName.execute(name)
        if(!nameError.successful) {
            responseAuth_.postValue(ResponseAuth(message = nameError.errormessage))
            return
        }

        val phoneError = validatePhone.execute(phone)
        if(!phoneError.successful) {
            responseAuth_.postValue(ResponseAuth(message = phoneError.errormessage))
            return
        }

        viewModelScope.launch {
            responseAuth_.postValue(settingsUserApiIMpl.updateInfo(CustomerAccount.info!!.copy(phone = phone, name = name)))
        }
    }

}