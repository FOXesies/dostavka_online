package com.wayplaner.learn_room.auth.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.wayplaner.learn_room.auth.domain.model.DTO.SingInRequest
import com.wayplaner.learn_room.auth.domain.model.DTO.SingUpRequest
import com.wayplaner.learn_room.auth.domain.model.DTO.UserResponse
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

suspend fun Context.getUser(): SingInRequest? {
    val user = this.user.data.first()
    if (user[DataStoreKeys.PHONE] == null) return null
    return SingInRequest(
        user[DataStoreKeys.PHONE]!!,
        user[DataStoreKeys.PASSWORD]!!)
}

suspend fun UserResponse.deleteUser() {
    context_!!.user.edit {
        it.clear()
    }
}


private object DataStoreKeys {
    val PHONE = stringPreferencesKey("phone")
    val PASSWORD = stringPreferencesKey("password")
}
