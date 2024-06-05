package com.wayplaner.learn_room.auth.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wayplaner.learn_room.R
import com.wayplaner.learn_room.ui.theme.backGrayColor
import com.wayplaner.learn_room.ui.theme.textFieldBack
import com.wayplaner.learn_room.ui.theme.textFieldError
import com.wayplaner.learn_room.ui.theme.textFieldFocus
import com.wayplaner.learn_room.ui.theme.textFieldHint
import com.wayplaner.learn_room.ui.theme.textFieldUnFocus
import com.wayplaner.learn_room.ui.theme.whiteColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginComponents() {

    val colorContext = TextFieldDefaults.textFieldColors(
        disabledIndicatorColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        containerColor = textFieldBack,
        focusedPlaceholderColor = textFieldFocus,
        unfocusedPlaceholderColor = textFieldHint,
        errorTextColor = textFieldError,
        focusedLeadingIconColor = textFieldFocus,
        unfocusedLeadingIconColor = textFieldHint,
        focusedTextColor = textFieldFocus,
        unfocusedTextColor = textFieldUnFocus,
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Image(
            painter = painterResource(id = R.drawable.back),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
        )

        Column(modifier = Modifier.align(Alignment.BottomCenter),) {

            Text(modifier = Modifier.padding(bottom = 120.dp, top = 60.dp).fillMaxWidth(),
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
                    Text(modifier = Modifier.padding(start = 10.dp),
                        text = "Войдите в аккаунт",
                        fontSize = 18.sp,
                        color = whiteColor,
                        fontFamily = FontFamily(Font(R.font.montserrat_alternates))
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    var textState = remember {
                        mutableStateOf("")
                    }

                    TextField(modifier = Modifier.fillMaxWidth(),
                        value = textState.value,
                        onValueChange = { textState.value = it },
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

                    Spacer(modifier = Modifier.height(20.dp))

                    TextField(modifier = Modifier.fillMaxWidth(),
                        value = textState.value,
                        onValueChange = { textState.value = it },
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
                            Text(text = "Введите пароль", fontSize = 16.sp)
                        }
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                        colors = ButtonDefaults.buttonColors(textFieldFocus),
                        shape = RoundedCornerShape(16.dp),
                        onClick = { /*TODO*/ }) {
                        Text(text = "Войти", color = whiteColor, fontSize = 16.sp)
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }
            }

            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun preview(){
    LoginComponents()
}