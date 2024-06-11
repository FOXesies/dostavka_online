package com.wayplaner.learn_room.splash.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wayplaner.learn_room.admin.util.AdminAccount
import com.wayplaner.learn_room.auth.domain.model.DTO.SingInOrgRequest
import com.wayplaner.learn_room.auth.domain.model.DTO.SingInRequest
import com.wayplaner.learn_room.auth.domain.repository.AuthCustomerRepository
import com.wayplaner.learn_room.auth.domain.repository.AuthOrgRepository
import com.wayplaner.learn_room.utils.CustomerAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authCustomerRepository: AuthCustomerRepository,
    private val authOrgRepository: AuthOrgRepository,
): ViewModel() {

    private val mutableStateFlow = MutableStateFlow(true)
    val isLoading = mutableStateFlow.asStateFlow()

    fun sing_in(singInValu: SingInRequest) {
        viewModelScope.launch {
            val response = authCustomerRepository.sing_in(singInValu)
            val responseBody = response.body()!!
            if (responseBody.userResponse != null) {
                CustomerAccount.info = responseBody.userResponse
                mutableStateFlow.value = (true)
            }
        }
    }

    fun sing_in_org(singInValu: SingInOrgRequest) {
        viewModelScope.launch {
            val response = authOrgRepository.sing_in(singInValu)
            val responseBody = response.body()!!
            if (responseBody.idOrg != null) {
                AdminAccount.idOrg = responseBody.idOrg
                mutableStateFlow.value = (true)
            }
        }
    }
}