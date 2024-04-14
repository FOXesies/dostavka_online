package com.wayplaner.learn_room.presentation.auth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.wayplaner.learn_room.presentation.auth.components.firebase.logInButton
import com.wayplaner.learn_room.ui.theme.Learn_roomTheme

class AuthScreen : ComponentActivity() {
    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        googleSignInClient.signOut().addOnCompleteListener(this) {
            // После успешного выхода из аккаунта, отзовем доступ и разорвем соединение
            googleSignInClient.revokeAccess().addOnCompleteListener(this) {
                googleSignInClient.signOut().addOnCompleteListener(this) {
                    // Теперь пользователь снова увидит выбор аккаунта при следующей аутентификации
                }
            }
        }

        setContent {
            Learn_roomTheme {
                Column {
                   logInButton(googleSignInClient)
                }
            }
        }
    }
}
