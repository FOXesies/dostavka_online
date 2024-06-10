package com.wayplaner.learn_room.organization.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wayplaner.learn_room.organization.data.repository.OrganizationApiImpl
import com.wayplaner.learn_room.organization.model.OrganizationIdDTO
import com.wayplaner.learn_room.utils.CustomerAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.example.favorite.entity.DTO.ResponseFavProduct
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OrganizationModelView @Inject constructor(
    private val organizationApi: OrganizationApiImpl
): ViewModel() {

    private var organizations = MutableLiveData<OrganizationIdDTO>()
    fun getOrganization() = organizations

    private var isLike = MutableLiveData(false)
    fun isLike() = isLike

    fun loadOrganization(id: Long, city: String){
        viewModelScope.launch {
            val response = organizationApi.getOrganizationById(id, city)
            val like = organizationApi.getLikeOrg(ResponseFavProduct(idUser = CustomerAccount.info!!.profileUUID, idOrg = id))
            if(response.isSuccessful){
                organizations.postValue(response.body())
                isLike.postValue(like.body())
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

    fun likeOrg(idOrg: Long){
        viewModelScope.launch {
            isLike.postValue(!isLike.value!!)
            organizationApi.likeOrg(ResponseFavProduct(idUser = CustomerAccount.info!!.profileUUID, idOrg = idOrg))
        }
    }
}