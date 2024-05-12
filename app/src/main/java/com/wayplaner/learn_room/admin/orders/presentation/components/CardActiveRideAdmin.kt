package com.wayplaner.learn_room.admin.orders.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.balloon.ArrowOrientation
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonHighlightAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.compose.Balloon
import com.skydoves.balloon.compose.rememberBalloonBuilder
import com.wayplaner.learn_room.R
import com.wayplaner.learn_room.createorder.domain.model.Order
import com.wayplaner.learn_room.createorder.domain.model.OrderSelfDelivery
import com.wayplaner.learn_room.createorder.domain.model.StatusOrder
import com.wayplaner.learn_room.ui.theme.cookingStatus
import com.wayplaner.learn_room.ui.theme.cookingStatusBack
import com.wayplaner.learn_room.ui.theme.deliveryType
import com.wayplaner.learn_room.ui.theme.deliveryTypeBack
import com.wayplaner.learn_room.ui.theme.endCookingStatus
import com.wayplaner.learn_room.ui.theme.endCookingStatusBack
import com.wayplaner.learn_room.ui.theme.endStatus
import com.wayplaner.learn_room.ui.theme.endStatusBack
import com.wayplaner.learn_room.ui.theme.finishStatus
import com.wayplaner.learn_room.ui.theme.finishStatusBack
import com.wayplaner.learn_room.ui.theme.inLineStatus
import com.wayplaner.learn_room.ui.theme.inLineStatusBack
import com.wayplaner.learn_room.ui.theme.onTheWayStatus
import com.wayplaner.learn_room.ui.theme.onTheWayStatusBack
import com.wayplaner.learn_room.ui.theme.redActionColor
import com.wayplaner.learn_room.ui.theme.selfdeliveryType
import com.wayplaner.learn_room.ui.theme.selfdeliveryTypeBack
import com.wayplaner.learn_room.ui.theme.testButton
import com.wayplaner.learn_room.ui.theme.testText
import com.wayplaner.learn_room.ui.theme.waitStatus
import com.wayplaner.learn_room.ui.theme.waitStatusBack

@Composable
private fun CardActiveOrderAdmin(idOrder: String, dateOrder: String, summ: String, isDelivery: Boolean, loginCancel: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(Color.White)) {
        Column(modifier = Modifier.padding(horizontal = 15.dp, vertical = 12.dp)) {
            cardForStatusDeliveryAdmin(isDelivery)
            cardForStatus(StatusOrder.WAIT_ACCEPT)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)) {
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

            Column(modifier = Modifier.padding(top = 20.dp)) {
                Button(shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(redActionColor),
                    onClick = {  }) {
                    Text(text = "Отследить заказ",
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        textAlign = TextAlign.Center)
                }

                Spacer(modifier = Modifier.height(6.dp))

                Button(shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(testButton),
                    onClick = { loginCancel() }) {
                    Text(text = "Отменить", color = redActionColor,
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        textAlign = TextAlign.Center)
                }
            }
        }
    }
}

@Composable
fun createCardOrderActiveAdmin(order: Order, loginCancel: () -> Unit){
    CardActiveOrderAdmin(order.uuid!!.id.toString(), "10 марта", order.summ.toString(), true, loginCancel)
}

@Composable
fun createCardOrderActiveAdmin(orderSelf: OrderSelfDelivery, loginCancel: () -> Unit){
    CardActiveOrderAdmin(orderSelf.uuid!!.id.toString(), "10 марта", orderSelf.summ.toString(), false, loginCancel)
}

@Composable
fun openMapAdmin(staus: StatusOrder){
    val builder = rememberBalloonBuilder {
        setArrowSize(10)
        setArrowPosition(0.5f)
        setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
        setWidth(BalloonSizeSpec.WRAP)
        setHeight(BalloonSizeSpec.WRAP)
        setPadding(12)
        setBalloonHighlightAnimation(BalloonHighlightAnimation.SHAKE)
        setMarginHorizontal(12)
        setCornerRadius(8f)
        setAutoDismissDuration(10000L)
        setArrowOrientation(ArrowOrientation.BOTTOM)
        setBackgroundColorResource(R.color.red_logo)
        setBalloonAnimation(BalloonAnimation.ELASTIC)
    }

    when(staus){
        StatusOrder.ON_TWE_WAY -> builder.setText("В пути :)")
        StatusOrder.COMPLETE_WAY -> builder.setText("У вашей двери")
        else -> builder.setText("Ожидайте пока ваш заказ будет готов")
    }

    createBalloon(staus, builder)

}

@Composable
private fun createBalloon(staus: StatusOrder, builder: Balloon.Builder){
    Balloon(
        builder = builder,
        balloonContent = {
            Text(text = "Now you can edit your profile!")
        }
    ) { balloonWindow ->
        Button(
            modifier = Modifier.size(120.dp, 75.dp),
            onClick = {
                balloonWindow.showAlignTop() // display your balloon.
            }
        ) {
            Text(text = "showAlignTop")
        }
    }
}
@Composable
fun cardForStatusDeliveryAdmin(isDelivery: Boolean) {
    if(isDelivery) {
        Card(
            colors = CardDefaults.cardColors(deliveryTypeBack),
            modifier = Modifier.padding(top = 4.dp, bottom = 6.dp)
        ) {
            Text(
                text = "Доставка",
                color = deliveryType,
                fontSize = 14.sp,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
            )
        }
    }
    else{
        Card(
            colors = CardDefaults.cardColors(selfdeliveryTypeBack),
            modifier = Modifier.padding(top = 4.dp, bottom = 6.dp)
        ) {
            Text(
                text = "Самовывоз",
                color = selfdeliveryType,
                fontSize = 13.sp,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
            )
        }
    }
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
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp))
            }
        }
        StatusOrder.IN_LINE_COOKING -> {
            Card(
                colors = CardDefaults.cardColors(inLineStatusBack)
            ) {
                Text(text = "В очереди на готовку",
                    color = inLineStatus,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp))
            }
        }
        StatusOrder.PROCESS_COOKING -> {
            Card(
                colors = CardDefaults.cardColors(cookingStatusBack)
            ) {
                Text(text = "Готовится",
                    color = cookingStatus,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp))
            }
        }
        StatusOrder.COOKING_END -> {
            Card(
                colors = CardDefaults.cardColors(endCookingStatusBack)
            ) {
                Text(text = "Заказ готов",
                    color = endCookingStatus,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp))
            }
        }
        StatusOrder.ON_TWE_WAY -> {
            Card(
                colors = CardDefaults.cardColors(onTheWayStatusBack)
            ) {
                Text(text = "Заказ в пути",
                    color = onTheWayStatus,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp))
            }
        }
        StatusOrder.COMPLETE_WAY -> {
            Card(
                colors = CardDefaults.cardColors(finishStatusBack)
            ) {
                Text(text = "Заказ ждёт вас",
                    color = finishStatus,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp))
            }
        }
        StatusOrder.COMPLETE_ORDER -> {
            Card(
                colors = CardDefaults.cardColors(endStatusBack)
            ) {
                Text(text = "Доставлено",
                    color = endStatus,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp))
            }
        }
    }
}

@Preview
@Composable
fun cardActivityAdmin(){
    //CardActiveOrder("1", "10 февраля", "12266 руб.", false)
}