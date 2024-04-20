package com.wayplaner.learn_room.createorder.presentation.components

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CommentBank
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wayplaner.learn_room.ui.theme.grayColor
import com.wayplaner.learn_room.ui.theme.lightGrayColor
import com.wayplaner.learn_room.ui.theme.redLogoColor
import com.wayplaner.learn_room.ui.theme.textFieldHint

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeliveryPick() {
    var textColorValue = TextFieldDefaults.textFieldColors(
        containerColor = Color.Transparent,
        cursorColor = redLogoColor,
        focusedIndicatorColor = redLogoColor,
        unfocusedIndicatorColor = grayColor,
        focusedLabelColor = redLogoColor,
        unfocusedLabelColor = grayColor,
    )

    Column(modifier = Modifier.background(color = lightGrayColor).padding(horizontal = 2.dp)
        .verticalScroll(rememberScrollState())) {

        Card(
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(Color.White)
        ) {

            Column(modifier = Modifier.padding(horizontal = 20.dp)) {

                Spacer(modifier = Modifier.height(20.dp))

                Text(text = "Куда", fontSize = 18.sp)

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(lightGrayColor),
                    shape = RoundedCornerShape(14.dp),
                    onClick = { /*TODO*/ }) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Выберите адрес ",
                            modifier = Modifier.weight(1f),
                            fontSize = 16.sp,
                            color = textFieldHint
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                            modifier = Modifier.size(21.dp),
                            tint = textFieldHint,
                            contentDescription = null
                        )
                    }

                }

                Spacer(modifier = Modifier.height(20.dp))

                Row {
                    TextField(
                        colors = textColorValue,
                        modifier = Modifier.weight(1f),
                        value = "",
                        onValueChange = {},
                        label = { Text(text = "Подъезд") },
                    )

                    Spacer(modifier = Modifier.width(40.dp))

                    TextField(
                        colors = textColorValue,
                        modifier = Modifier.weight(1f),
                        value = "",
                        onValueChange = {},
                        label = { Text(text = "Домофон") },
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row {
                    TextField(
                        colors = textColorValue,
                        modifier = Modifier.weight(1f), value = "",
                        onValueChange = {},
                        label = { Text(text = "Квартира") },
                    )

                    Spacer(modifier = Modifier.width(40.dp))

                    TextField(
                        colors = textColorValue,
                        modifier = Modifier.weight(1f),
                        value = "",
                        onValueChange = {},
                        label = { Text(text = "Этаж") },
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                Row(
                    modifier = Modifier.padding(bottom = 30.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.CommentBank,
                        modifier = Modifier.size(21.dp),
                        tint = textFieldHint,
                        contentDescription = null
                    )

                    TextField(
                        colors = textColorValue,
                        value = "",
                        modifier = Modifier.fillMaxWidth(),
                        onValueChange = {},
                        label = { Text(text = "Комментарий") },
                    )
                }

            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        PayViewCardOrder()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun prewievDelivery(){
    DeliveryPick()
}