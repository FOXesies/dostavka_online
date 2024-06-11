package com.wayplaner.learn_room.splash.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.wayplaner.learn_room.MainActivityPage
import com.wayplaner.learn_room.R
import com.wayplaner.learn_room.admin.util.AdminAccount
import com.wayplaner.learn_room.auth.util.getUser
import com.wayplaner.learn_room.auth.util.setContext
import com.wayplaner.learn_room.utils.CustomerAccount
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
            val user = CustomerAccount.getUser()
            val org = AdminAccount.getUser()

            if(user != null){
                modelView.sing_in(user)
                delay(1000)
            }
            else if (org != null){
                modelView.sing_in_org(org)
                delay(1000)
            }
            else delay(2000)

            startActivity(Intent(this@SplashActivity, MainActivityPage::class.java))
            finish()

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        this.cancel()
    }


}