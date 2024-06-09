package com.wayplaner.learn_room.order_info.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wayplaner.learn_room.order_info.data.repository.InfoOrderIMpl
import com.wayplaner.learn_room.order_info.domain.data.BasicInfoOrderUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class InfoOrderUserModelView @Inject constructor(
    private val repositoryImpl: InfoOrderIMpl
): ViewModel(){

    private val info_ = MutableLiveData<BasicInfoOrderUser>()
    var info: LiveData<BasicInfoOrderUser> = info_

    private fun loadInfo(idOrder: Long){
        viewModelScope.launch {
            val response = repositoryImpl.getInfoOrder(idOrder)
            if(response.isSuccessful){
                info_.postValue(response.body())
            }
            else {
                // Обработка ошибки
                val errorBody = response.errorBody()
                if (errorBody != null) {
                    Timber.e(errorBody.string())
                }
            }
        }
    }

    fun getOrder(idOrder: Long) {
        viewModelScope.launch {
            while(true) {
                loadInfo(idOrder)
                delay(2500)
            }
        }
    }

}