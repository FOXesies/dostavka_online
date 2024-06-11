package com.wayplaner.learn_room.auth.presentation

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.HomeRepairService
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Password
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.wayplaner.learn_room.MainRoute
import com.wayplaner.learn_room.MapSearchActivity
import com.wayplaner.learn_room.R
import com.wayplaner.learn_room.createorder.presentation.components.AddressSuggestModelView
import com.wayplaner.learn_room.ui.theme.backGrayColor
import com.wayplaner.learn_room.ui.theme.conteinertextFieldError
import com.wayplaner.learn_room.ui.theme.grayList
import com.wayplaner.learn_room.ui.theme.redActionColor
import com.wayplaner.learn_room.ui.theme.textFieldBack
import com.wayplaner.learn_room.ui.theme.textFieldFocus
import com.wayplaner.learn_room.ui.theme.textFieldHint
import com.wayplaner.learn_room.ui.theme.textFieldUnFocus
import com.wayplaner.learn_room.ui.theme.whiteColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterOrgScreen(navController: NavController, authModelView: AuthOrgModelView = hiltViewModel()) {
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
    val loginText = remember { mutableStateOf("") }
    val nameText = remember { mutableStateOf("") }
    val cityText = remember { mutableStateOf("") }
    val passwordText = remember { mutableStateOf("") }
    val passwordRepeatText = remember { mutableStateOf("") }

    val userInfo_ = authModelView.userFormState.observeAsState()
    val success = authModelView.success.observeAsState()

    if(success.value!!)
        navController.navigate(MainRoute.Admin_Home.name)

    val cities_ = authModelView.getCity().observeAsState()

    if (cities_.value != null) {
        val cities = cities_.value!!
        var expanded by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            val userInfo = userInfo_.value!!

            if(userInfo.baseError != null) {
                Toast.makeText(mContext, userInfo.baseError!!, Toast.LENGTH_LONG).show()
                userInfo.baseError = null
            }

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
                    .padding(top = 40.dp)
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
                            value = loginText.value,
                            onValueChange = {
                                    loginText.value = it
                                    authModelView.onEvent(EventFormUserState.ChangedLogin(it))
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
                                Text(text = "Введите логин", fontSize = 16.sp)
                            }
                        )
                        if(userInfo.errorLogin != null){
                                Text(
                                    text = userInfo.errorLogin!!,
                                    color = grayList,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                                )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        TextField(modifier = Modifier.fillMaxWidth(),
                            value = phoneText.value,
                            onValueChange = {
                                if (it.length <= 11 && it.all { char -> char.isDigit() }) {
                                    phoneText.value = it
                                    authModelView.onEvent(EventFormUserState.ChangedPhone(it))
                                }
                                            },
                            shape = RoundedCornerShape(16.dp),
                            textStyle = TextStyle(fontSize = 16.sp),
                            singleLine = true,

                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Add,
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
                                    color = grayList,
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
                                    imageVector = Icons.Filled.HomeRepairService,
                                    contentDescription = null
                                )
                            },
                            colors = colorContext,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            placeholder = {
                                Text(text = "Введите название ресторана", fontSize = 16.sp)
                            }
                        )
                        if(userInfo.errorName != null){
                            Text(
                                text = userInfo.errorName!!,
                                color = grayList,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Spacer(modifier = Modifier.height(2.dp))

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
                                    cityText.value = it
                                    authModelView.onEvent(EventFormUserState.ChangedCity(it))
                                    expanded = true
                                },
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
                                color = grayList,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        val selectedText = remember {
                            mutableStateOf("")
                        }

                        val value = AddressSuggestModelView.addressTo?.observeAsState()
                        val displayText = value?.value?.address ?: ""

                        LaunchedEffect(displayText) {
                            selectedText.value = displayText
                            value?.value?.let { authModelView.onEvent(EventFormUserState.ChangedAddress(it)) }
                        }


                        Button(modifier = Modifier
                            .fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(textFieldFocus),
                            shape = RoundedCornerShape(16.dp),
                            onClick = {
                                AddressSuggestModelView.setPickCity(selectedText.value)
                                val intent = Intent(mContext, MapSearchActivity::class.java)
                                mContext.startActivity(intent)
                            }) {
                            if (selectedText.value.isEmpty())
                                Text(text = "Выбрать адрес", color = whiteColor, fontSize = 16.sp, modifier = Modifier
                                    .padding(vertical = 6.dp))
                            else
                                Text(text = selectedText.value, color = whiteColor, fontSize = 16.sp, modifier = Modifier
                                    .padding(vertical = 6.dp))
                        }
                        if(userInfo.errorAddress != null){
                            Text(
                                text = userInfo.errorAddress!!,
                                color = grayList,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        var showPassword by remember { mutableStateOf(false) }

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
                            trailingIcon = {
                                // Password visibility toggle icon
                                PasswordVisibilityToggleIcon(
                                    showPassword = showPassword,
                                    onTogglePasswordVisibility = { showPassword = !showPassword })
                            },
                            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                            colors = colorContext,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                            placeholder = {
                                Text(text = "Введите пароль", fontSize = 16.sp)
                            }
                        )
                        if(userInfo.passwordError != null){
                            Text(
                                text = userInfo.passwordError!!,
                                color = grayList,
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
                            textStyle = TextStyle(fontSize = 16.sp),
                            singleLine = true,
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Password,
                                    contentDescription = null
                                )
                            },
                            trailingIcon = {
                                // Password visibility toggle icon
                                PasswordVisibilityToggleIcon(
                                    showPassword = showPassword,
                                    onTogglePasswordVisibility = { showPassword = !showPassword })
                            },
                            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                            colors = colorContext,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                            placeholder = {
                                Text(text = "Подтвердите пароль", fontSize = 16.sp)
                            }
                        )
                        if(userInfo.passwordRepeatError != null){
                            Text(
                                text = userInfo.passwordRepeatError!!,
                                color = grayList,
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

            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .clip(MaterialTheme.shapes.small)
                    .padding(top = 8.dp, start = 10.dp)
                    .size(45.dp),
                containerColor = whiteColor,
                onClick = { navController.navigateUp() }) {
                Icon(
                    Icons.Filled.KeyboardArrowLeft,
                    tint = redActionColor,
                    modifier = Modifier.size(32.dp),
                    contentDescription = "Добавить"
                )
            }
        }
    }
}