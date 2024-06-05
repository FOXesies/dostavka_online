package com.wayplaner.learn_room.auth.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wayplaner.learn_room.auth.domain.model.DTO.SingInRequest
import com.wayplaner.learn_room.auth.domain.model.DTO.SingUpRequest
import com.wayplaner.learn_room.auth.domain.repository.AuthCustomerRepository
import com.wayplaner.learn_room.auth.usecase.ValidateCity
import com.wayplaner.learn_room.auth.usecase.ValidateName
import com.wayplaner.learn_room.auth.usecase.ValidatePhone
import com.wayplaner.learn_room.auth.util.UserFormState
import com.wayplaner.learn_room.utils.CustometAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.comet.android.auth.domain.usecase.ValidatePassword
import ru.comet.android.auth.domain.usecase.ValidatePasswordRepeat
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthModelView @Inject constructor(
    private val validateName: ValidateName = ValidateName(),
    private val validatePhone: ValidatePhone = ValidatePhone(),
    private val validateCity: ValidateCity = ValidateCity(),
    private val validatePasswordRepeat: ValidatePasswordRepeat = ValidatePasswordRepeat(),
    private val validatePassword: ValidatePassword = ValidatePassword(),
    private val authCustomerRepository: AuthCustomerRepository
): ViewModel() {



    private var cities = MutableLiveData<List<String>>()

    init {
        getCities()
    }
    fun getCity() = cities

    private fun getCities(){
        viewModelScope.launch{
            val response = authCustomerRepository.getCities()
            if (response.isSuccessful) {
                val cities_ = response.body()
                Timber.i(cities_.toString())
                cities.postValue(cities_!!)
            } else {
                // Обработка ошибки
                val errorBody = response.errorBody()
                if (errorBody != null) {
                    Timber.e(errorBody.string())
                }
            }
        }
    }

    private val userFormState_ = MutableLiveData(UserFormState())
    val userFormState: LiveData<UserFormState> = userFormState_

    private val success_ = MutableLiveData(false)
    val success: LiveData<Boolean> = success_

    private fun sing_in(){
        viewModelScope.launch {
            val response = authCustomerRepository.sing_in(SingInRequest(userFormState_.value!!.phone, userFormState_.value!!.password))
            val responseBody = response.body()!!
            if(responseBody.userResponse != null) {
                CustometAccount.info = responseBody.userResponse
                success_.postValue(true)
                return@launch
            }

            userFormState_.postValue(userFormState_.value!!.copy(baseError = responseBody.message))
        }
    }
    private fun sing_up(){
        viewModelScope.launch {
            val response = authCustomerRepository.sing_up(SingUpRequest(
                userFormState_.value!!.phone,
                userFormState_.value!!.name,
                userFormState_.value!!.city,
                userFormState_.value!!.password))
            val responseBody = response.body()!!
            if(responseBody.userResponse != null) {
                CustometAccount.info = responseBody.userResponse
                success_.postValue(true)
                return@launch
            }

            userFormState_.postValue(userFormState_.value!!.copy(baseError = responseBody.message))
        }
    }

    fun onEvent(event: EventFormUserState){
        when(event){
            is EventFormUserState.ChangedCity -> userFormState_.postValue(userFormState_.value!!.copy(city = event.city))
            is EventFormUserState.ChangedName -> userFormState_.postValue(userFormState_.value!!.copy(name = event.name))
            is EventFormUserState.ChangedPassword -> userFormState_.postValue(userFormState_.value!!.copy(password = event.password))
            is EventFormUserState.ChangedPasswordRepeat -> userFormState_.postValue(userFormState_.value!!.copy(passwordRepeat = event.passwordRepeat))
            is EventFormUserState.ChangedPhone -> userFormState_.postValue(userFormState_.value!!.copy(phone = event.phone))
            is EventFormUserState.SumbitSingIn -> { sumbitSingIn() }
            is EventFormUserState.SumbitSingUp -> {}
        }
    }

    private fun sumbitSingIn(){
        clearErrorValue()

        val name = validateName.execute(userFormState_.value!!.name)
        val phone = validatePhone.execute(userFormState_.value!!.phone)
        val city = validateCity.execute(userFormState_.value!!.city)
        val password = validatePassword.execute(userFormState_.value!!.password)
        val passwordRepeat = validatePasswordRepeat.execute(userFormState_.value!!.password, userFormState_.value!!.passwordRepeat)

        val hasError = listOf(
            name,
            phone,
            city,
            password,
            passwordRepeat
        ).any { !it.successful }

        if(hasError){
            userFormState_.value = userFormState_.value?.copy(
                errorName = name.errormessage,
                errorCity = city.errormessage,
                errorPhone = phone.errormessage,
                passwordError = password.errormessage,
                passwordRepeatError = passwordRepeat.errormessage,
            )

            return
        }

        sing_in()
    }

    private fun clearErrorValue(){
        userFormState_.value = userFormState_.value?.copy(
            errorName = null,
            errorCity = null,
            errorPhone = null,
            passwordRepeatError = null,
            passwordError = null,
        )
    }

}

sealed class EventFormUserState{
    data class ChangedCity(val city: String): EventFormUserState()
    data class ChangedName(val name: String): EventFormUserState()
    data class ChangedPassword(val password: String): EventFormUserState()
    data class ChangedPasswordRepeat(val passwordRepeat: String): EventFormUserState()
    data class ChangedPhone(val phone: String): EventFormUserState()
    data object SumbitSingIn: EventFormUserState()
    data object SumbitSingUp: EventFormUserState()
}
