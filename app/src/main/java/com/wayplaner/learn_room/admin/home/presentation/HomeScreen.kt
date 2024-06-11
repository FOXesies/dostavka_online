package com.wayplaner.learn_room.admin.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.wayplaner.learn_room.MainRoute
import com.wayplaner.learn_room.admin.util.AdminAccount
import com.wayplaner.learn_room.auth.util.deleteUser
import com.wayplaner.learn_room.settings.presentation.verifyLogout
import com.wayplaner.learn_room.ui.theme.backOrgHome
import com.wayplaner.learn_room.ui.theme.orderCreateBackField
import com.wayplaner.learn_room.ui.theme.redActionColor
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavController
) {

    Column(modifier = Modifier
        .fillMaxSize()
        .background(backOrgHome),
        verticalArrangement = Arrangement.Center){
        Column(
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally)
        ) {
            val modifiers = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 40.dp)
                .fillMaxWidth()

            Spacer(modifier = Modifier.weight(1f))

            Button(onClick = { navController.navigate("${MainRoute.Admin_BasicInfo.name}") },
                modifier = modifiers,
                colors = ButtonDefaults.buttonColors(orderCreateBackField),
                shape = RoundedCornerShape(40)
            ) {
                Text(text = "Основная информация", fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = { navController.navigate("${MainRoute.Admin_MenuList.name}") },
                modifier = modifiers,
                colors = ButtonDefaults.buttonColors(orderCreateBackField),
                shape = RoundedCornerShape(40)
            ) {
                Text(text = "Меню", fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = { navController.navigate("${MainRoute.Admin_Orders.name}") },
                modifier = modifiers,
                colors = ButtonDefaults.buttonColors(orderCreateBackField),
                shape = RoundedCornerShape(40)
            ) {
                Text(text = "Заказы", fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = { navController.navigate("${MainRoute.FeedBacks.name}/${AdminAccount.idOrg}") },
                modifier = modifiers,
                colors = ButtonDefaults.buttonColors(orderCreateBackField),
                shape = RoundedCornerShape(40)
            ) {
                Text(text = "Отзывы", fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.weight(1f))
            val coroutineScope = rememberCoroutineScope()

            var stateFeedbacks by remember { mutableStateOf(false) }
            if (stateFeedbacks) {
                verifyLogout(
                    onConfirm = {
                        coroutineScope.launch {
                            AdminAccount.deleteUser()
                            AdminAccount.idOrg = null
                            navController.navigate(MainRoute.LoginCustomer.name) {
                                popUpTo(navController.graph.startDestinationId) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        }
                    },
                    onDismiss = {
                        stateFeedbacks = false
                    })
            }

            Button(onClick = { stateFeedbacks = true },
                modifier = modifiers,
                colors = ButtonDefaults.buttonColors(redActionColor),
                shape = RoundedCornerShape(40)
            ) {
                Text(text = "Выйти из аккаунта", fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}