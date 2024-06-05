package com.wayplaner.learn_room.auth.presentation

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wayplaner.learn_room.R
import com.wayplaner.learn_room.ui.theme.backGrayColor
import com.wayplaner.learn_room.ui.theme.conteinertextFieldError
import com.wayplaner.learn_room.ui.theme.textFieldBack
import com.wayplaner.learn_room.ui.theme.textFieldFocus
import com.wayplaner.learn_room.ui.theme.textFieldHint
import com.wayplaner.learn_room.ui.theme.textFieldUnFocus
import com.wayplaner.learn_room.ui.theme.whiteColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(authModelView: AuthModelView = hiltViewModel()) {
    val mContext = LocalContext.current

    val colorContext = TextFieldDefaults.textFieldColors(
        disabledIndicatorColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        containerColor = textFieldBack,
        errorContainerColor = conteinertextFieldError,
        focusedLeadingIconColor = textFieldFocus,
        unfocusedLeadingIconColor = textFieldHint,
        focusedTextColor = textFieldFocus,
        unfocusedTextColor = textFieldUnFocus,
    )

    val phoneText = remember { mutableStateOf("") }
    val nameText = remember { mutableStateOf("") }
    val cityText = remember { mutableStateOf("") }
    val passwordText = remember { mutableStateOf("") }
    val passwordRepeatText = remember { mutableStateOf("") }

    val userInfo_ = authModelView.userFormState.observeAsState()
    val success = authModelView.success.observeAsState()
    val cities_ = authModelView.getCity().observeAsState()

    if (cities_.value != null) {
        val cities = cities_.value!!
        var expanded by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val userInfo = userInfo_.value!!

            if(userInfo.baseError != null)
            Toast.makeText(mContext, userInfo.baseError!!, Toast.LENGTH_LONG).show()

            Image(
                painter = painterResource(id = R.drawable.back),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .verticalScroll(
                        rememberScrollState()
                    )
            ) {

                Text(
                    modifier = Modifier
                        .padding(bottom = 120.dp, top = 60.dp)
                        .fillMaxWidth(),
                    text = "Быстрая еда",
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center,
                    color = whiteColor,
                    fontFamily = FontFamily(Font(R.font.montserrat_alternates))
                )

                ElevatedCard(
                    modifier = Modifier.padding(horizontal = 15.dp),
                    colors = CardDefaults.cardColors(backGrayColor)
                ) {
                    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                        Spacer(modifier = Modifier.height(22.dp))
                        Text(
                            modifier = Modifier.padding(start = 10.dp),
                            text = "Создайте аккаунт",
                            fontSize = 18.sp,
                            color = whiteColor,
                            fontFamily = FontFamily(Font(R.font.montserrat_alternates))
                        )
                        Spacer(modifier = Modifier.height(16.dp))


                        TextField(modifier = Modifier.fillMaxWidth(),
                            value = phoneText.value,
                            onValueChange = {
                                phoneText.value = it
                                authModelView.onEvent(EventFormUserState.ChangedPhone(phoneText.value))
                                            },
                            shape = RoundedCornerShape(16.dp),
                            textStyle = TextStyle(fontSize = 16.sp),
                            singleLine = true,

                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Phone,
                                    contentDescription = null
                                )
                            },
                            colors = colorContext,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            placeholder = {
                                Text(text = "Введите номер телефона", fontSize = 16.sp)
                            }
                        )
                        if(userInfo.errorPhone != null){
                                Text(
                                    text = userInfo.errorPhone!!,
                                    color = MaterialTheme.colorScheme.error,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                                )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        TextField(modifier = Modifier.fillMaxWidth(),
                            value = nameText.value,
                            onValueChange = { nameText.value = it
                                authModelView.onEvent(EventFormUserState.ChangedName(nameText.value))
                            },
                            shape = RoundedCornerShape(16.dp),
                            textStyle = TextStyle(fontSize = 16.sp),
                            singleLine = true,
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.AccountCircle,
                                    contentDescription = null
                                )
                            },
                            colors = colorContext,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            placeholder = {
                                Text(text = "Введите имя", fontSize = 16.sp)
                            }
                        )
                        if(userInfo.errorName != null){
                            Text(
                                text = userInfo.errorName!!,
                                color = MaterialTheme.colorScheme.error,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded }
                        ) {
                            TextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(),
                                value = cityText.value,
                                onValueChange = {
                                    expanded = true
                                },
                                readOnly = true,
                                shape = RoundedCornerShape(16.dp),
                                textStyle = TextStyle(fontSize = 16.sp),
                                singleLine = true,
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Filled.LocationCity,
                                        contentDescription = null
                                    )
                                },
                                trailingIcon = {
                                    Icon(
                                        imageVector = Icons.Filled.KeyboardArrowDown,
                                        contentDescription = null
                                    )
                                },
                                colors = colorContext,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                                placeholder = {
                                    Text(text = "Выберите город", fontSize = 16.sp)
                                }
                            )
                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                cities.forEach { city ->
                                    DropdownMenuItem(
                                        text = { Text(text = city) },
                                        onClick = {
                                            cityText.value = city
                                            authModelView.onEvent(EventFormUserState.ChangedCity(city))
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }

                        if(userInfo.errorCity != null){
                            Text(
                                text = userInfo.errorCity!!,
                                color = MaterialTheme.colorScheme.error,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        TextField(modifier = Modifier.fillMaxWidth(),
                            value = passwordText.value,
                            onValueChange = { passwordText.value = it
                                authModelView.onEvent(EventFormUserState.ChangedPassword(it))
                                            },
                            shape = RoundedCornerShape(16.dp),
                            textStyle = TextStyle(fontSize = 16.sp),
                            singleLine = true,
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Password,
                                    contentDescription = null
                                )
                            },
                            colors = colorContext,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            placeholder = {
                                Text(text = "Введите пароль", fontSize = 16.sp)
                            }
                        )
                        if(userInfo.passwordError != null){
                            Text(
                                text = userInfo.passwordError!!,
                                color = MaterialTheme.colorScheme.error,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        TextField(modifier = Modifier.fillMaxWidth(),
                            value = passwordRepeatText.value,
                            onValueChange = { passwordRepeatText.value = it
                                authModelView.onEvent(EventFormUserState.ChangedPasswordRepeat(it))
                            },
                            shape = RoundedCornerShape(16.dp),
                            singleLine = true,
                            textStyle = TextStyle(fontSize = 16.sp),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Password,
                                    contentDescription = null
                                )
                            },
                            colors = colorContext,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            placeholder = {
                                Text(text = "Подтвердите пароль", fontSize = 16.sp)
                            }
                        )
                        if(userInfo.passwordRepeatError != null){
                            Text(
                                text = userInfo.passwordRepeatError!!,
                                color = MaterialTheme.colorScheme.error,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                            colors = ButtonDefaults.buttonColors(textFieldFocus),
                            shape = RoundedCornerShape(16.dp),
                            onClick = { authModelView.onEvent(EventFormUserState.SumbitSingUp) }) {
                            Text(text = "Зарегестрироваться", color = whiteColor, fontSize = 16.sp)
                        }

                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }

                Spacer(modifier = Modifier.height(60.dp))
            }
        }
    }
}


@Composable
@Preview(showSystemUi = true)
fun preview2(){
    RegisterScreen()
}