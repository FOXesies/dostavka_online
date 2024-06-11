package com.wayplaner.learn_room.auth.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wayplaner.learn_room.admin.auth_org.util.OrgFormState
import com.wayplaner.learn_room.admin.util.AdminAccount
import com.wayplaner.learn_room.auth.domain.model.DTO.SingInOrgRequest
import com.wayplaner.learn_room.auth.domain.model.DTO.SingUpOrgRequest
import com.wayplaner.learn_room.auth.domain.repository.AuthOrgRepository
import com.wayplaner.learn_room.auth.usecase.ValidateCity
import com.wayplaner.learn_room.auth.usecase.ValidateLogin
import com.wayplaner.learn_room.auth.usecase.ValidateName
import com.wayplaner.learn_room.auth.usecase.ValidatePhone
import com.wayplaner.learn_room.auth.util.save
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.comet.android.auth.domain.usecase.ValidatePassword
import ru.comet.android.auth.domain.usecase.ValidatePasswordRepeat
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthOrgModelView @Inject constructor(
    private val validateName: ValidateName = ValidateName(),
    private val validatePhone: ValidatePhone = ValidatePhone(),
    private val validateCity: ValidateCity = ValidateCity(),
    private val validatePasswordRepeat: ValidatePasswordRepeat = ValidatePasswordRepeat(),
    private val validateLogin: ValidateLogin = ValidateLogin(),
    private val validatePassword: ValidatePassword = ValidatePassword(),
    private val authCustomerRepository: AuthOrgRepository
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

    private val userFormState_ = MutableLiveData(OrgFormState())
    val userFormState: LiveData<OrgFormState> = userFormState_

    private val success_ = MutableLiveData(false)
    val success: LiveData<Boolean> = success_

    private fun sing_in(){
        viewModelScope.launch {
            val valueSend = SingInOrgRequest(userFormState_.value!!.login, userFormState_.value!!.password)
            val response = authCustomerRepository.sing_in(valueSend)
            val responseBody = response.body()!!
            if(responseBody.idOrg != null) {
                AdminAccount.idOrg = responseBody.idOrg
                valueSend.save()
                success_.postValue(true)
                return@launch
            }

            userFormState_.postValue(userFormState_.value!!.copy(baseError = responseBody.message))
        }
    }
    private fun sing_up(){
        viewModelScope.launch {
            val valueSend = SingUpOrgRequest(
                userFormState_.value!!.city,
                userFormState_.value!!.address!!,
                userFormState_.value!!.login,
                userFormState_.value!!.name,
                userFormState_.value!!.phone,
                userFormState_.value!!.password)

            val response = authCustomerRepository.sing_up(valueSend)
            val responseBody = response.body()!!
            if(responseBody.idOrg != null) {
                AdminAccount.idOrg = responseBody.idOrg
                valueSend.save()
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
            is EventFormUserState.SumbitSingUp -> { sumbitSingUp() }
            is EventFormUserState.ChangedAddress -> {
                userFormState_.value!!.address = event.address
            }
            is EventFormUserState.ChangedLogin -> {
                userFormState_.value!!.login = event.login
            }
        }
    }

    private fun sumbitSingUp(){
        clearErrorValue()

        val name = validateName.execute(userFormState_.value!!.name)
        val phone = validatePhone.execute(userFormState_.value!!.phone)
        val login = validateLogin.execute(userFormState_.value!!.login)
        val city = validateCity.execute(userFormState_.value!!.city)
        val password = validatePassword.execute(userFormState_.value!!.password)
        val passwordRepeat = validatePasswordRepeat.execute(userFormState_.value!!.password, userFormState_.value!!.passwordRepeat)

        val hasError = listOf(
            name,
            phone,
            city,
            login,
            password,
            passwordRepeat
        ).any { !it.successful }

        if(userFormState_.value!!.address?.address == null){
            userFormState_.value = userFormState_.value?.copy(
                errorAddress = "Выберите адрес")

            return
        }

        if(hasError){
            userFormState_.value = userFormState_.value?.copy(
                errorName = name.errormessage,
                errorCity = city.errormessage,
                errorPhone = phone.errormessage,
                errorLogin = login.errormessage,
                passwordError = password.errormessage,
                passwordRepeatError = passwordRepeat.errormessage,
            )

            return
        }

        sing_up()
    }


    private fun sumbitSingIn(){
        clearErrorValue()

        val login = validateLogin.execute(userFormState_.value!!.login)
        if(!login.successful){
            userFormState_.value = userFormState_.value?.copy(
                errorLogin = login.errormessage,
            )
            return
        }

        sing_in()
    }

    private fun clearErrorValue(){
        userFormState_.value = userFormState_.value?.copy(
            errorLogin = null,
            errorAddress = null,
            errorName = null,
            errorCity = null,
            errorPhone = null,
            passwordRepeatError = null,
            passwordError = null,
        )
    }

}
