package com.wayplaner.learn_room.home.presentation

import android.graphics.Bitmap
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

    init {
        loadOrganizations()
    }

    private val categories_ = MutableLiveData<List<CategoryDTO>?>()
    val categories: LiveData<List<CategoryDTO>?> = categories_
    private val pick_categoties: MutableList<CategoryDTO> = mutableListOf()

    private val image = MutableLiveData<Bitmap?>()

    private val ima1ge = MutableLiveData<Bitmap?>()

    fun getCountry() = organizationsLiveData

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

    private fun loadOrganizations(){
        viewModelScope.launch {

            val response = repository.getOrganizations()

            if (response.isSuccessful) {
                var organizationsList = listOf<OrganizationDTO>()

                val organizations = response.body()
                Timber.i(organizations.toString())

                organizations?.forEach {
                    //it.images.add(imageRepository.getImage(it.idImage!!).body())
                    organizationsList = organizationsList + it
                }
                categories_.value = (organizations?.flatMap { it.category }
                    ?.distinct())

                all_organizations.postValue(organizationsList)
                organizationsLiveData.postValue(organizationsList)
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