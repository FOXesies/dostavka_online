package com.wayplaner.learn_room.admin.home.presentation

import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.wayplaner.learn_room.MainRoute
import com.wayplaner.learn_room.admin.util.AdminAccount
import com.wayplaner.learn_room.ui.theme.backOrgHome
import com.wayplaner.learn_room.ui.theme.redLogoColor

@Composable
fun HomeScreen(
    navController: NavController
) {
    if(AdminAccount.idOrg == null)
        navController.navigate("${MainRoute.Admin_BasicInfo.name}")


    Column(modifier = Modifier.fillMaxSize().background(backOrgHome),
        verticalArrangement = Arrangement.Center,){
        Column(
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally)
        ) {
            Button(onClick = { navController.navigate("${MainRoute.Admin_BasicInfo.name}") },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width(280.dp),
                colors = ButtonDefaults.buttonColors(redLogoColor),
                shape = RoundedCornerShape(40)
            ) {
                Text(text = "Основная информация", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = { navController.navigate("${MainRoute.Admin_MenuList.name}") },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width(280.dp),
                colors = ButtonDefaults.buttonColors(redLogoColor),
                shape = RoundedCornerShape(40)
            ) {
                Text(text = "Меню", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = { navController.navigate("${MainRoute.Admin_Orders.name}") },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width(280.dp),
                colors = ButtonDefaults.buttonColors(redLogoColor),
                shape = RoundedCornerShape(40)
            ) {
                Text(text = "Заказы", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = { navController.navigate("${MainRoute.FeedBacks.name}/${AdminAccount.idOrg}") },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width(280.dp),
                colors = ButtonDefaults.buttonColors(redLogoColor),
                shape = RoundedCornerShape(40)
            ) {
                Text(text = "Отзывы", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}