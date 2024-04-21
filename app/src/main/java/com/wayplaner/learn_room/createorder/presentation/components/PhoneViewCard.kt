package com.wayplaner.learn_room.createorder.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wayplaner.learn_room.createorder.presentation.CreateOrderModelView
import com.wayplaner.learn_room.createorder.util.OrderFormState
import com.wayplaner.learn_room.ui.theme.grayColor
import com.wayplaner.learn_room.ui.theme.lightGrayColor
import com.wayplaner.learn_room.ui.theme.redLogoColor
import com.wayplaner.learn_room.ui.theme.textFieldHint


@Composable
fun PhoneViewCard(
    vmCreateOrder: CreateOrderModelView
) {

    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "Номер телефона", fontSize = 18.sp)

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                colors = CardDefaults.cardColors(lightGrayColor),
                shape = RoundedCornerShape(14.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth().padding(start = 20.dp, top = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Filled.Add,
                        modifier = Modifier.size(21.dp).padding(bottom = 2.dp),
                        tint = textFieldHint,
                        contentDescription = null
                    )
                    TextField(
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent),
                        value = vmCreateOrder.phone.value,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        onValueChange = {
                            if (it.length <= 11 && it.all {char -> char.isDigit() }) {
                                vmCreateOrder.phone.value = it
                                vmCreateOrder.onValidateEvent(OrderFormState.PhoneChanged(it))
                            }
                        },
                        textStyle = TextStyle.Default.copy(fontSize = 18.sp),
                        modifier = Modifier.weight(1f).fillMaxHeight(),
                    )
                }

            }

            Spacer(modifier = Modifier.height(25.dp))

        }
    }
}

@Preview
@Composable
fun previewPhone(){
    //PhoneViewCard()
}