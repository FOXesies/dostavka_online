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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wayplaner.learn_room.orderlist.domain.model.CancelOrderPreview
import com.wayplaner.learn_room.ui.theme.backOrgHome
import com.wayplaner.learn_room.ui.theme.errorStatus
import com.wayplaner.learn_room.ui.theme.errorStatusBack
import com.wayplaner.learn_room.ui.theme.grayList
import com.wayplaner.learn_room.ui.theme.redActionColor
import com.wayplaner.learn_room.ui.theme.whiteColor

@Composable
fun CardCanceledOrder(
    nameOrg: String?,
    orderId: Long,
    canceledTime: String?,
    summ: Double?,
    isDelivery: Boolean,
    canceledComment: String,
    logicOpen: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(backOrgHome)) {
        Column(modifier = Modifier.padding(horizontal = 15.dp, vertical = 12.dp)) {
            cardForStatusDelivery(isDelivery)

            Card(
                colors = CardDefaults.cardColors(errorStatusBack)
            ) {
                Text(text = "Заказ отменён по причине \"$canceledComment\"",
                    color = errorStatus,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp))
            }

            Text(text = "Ресторан: $nameOrg", modifier = Modifier
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
                    Text(text = getLocalDateTime(canceledTime!!), color = whiteColor)
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Цена", color = grayList)
                    Text(text = "$summ руб", color = whiteColor)
                }
            }

                Button(shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.padding(top = 20.dp),
                    colors = ButtonDefaults.buttonColors(redActionColor),
                    onClick = { logicOpen() }) {
                    Text(
                        text = "Посмотреть заказ",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        textAlign = TextAlign.Center
                    )
                }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                modifier = Modifier.height(30.dp).fillMaxWidth(),
                text = "Ждём вас снова",
                textAlign = TextAlign.Center,
                color = grayList,
                fontSize = 15.sp
            )
        }
    }
}

@Composable
fun createCancelOrderCard(canceledOrder: CancelOrderPreview, logicOpen: () -> Unit){
    CardCanceledOrder(canceledOrder.orderPreview!!.organizationName, canceledOrder.orderPreview!!.idOrder!!, canceledOrder.orderPreview!!.toTimeCooking!!, canceledOrder.orderPreview!!.summ!!, canceledOrder.orderPreview!!.isSelf, canceledOrder.comment!!, logicOpen)
}
@Preview
@Composable
fun CcardActivityOrders(){
/*    CardCanceledOrder(
        " canceledOrder.orderPreview!!.organizationName",
        1,
        "2024-06-09T11:25:00",
        1010.0,
        true,
        "sdfsd",
        logicOpen
    )*/
}