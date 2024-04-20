package com.wayplaner.learn_room.auth.data.repository

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.wayplaner.learn_room.auth.domain.model.Customer
import com.wayplaner.learn_room.auth.domain.repository.AuthCustomerRepository
import com.wayplaner.learn_room.utils.FirebaseResponse
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Singleton

@Singleton
class AuthCustomerRepositoryImpl(
    ) : AuthCustomerRepository {

    override suspend fun singIn(
        credential: AuthCredential
    ): Flow<FirebaseResponse<FirebaseUser>> =
        callbackFlow {

            this@callbackFlow.trySendBlocking(FirebaseResponse.Loading)
            try {
                FirebaseAuth.getInstance().signInWithCredential(credential).apply {
                    this.await()
                    saveCustomer(FirebaseAuth.getInstance().currentUser!!)
                    this@callbackFlow.trySendBlocking(FirebaseResponse.Success(FirebaseAuth.getInstance().currentUser!!))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                this@callbackFlow.trySendBlocking(FirebaseResponse.Failure(e))
            }

            awaitClose {
                channel.close()
                cancel()
            }

        }

    private fun saveCustomer(currentUser: FirebaseUser) {
        val customer = Customer(name = currentUser.displayName, email = currentUser.email)
        FirebaseDatabase.getInstance().getReference("users/${currentUser.uid}").setValue(customer)
    }
    override fun logout() {
        TODO("Not yet implemented")
    }
}