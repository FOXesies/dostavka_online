package com.wayplaner.learn_room.domain.repository

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import com.wayplaner.learn_room.domain.model.Customer
import com.wayplaner.learn_room.utils.FirebaseResponse
import kotlinx.coroutines.flow.Flow

interface AuthCustomerRepository {
    suspend fun singIn(credential: AuthCredential): Flow<FirebaseResponse<FirebaseUser>>
    fun logout()
}