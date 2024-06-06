package com.wayplaner.learn_room.createorder.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.wayplaner.learn_room.organization.domain.model.LocationOrganization

object AddressPick {

    val address = MutableLiveData<LocationOrganization>()
    var lifecycle: LifecycleOwner? = null
}