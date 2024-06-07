package com.wayplaner.learn_room.admin.infoorder.presintation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wayplaner.learn_room.admin.infoorder.data.repository.InfoOrderApiImpl
import com.wayplaner.learn_room.admin.infoorder.domain.model.SendBasicInfoOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class InfoOrderAdminModelView @Inject constructor(
    private val repositoryImpl: InfoOrderApiImpl
): ViewModel(){

    private val info_ = MutableLiveData<SendBasicInfoOrder>()
    var info: LiveData<SendBasicInfoOrder> = info_

    init {
        loadInfo()
    }

    private fun loadInfo(){
        viewModelScope.launch {
            val response = repositoryImpl.getInfoOrder(1)
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

}