package com.wayplaner.learn_room.orderlist.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wayplaner.learn_room.ui.theme.backOrgHome
import com.wayplaner.learn_room.ui.theme.errorStatus
import com.wayplaner.learn_room.ui.theme.errorStatusBack
import com.wayplaner.learn_room.ui.theme.grayList
import com.wayplaner.learn_room.ui.theme.orderCreateCard
import com.wayplaner.learn_room.ui.theme.redActionColor
import com.wayplaner.learn_room.ui.theme.testButton
import com.wayplaner.learn_room.ui.theme.testText
import com.wayplaner.learn_room.ui.theme.whiteColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardCompleteOrder(orgName: String, orderId: Long, dateOrder: String?, summ: Double?, isDelivery: Boolean, feedback: String? = null) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(backOrgHome)) {
        Column(modifier = Modifier.padding(horizontal = 15.dp, vertical = 12.dp)) {
            cardForStatusDelivery(isDelivery)
            Card(
                colors = CardDefaults.cardColors(errorStatusBack)
            ) {
                Text(text = "Заказ доставлен",
                    color = errorStatus,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp))
            }
            Text(text = "Ресторан: $orgName", modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
                color = whiteColor)

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
                    Text(text = getLocalDateTime(dateOrder!!), color = whiteColor)
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Цена", color = grayList)
                    Text(text = "$summ руб", color = whiteColor)
                }
            }

            Column(modifier = Modifier.padding(top = 20.dp)) {

                Button(shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(redActionColor),
                    onClick = {  }) {
                    Text(text = "Посмотреть заказ",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        textAlign = TextAlign.Center)
                }

                Spacer(modifier = Modifier.height(6.dp))

                if(feedback == null) {
                    Button(shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f).height(42.dp),
                        colors = ButtonDefaults.buttonColors(redActionColor),
                        onClick = { /*TODO*/ }) {
                        Text(text = "Оставьте отзыв ☺\uFE0F")
                    }
                }
                else{
                    Card(shape = RoundedCornerShape(10.dp),
                        modifier = Modifier,
                        colors = CardDefaults.cardColors(orderCreateCard),
                        onClick = { /*TODO*/ }) {
                        Text(text = feedback, modifier = Modifier
                            .fillMaxWidth()
                            .height(42.dp)
                            .padding(vertical = 4.dp),
                            textAlign = TextAlign.Center, color = whiteColor)
                    }
                }
            }

        }
    }
}
