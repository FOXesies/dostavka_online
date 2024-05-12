package com.wayplaner.learn_room.admin.orders.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.sp
import com.wayplaner.learn_room.orderlist.domain.model.CanceledOrder
import com.wayplaner.learn_room.orderlist.domain.model.CanceledOrderSelf
import com.wayplaner.learn_room.ui.theme.blackTransperent
import com.wayplaner.learn_room.ui.theme.errorStatus
import com.wayplaner.learn_room.ui.theme.errorStatusBack
import com.wayplaner.learn_room.ui.theme.testText

@Composable
fun CardCanceledOrderAdmin(orderId: Long, canceledTime: String?, summ: Double?, isDelivery: Boolean) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(Color.White)) {
        Column(modifier = Modifier.padding(horizontal = 15.dp, vertical = 12.dp)) {
            cardForStatusDeliveryAdmin(isDelivery)

            Card(
                colors = CardDefaults.cardColors(errorStatusBack)
            ) {
                Text(text = "Заказ отменён",
                    color = errorStatus,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp))
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp)) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "ID заказа", color = testText)
                    Text(text = "$orderId")
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Дата", color = testText)
                    Text(text = canceledTime!!)
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Цена", color = testText)
                    Text(text = "$summ руб")
                }
            }

            Row(modifier = Modifier.padding(top = 10.dp)) {

                Button(shape = RoundedCornerShape(15.dp),
                    modifier = Modifier.weight(1f).height(40.dp).clickable {},
                    colors = ButtonDefaults.buttonColors(Color.Transparent),
                    onClick = { /*TODO*/ }) {
                    Text(text = "Ждём вас снова", color = blackTransperent, fontSize = 15.sp)
                }
            }

        }
    }
}

@Composable
fun createCancelOrderCardAdmin(canceledOrder: CanceledOrder){
    CardCanceledOrderAdmin(canceledOrder.uuid!!, canceledOrder.toTimeDelivery, canceledOrder.summ, true)
}
@Composable
fun createCancelOrderCardAdmin(canceledOrderSelf: CanceledOrderSelf){
    CardCanceledOrderAdmin(canceledOrderSelf.uuid!!, canceledOrderSelf.toTimeCooling, canceledOrderSelf.summ, false)
}

@Preview
@Composable
fun CcardActivityOrdersAdmin(){
    //CardCanceledOrder(canceledOrder.orderId, canceledOrder.CanceledTime, canceledOrder.summ)
}