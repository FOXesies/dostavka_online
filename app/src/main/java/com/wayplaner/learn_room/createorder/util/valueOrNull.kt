package com.wayplaner.learn_room.createorder.util

inline fun String.valueOrNull(): String? {
    if(this.isEmpty())
        return null
    else
        return this

}