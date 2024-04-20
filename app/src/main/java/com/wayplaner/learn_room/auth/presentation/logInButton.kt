package com.wayplaner.learn_room.auth.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.google.android.gms.auth.api.signin.GoogleSignInClient


@Composable
fun logInButton(googleSignInClient: GoogleSignInClient) {
    //val otherLauncher = reme()
    Column {
        Text(text = "Войти через почту")
        Button(onClick = {

            //laun.launch(googleSignInClient.signInIntent)

        }) {
        }
        activityGoogle()
    }
}