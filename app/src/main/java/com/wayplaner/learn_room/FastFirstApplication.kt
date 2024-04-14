package com.wayplaner.learn_room

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import retrofit2.Retrofit

@HiltAndroidApp
public class FastFirstApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        //FirebaseApp.initializeApp(this)

    }
}
