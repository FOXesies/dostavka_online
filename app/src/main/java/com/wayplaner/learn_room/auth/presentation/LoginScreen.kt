package com.wayplaner.learn_room.auth.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wayplaner.learn_room.R
import com.wayplaner.learn_room.ui.theme.textFieldBack
import com.wayplaner.learn_room.ui.theme.textFieldError
import com.wayplaner.learn_room.ui.theme.textFieldFocus
import com.wayplaner.learn_room.ui.theme.textFieldHint
import com.wayplaner.learn_room.ui.theme.textFieldUnFocus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginComponents(){

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
    Column(modifier = Modifier.padding(horizontal = 30.dp)) {
        Spacer(modifier = Modifier.height(35.dp))

        Text(text = "Войдите в аккаунт",
            fontSize = 18.sp,
            color = textFieldUnFocus,
            fontFamily = FontFamily(Font(R.font.montserrat_alternates))
        )
        Spacer(modifier = Modifier.height(20.dp))

        var textState = remember {
            mutableStateOf("")
        }

        TextField(modifier = Modifier.fillMaxWidth(),
            value = textState.value,
            onValueChange = { textState.value = it },
            shape = RoundedCornerShape(16.dp),
            textStyle = TextStyle(fontSize = 16.sp),
            singleLine = true,
            leadingIcon = { Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = null) },
            colors = colorContext,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            placeholder = {
                Text(text = "Введите имя", fontSize = 16.sp)
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextField(modifier = Modifier.fillMaxWidth(),
            value = textState.value,
            onValueChange = { textState.value = it },
            shape = RoundedCornerShape(16.dp),
            singleLine = true,
            textStyle = TextStyle(fontSize = 16.sp),
            leadingIcon = { Icon(imageVector = Icons.Filled.Email, contentDescription = null) },
            colors = colorContext,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            placeholder = {
                Text(text = "Введите электронную почту", fontSize = 16.sp)
            }
        )

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
@Preview(showSystemUi = true)
fun preview(){
    LoginComponents()
}