package com.wayplaner.learn_room.admin.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wayplaner.learn_room.ui.theme.redLogoColor

@Composable
fun HomeScreen() {
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,){
        Column(
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally)
        ) {
            Button(onClick = { /*TODO*/ },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width(250.dp),
                colors = ButtonDefaults.buttonColors(redLogoColor),
                shape = RoundedCornerShape(40)
            ) {
                Text(text = "Основная информация")
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = { /*TODO*/ },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width(250.dp),
                colors = ButtonDefaults.buttonColors(redLogoColor),
                shape = RoundedCornerShape(40)
            ) {
                Text(text = "Меню")
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = { /*TODO*/ },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width(250.dp),
                colors = ButtonDefaults.buttonColors(redLogoColor),
                shape = RoundedCornerShape(40)
            ) {
                Text(text = "Заказы")
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = { /*TODO*/ },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width(250.dp),
                colors = ButtonDefaults.buttonColors(redLogoColor),
                shape = RoundedCornerShape(40)
            ) {
                Text(text = "Отзывы")
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = { /*TODO*/ },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width(250.dp),
                colors = ButtonDefaults.buttonColors(redLogoColor),
                shape = RoundedCornerShape(40)
            ) {
                Text(text = "Настройки")
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun fsdf(){
    HomeScreen()
}