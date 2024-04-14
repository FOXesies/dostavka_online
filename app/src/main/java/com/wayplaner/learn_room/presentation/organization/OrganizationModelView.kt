package com.wayplaner.learn_room.presentation.organization

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wayplaner.learn_room.data.repository.OrganizationApiImpl
import com.wayplaner.learn_room.domain.model.Category
import com.wayplaner.learn_room.domain.model.OrganizationIdDTO
import com.wayplaner.learn_room.navigation.AppDestinations.ORGANIZATION_ID_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OrganizationModelView @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val organizationApi: OrganizationApiImpl
): ViewModel() {

    private val organizationId: Long = checkNotNull(savedStateHandle[ORGANIZATION_ID_KEY])

    private var organizations = MutableLiveData<OrganizationIdDTO>()
    fun getOrganization() = organizations

    init {
        loadOrganization()
    }

    private fun loadOrganization(){
        viewModelScope.launch {
            val response = organizationApi.getOrganizationById(organizationId)
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