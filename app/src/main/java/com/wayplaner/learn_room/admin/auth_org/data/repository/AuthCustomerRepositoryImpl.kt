package com.wayplaner.learn_room.auth.data.repository

import com.wayplaner.learn_room.auth.domain.model.DTO.SingInOrgRequest
import com.wayplaner.learn_room.auth.domain.model.DTO.SingUpOrgRequest
import com.wayplaner.learn_room.auth.domain.repository.AuthOrgRepository
import javax.inject.Singleton

@Singleton
class AuthOrgRepositoryImpl(private var repository: AuthOrgRepository) {
    suspend fun getCities() = repository.getCities()
    suspend fun sing_in(singInRequest: SingInOrgRequest) = repository.sing_in(singInRequest)
    suspend fun sing_up(singUpRequest: SingUpOrgRequest) = repository.sing_up(singUpRequest)
}