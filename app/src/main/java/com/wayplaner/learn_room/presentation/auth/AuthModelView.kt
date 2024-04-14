package com.wayplaner.learn_room.presentation.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.wayplaner.learn_room.domain.model.Customer
import com.wayplaner.learn_room.domain.repository.AuthCustomerRepository
import com.wayplaner.learn_room.utils.FirebaseResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthModelView @Inject constructor(
    private val authCustomerRepository: AuthCustomerRepository
): ViewModel() {

    private val firebaseUserEmitter = MutableLiveData<String>()
    var firebaseUser: LiveData<String> = firebaseUserEmitter

    private var toastMessage = mutableStateOf("")
        private set

    fun signIn(credential: AuthCredential){
        viewModelScope.launch {
            authCustomerRepository.singIn(credential).collect { response ->
                when (response){
                    is FirebaseResponse.Failure -> {
                        toastMessage.value = "Ошибка входа"
                    }
                    is FirebaseResponse.Success -> {
                        firebaseUserEmitter.value = response.result.uid
                        toastMessage.value = "Успешный вход"
                    }
                    is FirebaseResponse.Loading -> {

                    }
                }
            }
        }
    }

    fun logout(){
        authCustomerRepository.logout()
    }
}