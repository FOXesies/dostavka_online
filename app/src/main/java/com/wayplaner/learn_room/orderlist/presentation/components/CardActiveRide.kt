package com.wayplaner.learn_room.orderlist.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wayplaner.learn_room.createorder.domain.model.OrderSelfDelivery
import com.wayplaner.learn_room.createorder.domain.model.StatusOrder
import com.wayplaner.learn_room.ui.theme.cookingStatus
import com.wayplaner.learn_room.ui.theme.cookingStatusBack
import com.wayplaner.learn_room.ui.theme.endCookingStatus
import com.wayplaner.learn_room.ui.theme.endCookingStatusBack
import com.wayplaner.learn_room.ui.theme.finishStatus
import com.wayplaner.learn_room.ui.theme.finishStatusBack
import com.wayplaner.learn_room.ui.theme.inLineStatus
import com.wayplaner.learn_room.ui.theme.inLineStatusBack
import com.wayplaner.learn_room.ui.theme.onTheWayStatus
import com.wayplaner.learn_room.ui.theme.onTheWayStatusBack
import com.wayplaner.learn_room.ui.theme.redActionColor
import com.wayplaner.learn_room.ui.theme.testButton
import com.wayplaner.learn_room.ui.theme.testText
import com.wayplaner.learn_room.ui.theme.waitStatus
import com.wayplaner.learn_room.ui.theme.waitStatusBack
import org.example.order.model.Order

@Composable
private fun CardActiveOrder(idOrder: String, dateOrder: String, summ: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(Color.White)) {
        Column(modifier = Modifier.padding(horizontal = 15.dp, vertical = 12.dp)) {
            //cardForStatus(StatusOrder.WAIT_ACCEPT)
            Card(
                colors = CardDefaults.cardColors(waitStatusBack)
            ) {
                Text(text = "Ожидает подтверждения...",
                    color = waitStatus,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp))
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp)) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "ID заказа", color = testText)
                    Text(text = idOrder)
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Дата", color = testText)
                    Text(text = dateOrder)
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Цена", color = testText)
                    Text(text = summ + " руб")
                }
            }

            Row(modifier = Modifier.padding(top = 20.dp)) {
                Button(shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(testButton),
                    modifier = Modifier.weight(1f).height(42.dp),
                    onClick = { /*TODO*/ }) {
                    Text(text = "Отменить", color = redActionColor)
                }

                Spacer(modifier = Modifier.width(12.dp))

                Button(shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.weight(1f).height(42.dp),
                    colors = ButtonDefaults.buttonColors(redActionColor),
                    onClick = { /*TODO*/ }) {
                    Text(text = "Отследить заказ")
                }
            }

        }
    }
}

@Composable
fun createCardOrderActive(order: Order){
    CardActiveOrder(order.orderId.toString(), "10 марта", order.summ.toString())
}
@Composable
fun createCardOrderActive(orderSelf: OrderSelfDelivery){
    CardActiveOrder(orderSelf.idOrder.toString(), "10 марта", orderSelf.summ.toString())
}

@Composable
private fun cardForStatus(pick: StatusOrder){
    when(pick){
        StatusOrder.WAIT_ACCEPT -> {
            Card(
                colors = CardDefaults.cardColors(waitStatusBack)
            ) {
                Text(text = "Ожидает подтверждения...",
                    color = waitStatus,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp))
            }
        }
        StatusOrder.IN_LINE_COOKING -> {
            Card(
                colors = CardDefaults.cardColors(inLineStatusBack)
            ) {
                Text(text = "В очереди на готовку",
                    color = inLineStatus,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp))
            }
        }
        StatusOrder.PROCESS_COOKING -> {
            Card(
                colors = CardDefaults.cardColors(cookingStatusBack)
            ) {
                Text(text = "Готовится",
                    color = cookingStatus,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp))
            }
        }
        StatusOrder.COOKING_END -> {
            Card(
                colors = CardDefaults.cardColors(endCookingStatusBack)
            ) {
                Text(text = "Заказ готов",
                    color = endCookingStatus,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp))
            }
        }
        StatusOrder.ON_TWE_WAY -> {
            Card(
                colors = CardDefaults.cardColors(onTheWayStatusBack)
            ) {
                Text(text = "Заказ в пути",
                    color = onTheWayStatus,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp))
            }
        }
        StatusOrder.COMPLETE_WAY -> {
            Card(
                colors = CardDefaults.cardColors(finishStatusBack)
            ) {
                Text(text = "Заказ доставлен",
                    color = finishStatus,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp))
            }
        }
    }
}

@Preview
@Composable
fun cardActivity(){/*
    CardActiveOrder()*/
}