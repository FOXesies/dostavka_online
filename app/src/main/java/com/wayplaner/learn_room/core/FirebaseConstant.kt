package com.wayplaner.learn_room.core

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

object FirebaseValues {
    private val FIREBASE = FirebaseDatabase.getInstance()
    val CATEGORY_REF = FIREBASE.getReference("categories")
    val ORGANIZATION_REF = FIREBASE.getReference("organizations")
    val FEEDBACK_REF = FIREBASE.getReference("estimate")
}