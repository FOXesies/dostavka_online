package com.wayplaner.learn_room.domain.model

//Аннотация @JvmOverloads сообщает компилятору создать все возможные перегруженные варианты конструктор
data class Holiday @JvmOverloads constructor(
    var name: String = "",
    var dateClose: String = "",
    var dateOpen: String = "",
    var timeClose: String = "",
    var timeOpen: String = "",
    var isClose: Boolean = false,
    var description: String? = null
) {

}