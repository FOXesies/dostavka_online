package com.wayplaner.learn_room.utils

import kotlin.Exception

//Они известны как закрытые классы. Как предполагает слово "запечатанный",
// запечатанные классы соответствуют ограниченным или ограниченным иерархиям классов.
// Закрытый класс определяет набор подклассов внутри него. Он используется,
// когда заранее известно, что тип будет соответствовать одному из типов подкласса.
// Закрытые классы обеспечивают безопасность типов, ограничивая сопоставление
// типов во время компиляции, а не во время выполнения.

sealed class FirebaseResponse<out R> {
    data object Loading: FirebaseResponse<Nothing>()
    data class Success<out R>(val result: R): FirebaseResponse<R>()
    data class Failure(val exception: Exception): FirebaseResponse<Nothing>()
}