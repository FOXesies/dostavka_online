package com.wayplaner.learn_room.auth.data.repository

import com.wayplaner.learn_room.auth.domain.model.DTO.SingInRequest
import com.wayplaner.learn_room.auth.domain.model.DTO.SingUpRequest
import com.wayplaner.learn_room.auth.domain.repository.AuthCustomerRepository
import javax.inject.Singleton

@Singleton
class AuthCustomerRepositoryImpl(private var repository: AuthCustomerRepository) {
    suspend fun getCities() = repository.getCities()
    suspend fun sing_in(singInRequest: SingInRequest) = repository.sing_in(singInRequest)
    suspend fun sing_up(singUpRequest: SingUpRequest) = repository.sing_up(singUpRequest)
}