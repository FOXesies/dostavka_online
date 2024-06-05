package com.wayplaner.learn_room.home.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wayplaner.learn_room.home.data.repository.HomeApiRepositoryImpl
import com.wayplaner.learn_room.home.domain.model.OrganizationDTO
import com.wayplaner.learn_room.organization.domain.model.FiltercategoryOrg
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainModelView @Inject constructor(
    private val repository: HomeApiRepositoryImpl
): ViewModel() {

    private val organizationsLiveData = MutableLiveData<List<OrganizationDTO>?>()
    private val all_organizations = MutableLiveData<List<OrganizationDTO>?>()

    private val categories_ = MutableLiveData<List<String>?>()
    val categories: LiveData<List<String>?> = categories_

    private val filter_id_ = MutableLiveData<List<Long>>(mutableListOf())
    val filter_id: LiveData<List<Long>> = filter_id_

    private var cities = MutableLiveData<List<String>>()

    init {
        getCities()
    }
    fun getCity() = cities
    fun getCountry() = organizationsLiveData

    fun getOrganizationsByCity(city: String){
        loadOrganizations(city)
    }

    fun getOrganizationsByCategory(filterCategory: FiltercategoryOrg){
        loadOrganizations(filterCategory)
    }

    fun clearFilterIds(){
        filter_id_.postValue(emptyList())
    }

    private fun loadOrganizations(city: String){
        viewModelScope.launch {
            val response = repository.getOrganizations(city)
            if (response.isSuccessful) {
                getCategoriesInCity(city)
                val organizationsList = response.body() ?: emptyList()
                all_organizations.postValue(organizationsList)
                organizationsLiveData.postValue(organizationsList)
            } else {
                val errorBody = response.errorBody()
                if (errorBody != null) {
                    Timber.e(errorBody.string())
                }
            }
        }
    }

    private fun getCategoriesInCity(city: String){
        viewModelScope.launch {
            val response = repository.getCategoriesCity(city)
            if (response.isSuccessful) {
                val categories = response.body() ?: emptyList()
                categories_.postValue(categories)
            } else {
                val errorBody = response.errorBody()
                if (errorBody != null) {
                    Timber.e(errorBody.string())
                }
            }
        }
    }

    private fun loadOrganizations(filterCategory: FiltercategoryOrg){
        viewModelScope.launch {
            val response = repository.getOrganizations(filterCategory)
            if (response.isSuccessful) {
                val filter_ids = response.body() ?: emptyList()
                filter_id_.postValue(filter_ids)
            } else {
                val errorBody = response.errorBody()
                if (errorBody != null) {
                    Timber.e(errorBody.string())
                }
            }
        }
    }

    private fun getCities(){
        viewModelScope.launch{
            val response = repository.getCities()
            if (response.isSuccessful) {
                val cities_ = response.body()
                Timber.i(cities_.toString())

                cities.postValue(cities_!!)
                loadOrganizations(cities_[0])
            } else {
                // Обработка ошибки
                val errorBody = response.errorBody()
                if (errorBody != null) {
                    Timber.e(errorBody.string())
                }
            }
        }
    }

    /*private fun loadImage(){
        viewModelScope.launch {
            val response = repository.getPhotoByOrganization()

            if(response.isSuccessful) {
                val inputStream = response.body()?.byteStream()
                if(inputStream != null){
                    image.postValue(convertInputStreamToBitmap(inputStream))
                }
            }
        }
    }*/
}