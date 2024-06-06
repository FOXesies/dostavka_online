package com.wayplaner.learn_room.splash.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.wayplaner.learn_room.MainActivityPage
import com.wayplaner.learn_room.R
import com.wayplaner.learn_room.auth.util.getUser
import com.wayplaner.learn_room.auth.util.setContext
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity: AppCompatActivity(), CoroutineScope by MainScope() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        var modelView = ViewModelProvider(this)[SplashViewModel::class.java]

        launch {

            setContext(this@SplashActivity)
            val user = getUser()
            if(user == null){
                delay(2000)
            }
            else {
                modelView.sing_in(user)
                delay(1000)
            }

            startActivity(Intent(this@SplashActivity, MainActivityPage::class.java))
            finish()

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        this.cancel()
    }


}