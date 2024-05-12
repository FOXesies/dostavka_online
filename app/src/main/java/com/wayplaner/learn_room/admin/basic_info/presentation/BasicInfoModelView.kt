package com.wayplaner.learn_room.admin.basic_info.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wayplaner.learn_room.admin.basic_info.data.repository.BasicInfoImpl
import com.wayplaner.learn_room.admin.basic_info.domain.model.OrganizationResponse
import com.wayplaner.learn_room.admin.basic_info.util.StatusBasicInfo
import com.wayplaner.learn_room.admin.basic_info.util.UiEventBasicInfoA
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class BasicInfoModelView @Inject constructor(
    private val repositoryBasicInfo: BasicInfoImpl
): ViewModel() {

    private var UiStatus_ = MutableLiveData<StatusBasicInfo>()
    val UiStatus: LiveData<StatusBasicInfo> = UiStatus_


    private val imageStart = mutableStateOf<InputStream?>(null)
    private var infoOrg_ = mutableStateOf<OrganizationResponse?>(null)

    fun onEvent(event: UiEventBasicInfoA){
        when(event){
            is UiEventBasicInfoA.SearchOrg -> getInfoBasic(event.idOrg)
            is UiEventBasicInfoA.UpdateImage -> TODO()
            is UiEventBasicInfoA.UpdateOrg -> TODO()
        }
    }

    private fun getInfoBasic(idOrg: Long){
        viewModelScope.launch {
            infoOrg_.value = repositoryBasicInfo.getInfo(idOrg)
            //imageStart.value = repositoryBasicInfo.getImage(idOrg)
        }.invokeOnCompletion {
            if(it == null)
                UiStatus_.postValue(StatusBasicInfo.FoundInfo(infoOrg_.value!!, imageStart.value!!))
            else {
                UiStatus_.postValue(StatusBasicInfo.NoFoundInfo)
            }
        }
    }
}