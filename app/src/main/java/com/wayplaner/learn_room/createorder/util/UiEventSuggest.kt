package com.wayplaner.learn_room.createorder.util

sealed class UiEventSuggest {
    data class NoActive(val message: String): UiEventSuggest()
    data class SuccessSuggest(val listAddress: List<String>): UiEventSuggest()
    data class NoFound(val message: String): UiEventSuggest()
}