package com.wayplaner.learn_room.home.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wayplaner.learn_room.home.data.repository.HomeApiRepositoryImpl
import com.wayplaner.learn_room.home.domain.model.CategoryDTO
import com.wayplaner.learn_room.home.domain.model.OrganizationDTO
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

    private val categories_ = MutableLiveData<List<CategoryDTO>?>()
    val categories: LiveData<List<CategoryDTO>?> = categories_
    private val pick_categoties: MutableList<CategoryDTO> = mutableListOf()

    private var cities = MutableLiveData<List<String>>()

    init {
        getCities()
    }
    fun getCountry() = organizationsLiveData
    fun getCity() = cities

    fun addCategoryPick(categoryDTO: CategoryDTO){
        pick_categoties.add(categoryDTO)
        replaceOrganization()
    }

    fun removeCategoryPick(categoryDTO: CategoryDTO){
        pick_categoties.remove(categoryDTO)
        replaceOrganization()
    }

    fun checkPickCategory(): Boolean{
        if(pick_categoties.isEmpty()){
            organizationsLiveData.postValue(all_organizations.value)
            return false
        }
        return true
    }

    private fun replaceOrganization() {
        //organizationsLiveData.value =

        Timber.i(organizationsLiveData.value!!.size.toString())
    }

    fun getOrganizationsByCity(city: String){
        loadOrganizations(city)
    }

    private fun loadOrganizations(city: String){
        viewModelScope.launch {
            val response = repository.getOrganizations(city)
            if (response.isSuccessful) {
                val organizationsList = response.body() ?: emptyList()
                categories_.value = organizationsList.flatMap { it.category }.distinct()
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