package com.wayplaner.learn_room.admin.orders.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wayplaner.learn_room.orderlist.presentation.components.cardForStatusDelivery
import com.wayplaner.learn_room.orderlist.presentation.components.getLocalDateTime
import com.wayplaner.learn_room.ui.theme.backOrgHome
import com.wayplaner.learn_room.ui.theme.errorStatus
import com.wayplaner.learn_room.ui.theme.errorStatusBack
import com.wayplaner.learn_room.ui.theme.grayList
import com.wayplaner.learn_room.ui.theme.orderCreateCard
import com.wayplaner.learn_room.ui.theme.redActionColor
import com.wayplaner.learn_room.ui.theme.starColor
import com.wayplaner.learn_room.ui.theme.whiteColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardCompleteOrderAdmin(
    nameOrg: String?,
    orderId: Long,
    timeFrom: String?,
    summ: Double?,
    isDelivery: Boolean,
    feedback: Int? = null,
    logicOpen: () -> Unit)
{    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(backOrgHome)) {
        Column(modifier = Modifier.padding(horizontal = 15.dp, vertical = 12.dp)) {
            cardForStatusDelivery(isDelivery)
            Card(
                colors = CardDefaults.cardColors(errorStatusBack)
            ) {
                Text(text = "Заказ доставлен",
                    color = errorStatus,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp))
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "ID заказа", color = grayList)
                    Text(text = orderId.toString(), color = whiteColor)
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Дата", color = grayList)
                    Text(text = getLocalDateTime(timeFrom!!), color = whiteColor)
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Цена", color = grayList)
                    Text(text = "$summ руб", color = whiteColor)
                }
            }

            Column(modifier = Modifier.padding(top = 20.dp)) {

                Button(shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(redActionColor),
                    onClick = { logicOpen() }) {
                    Text(text = "Посмотреть заказ",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        textAlign = TextAlign.Center)
                }

                Spacer(modifier = Modifier.height(6.dp))

                if(feedback != null) {

                    Card(shape = RoundedCornerShape(10.dp),
                        modifier = Modifier,
                        colors = CardDefaults.cardColors(orderCreateCard),
                        onClick = {  }) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            tint = starColor,
                            modifier = Modifier
                                .size(24.dp)
                                .padding(2.dp)
                        )
                        Text(text = feedback.toString(), modifier = Modifier
                            .fillMaxWidth()
                            .height(42.dp)
                            .padding(vertical = 4.dp),
                            textAlign = TextAlign.Center, color = whiteColor
                        )
                    }
                }
            }

        }
    }
}



@Preview
@Composable
fun CcardActivityAdmin(){
}