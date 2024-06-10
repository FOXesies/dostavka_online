package com.wayplaner.learn_room.favotitea.presentations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wayplaner.learn_room.favotitea.data.reposirory.FavoriteApiImpl
import com.wayplaner.learn_room.home.domain.model.OrganizationDTO
import com.wayplaner.learn_room.organization.domain.model.ResponseProductOrg
import com.wayplaner.learn_room.utils.CustomerAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteModelView @Inject constructor(
    private val favoriteApiImpl: FavoriteApiImpl
): ViewModel(){

    private val organizationsLiveData = MutableLiveData<List<OrganizationDTO>?>()
    val all_organizations: LiveData<List<OrganizationDTO>?> = organizationsLiveData

    private val productsLiveData = MutableLiveData<List<ResponseProductOrg>?>()
    val all_products: LiveData<List<ResponseProductOrg>?> = productsLiveData

    init {
        loadProduct()
        loadOrgs()
    }

    private fun loadProduct(){
        viewModelScope.launch {
            productsLiveData.postValue(favoriteApiImpl.getProducts(CustomerAccount.info!!.profileUUID).body())
        }
    }
    private fun loadOrgs(){
        viewModelScope.launch {
            organizationsLiveData.postValue(favoriteApiImpl.getOrgs(CustomerAccount.info!!.profileUUID).body())
        }
    }

}