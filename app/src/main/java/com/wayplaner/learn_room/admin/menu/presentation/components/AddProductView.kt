package com.wayplaner.learn_room.admin.menu.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wayplaner.learn_room.R
import com.wayplaner.learn_room.ui.theme.lightGrayColor
import com.wayplaner.learn_room.ui.theme.redBlackColor
import com.wayplaner.learn_room.ui.theme.redLogoColor

@Composable
fun AddProductView(){
    val colorET = TextFieldDefaults.colors(
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledContainerColor = lightGrayColor,
        focusedContainerColor = lightGrayColor,
        unfocusedContainerColor = lightGrayColor,
    )

    Column {
    Image(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp),
        contentScale = ContentScale.Crop,
        painter = painterResource(id = R.drawable.burger),
        contentDescription = ""
    )

    Column(modifier = Modifier.padding(horizontal = 30.dp)) {

        Spacer(modifier = Modifier.height(15.dp))

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp),
            colors = ButtonDefaults.buttonColors(redLogoColor),
            shape = RoundedCornerShape(40)
        ) {
            Text(text = "Изменить фото блюда", fontSize = 15.sp)
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Название блюда", fontSize = 16.sp,
            modifier = Modifier.padding(start = 5.dp)
        )

        Spacer(modifier = Modifier.height(2.dp))

        TextField(
            value = "Бургер кинг",
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20)),
            colors = colorET
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Описание блюда", fontSize = 16.sp,
            modifier = Modifier.padding(start = 5.dp)
        )

        Spacer(modifier = Modifier.height(2.dp))

        TextField(
            value = "Бургер кинг",
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20)),
            colors = colorET
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = { /*TODO*/ },
            Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(redBlackColor),
            shape = RoundedCornerShape(40)
        ) {
            Text(text = "Сохранить изменения", fontSize = 16.sp)
        }
    }

    }

}
@Composable
@Preview(showBackground = true, showSystemUi = true)
fun Menu1f(){
    AddProductView()
}