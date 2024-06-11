package com.wayplaner.learn_room.auth.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.wayplaner.learn_room.admin.util.AdminAccount
import com.wayplaner.learn_room.auth.domain.model.DTO.SingInOrgRequest
import com.wayplaner.learn_room.auth.domain.model.DTO.SingInRequest
import com.wayplaner.learn_room.auth.domain.model.DTO.SingUpOrgRequest
import com.wayplaner.learn_room.auth.domain.model.DTO.SingUpRequest
import com.wayplaner.learn_room.auth.domain.model.DTO.UserResponse
import com.wayplaner.learn_room.utils.CustomerAccount
import kotlinx.coroutines.flow.first


private var context_: Context? = null
fun setContext(context: Context){
    context_ = context
}
private val Context.user: DataStore<Preferences> by preferencesDataStore(name = "userInfo")

suspend fun SingInRequest.save(){
    context_!!.user.edit {
        it[DataStoreKeys.PHONE] = this.phone
        it[DataStoreKeys.PASSWORD] = this.password
    }
}
suspend fun SingUpRequest.save(){
    context_!!.user.edit {
        it[DataStoreKeys.PHONE] = this.phone
        it[DataStoreKeys.PASSWORD] = this.password
    }
}
suspend fun SingInOrgRequest.save(){
    context_!!.user.edit {
        it[DataStoreKeys.LOGIN] = this.login
        it[DataStoreKeys.PASSWORD] = this.password
    }
}
suspend fun SingUpOrgRequest.save(){
    context_!!.user.edit {
        it[DataStoreKeys.LOGIN] = this.login!!
        it[DataStoreKeys.PASSWORD] = this.password!!
    }
}

suspend fun CustomerAccount.getUser(): SingInRequest? {
    val user = context_!!.user.data.first()
    if (user[DataStoreKeys.PHONE] == null) return null
    return SingInRequest(
        user[DataStoreKeys.PHONE]!!,
        user[DataStoreKeys.PASSWORD]!!)
}

suspend fun AdminAccount.getUser(): SingInOrgRequest? {
    val user = context_!!.user.data.first()
    if (user[DataStoreKeys.LOGIN] == null) return null
    return SingInOrgRequest(
        user[DataStoreKeys.LOGIN]!!,
        user[DataStoreKeys.PASSWORD]!!)
}

suspend fun UserResponse.deleteUser() {
    context_!!.user.edit {
        it.clear()
    }
}

suspend fun AdminAccount.deleteUser() {
    context_!!.user.edit {
        it.clear()
    }
}


private object DataStoreKeys {
    val PHONE = stringPreferencesKey("phone")
    val LOGIN = stringPreferencesKey("login")
    val PASSWORD = stringPreferencesKey("password")
}
