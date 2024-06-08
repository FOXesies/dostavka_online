package com.wayplaner.learn_room.organization.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wayplaner.learn_room.organization.data.repository.OrganizationApiImpl
import com.wayplaner.learn_room.organization.model.OrganizationIdDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OrganizationModelView @Inject constructor(
    private val organizationApi: OrganizationApiImpl
): ViewModel() {

    private var organizations = MutableLiveData<OrganizationIdDTO>()
    fun getOrganization() = organizations

    fun loadOrganization(id: Long, city: String){
        viewModelScope.launch {
            val response = organizationApi.getOrganizationById(id, city)
            if(response.isSuccessful){
                organizations.postValue(response.body())
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