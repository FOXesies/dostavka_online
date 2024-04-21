package com.wayplaner.learn_room.createorder.util

import com.yandex.mapkit.geometry.BoundingBox
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.search.SuggestOptions
import com.yandex.mapkit.search.SuggestType

object MapKitConstant {
    //Suggest Param
    val BOX_SIZE = 0.2;

    val DEFAULT_POINT = Point(59.945933, 30.320045)

    val BOUNDING_BOX = BoundingBox(
        Point(DEFAULT_POINT.latitude - BOX_SIZE, DEFAULT_POINT.longitude - BOX_SIZE),
        Point(DEFAULT_POINT.latitude + BOX_SIZE, DEFAULT_POINT.longitude + BOX_SIZE))

    val SEARCH_OPTIONS = SuggestOptions().setSuggestTypes(
        SuggestType.UNSPECIFIED.value)
}