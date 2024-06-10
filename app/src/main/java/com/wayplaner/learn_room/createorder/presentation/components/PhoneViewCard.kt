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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import com.wayplaner.learn_room.ui.theme.grayList
import com.wayplaner.learn_room.ui.theme.orderCreateBackField
import com.wayplaner.learn_room.ui.theme.orderCreateCard
import com.wayplaner.learn_room.ui.theme.whiteColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneViewCard(
    vmCreateOrder: CreateOrderModelView
) {
    var textColorValue = TextFieldDefaults.textFieldColors(
        focusedTextColor = whiteColor,
        unfocusedTextColor = grayList,
        containerColor = Color.Transparent,
        cursorColor = whiteColor,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        focusedLabelColor = whiteColor,
        unfocusedLabelColor = grayList,
    )

    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(orderCreateCard)
    ) {
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "Номер телефона", fontSize = 18.sp, color = grayList)

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                colors = CardDefaults.cardColors(orderCreateBackField),
                shape = RoundedCornerShape(14.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth().padding(start = 20.dp, top = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Filled.Add,
                        modifier = Modifier.size(21.dp).padding(bottom = 2.dp),
                        tint = grayList,
                        contentDescription = null
                    )
                    TextField(
                        colors = textColorValue,
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