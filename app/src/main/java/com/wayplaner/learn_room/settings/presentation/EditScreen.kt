package com.wayplaner.learn_room.settings.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.wayplaner.learn_room.MainRoute
import com.wayplaner.learn_room.R
import com.wayplaner.learn_room.ui.theme.backOrgHome
import com.wayplaner.learn_room.ui.theme.grayList
import com.wayplaner.learn_room.ui.theme.orderCreateBackField
import com.wayplaner.learn_room.ui.theme.orderCreateCard
import com.wayplaner.learn_room.ui.theme.redActionColor
import com.wayplaner.learn_room.ui.theme.saveColor
import com.wayplaner.learn_room.ui.theme.summRedColor
import com.wayplaner.learn_room.ui.theme.textFieldError
import com.wayplaner.learn_room.ui.theme.whiteColor

@Composable
fun EditProfileScreen(navController: NavController, settingsModelView: SettingsModelView = hiltViewModel()) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backOrgHome)
        ) {

            LaunchedEffect(Unit){
                settingsModelView.getInfo()
            }
            val user = settingsModelView.userInfo.observeAsState()
            if(user.value != null) {

                val phoneState = remember { mutableStateOf(user.value?.phone?: "") }
                val nameState = remember { mutableStateOf(user.value?.name?: "") }

            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .clip(MaterialTheme.shapes.small)
                    .padding(top = 12.dp, start = 16.dp, bottom = 5.dp, end = 5.dp)
                    .size(45.dp),
                containerColor = summRedColor,
                onClick = { navController.navigateUp() }) {
                Icon(
                    Icons.Filled.KeyboardArrowLeft,
                    tint = whiteColor,
                    modifier = Modifier.size(32.dp),
                    contentDescription = "Добавить"
                )
            }

            Text(
                text = "Профиль",
                color = Color.White,
                fontSize = 24.sp,
                fontFamily = FontFamily(
                    Font(
                        R.font.ag_cooper_cyr,
                        FontWeight.Medium
                    )
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                textAlign = TextAlign.Center
            )

            Column(
                modifier = Modifier
                    .padding(horizontal = 40.dp)
                    .padding(top = 150.dp)
                    .align(Alignment.BottomCenter),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column() {
                    Spacer(modifier = Modifier.height(20.dp))

                    Text(text = "Имя", fontSize = 18.sp, color = grayList)

                    Spacer(modifier = Modifier.height(10.dp))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),
                        colors = CardDefaults.cardColors(orderCreateBackField),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp, top = 2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                imageVector = Icons.Filled.AccountCircle,
                                modifier = Modifier
                                    .size(21.dp)
                                    .padding(bottom = 2.dp),
                                tint = grayList,
                                contentDescription = null
                            )
                            TextField(
                                colors = TextFieldDefaults.colors(
                                    focusedTextColor = whiteColor,
                                    cursorColor = whiteColor,
                                    unfocusedTextColor = grayList,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent
                                ),
                                value = nameState.value,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                                onValueChange = {
                                    nameState.value = it
                                },
                                textStyle = TextStyle.Default.copy(fontSize = 18.sp),
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight(),
                            )
                        }


                        Spacer(modifier = Modifier.height(25.dp))

                    }
                }
                Column() {
                    Spacer(modifier = Modifier.height(30.dp))

                    Text(text = "Номер телефона", fontSize = 18.sp, color = grayList)

                    Spacer(modifier = Modifier.height(10.dp))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),
                        colors = CardDefaults.cardColors(orderCreateBackField),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp, top = 2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                imageVector = Icons.Filled.Add,
                                modifier = Modifier
                                    .size(21.dp)
                                    .padding(bottom = 2.dp),
                                tint = grayList,
                                contentDescription = null
                            )
                            TextField(
                                colors = TextFieldDefaults.colors(
                                    focusedTextColor = whiteColor,
                                    unfocusedTextColor = grayList,
                                    cursorColor = whiteColor,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent
                                ),
                                value = phoneState.value,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                                onValueChange = {
                                    if (it.length <= 11 && it.all { char -> char.isDigit() }) {
                                        phoneState.value = it
                                    }
                                },
                                textStyle = TextStyle.Default.copy(fontSize = 18.sp),
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight(),
                            )
                        }

                    }

                    Spacer(modifier = Modifier.height(25.dp))

                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(saveColor),
                    onClick = { settingsModelView.updateValue(phoneState.value, nameState.value) }) {
                    Text(
                        text = "Сохранить данные",
                        fontSize = 16.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                val error = settingsModelView.responseAuth_.observeAsState()
                if(error.value != null && error.value!!.message != null) {
                    Toast.makeText(LocalContext.current, error.value!!.message, Toast.LENGTH_LONG)
                        .show()
                    settingsModelView.responseAuth_.postValue(null)
                }

                var stateFeedbacks by remember { mutableStateOf(false) }
                if (stateFeedbacks) {
                    verifyLogout(
                        onConfirm = {
                            settingsModelView.logout()
                            navController.navigate(MainRoute.LoginCustomer.name) {
                                popUpTo(navController.graph.startDestinationId) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        },
                        onDismiss = {
                            stateFeedbacks = false
                        })
                }

                Button(
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(textFieldError),
                    onClick = {
                        stateFeedbacks = true
                    }) {
                    Text(
                        text = "Выйти из аккаунта",
                        fontSize = 16.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@Composable
fun verifyLogout(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit){
    AlertDialog(
    containerColor = orderCreateCard,
    onDismissRequest = { onDismiss() },
    title = {
        Text(text = "Уверены, что хотите выйти?", color = whiteColor)
    },
    confirmButton = {
        TextButton(colors = ButtonDefaults.buttonColors(redActionColor),
            onClick = {
                onConfirm()
                onDismiss()
            }
        ) {
            Text("Да", color = whiteColor, fontSize = 16.sp, modifier = Modifier.padding(4.dp))
        }
    },
    dismissButton = {
        TextButton(
            onClick = {onDismiss()}
        ) {
            Text("Нет", color = whiteColor, fontSize = 16.sp, modifier = Modifier.padding(4.dp))
        }
    }
    )
}