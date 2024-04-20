package com.wayplaner.learn_room.organization.data.repository

import com.wayplaner.learn_room.organization.domain.repository.OrganizationApi

class OrganizationApiImpl(private val organizationApi: OrganizationApi) {

    // получение ресторана
    suspend fun getOrganizationById(id: Long) = organizationApi.getOrganizationsById(id)

}