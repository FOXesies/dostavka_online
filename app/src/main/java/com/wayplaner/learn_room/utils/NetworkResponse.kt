package com.wayplaner.learn_room.utils

sealed class NetworkResponse<out R> {
    data object Loading: NetworkResponse<Nothing>()
    data class Success<out R>(val result: R): NetworkResponse<R>()
    data class Failure(val exception: Exception): NetworkResponse<Nothing>()
}