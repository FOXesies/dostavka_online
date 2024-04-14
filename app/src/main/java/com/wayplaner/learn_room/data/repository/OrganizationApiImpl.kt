package com.wayplaner.learn_room.data.repository

import com.wayplaner.learn_room.domain.repository.OrganizationApi

class OrganizationApiImpl(private val organizationApi: OrganizationApi) {

    // получение ресторана
    suspend fun getOrganizationById(id: Long) = organizationApi.getOrganizationsById(id)

}