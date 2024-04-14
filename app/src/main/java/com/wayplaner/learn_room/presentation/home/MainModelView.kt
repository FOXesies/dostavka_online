package com.wayplaner.learn_room.presentation.home

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wayplaner.learn_room.data.repository.HomeApiRepositoryImpl
import com.wayplaner.learn_room.data.repository.ImageApiImpl
import com.wayplaner.learn_room.domain.model.CategoryDTO
import com.wayplaner.learn_room.domain.model.OrganizationDTO
import com.wayplaner.learn_room.utils.ImageUtils.Companion.convertInputStreamToBitmap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.InputStream
import java.util.Timer
import javax.inject.Inject

@HiltViewModel
class MainModelView @Inject constructor(
    private val repository: HomeApiRepositoryImpl,
    private val imageRepository: ImageApiImpl
): ViewModel() {

    private val organizationsLiveData = MutableLiveData<List<OrganizationDTO>?>()
    private val all_organizations = MutableLiveData<List<OrganizationDTO>?>()

    init {
        loadOrganizations()
    }

    private val categories = MutableLiveData<List<CategoryDTO>?>()
    private val pick_categoties: MutableList<CategoryDTO> = mutableListOf()

    private val image = MutableLiveData<Bitmap?>()

    fun getCountry() = organizationsLiveData
    fun getCategories() = categories

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
                    it.images.add(imageRepository.getImage(it.idImage).body())
                    organizationsList = organizationsList + it
                }
                categories.postValue(organizations?.flatMap { it.category }
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