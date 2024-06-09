package com.wayplaner.learn_room.admin.orders.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.skydoves.balloon.ArrowOrientation
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonHighlightAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.compose.Balloon
import com.skydoves.balloon.compose.rememberBalloonBuilder
import com.wayplaner.learn_room.MainRoute
import com.wayplaner.learn_room.R
import com.wayplaner.learn_room.admin.orders.presentation.AdminOrdersModelView
import com.wayplaner.learn_room.createorder.domain.model.StatusOrder
import com.wayplaner.learn_room.createorder.domain.model.StatusOrder.Companion.getBackColor
import com.wayplaner.learn_room.createorder.domain.model.StatusOrder.Companion.getNext
import com.wayplaner.learn_room.createorder.domain.model.StatusOrder.Companion.getText
import com.wayplaner.learn_room.createorder.domain.model.StatusOrder.Companion.getTextColor
import com.wayplaner.learn_room.orderlist.presentation.components.cardForStatusDelivery
import com.wayplaner.learn_room.orderlist.presentation.components.getLocalDateTime
import com.wayplaner.learn_room.orderlist.util.UiOrderEvent
import com.wayplaner.learn_room.ui.theme.backOrgHome
import com.wayplaner.learn_room.ui.theme.deliveryType
import com.wayplaner.learn_room.ui.theme.deliveryTypeBack
import com.wayplaner.learn_room.ui.theme.grayList
import com.wayplaner.learn_room.ui.theme.lightGrayColor
import com.wayplaner.learn_room.ui.theme.orderCreateBackField
import com.wayplaner.learn_room.ui.theme.orderCreateCard
import com.wayplaner.learn_room.ui.theme.redActionColor
import com.wayplaner.learn_room.ui.theme.selfdeliveryType
import com.wayplaner.learn_room.ui.theme.selfdeliveryTypeBack
import com.wayplaner.learn_room.ui.theme.testButton
import com.wayplaner.learn_room.ui.theme.whiteColor

@Composable
fun CardActiveOrderAdmin(
    navController: NavController,
    idOrder: String,
    vmListorder: AdminOrdersModelView,
    dateOrder: String,
    summ: String,
    statusOrder: StatusOrder,
    isDelivery: Boolean,
) {
    var status by remember { mutableStateOf(statusOrder) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(backOrgHome)) {
        Column(modifier = Modifier.padding(horizontal = 15.dp, vertical = 12.dp)) {
            cardForStatusDelivery(isDelivery)
            Card(
                colors = CardDefaults.cardColors(status.getBackColor())
            ) {
                Text(text = status.getText(),
                    color = status.getTextColor(),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp))
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "ID заказа", color = grayList)
                    Text(text = idOrder, color = whiteColor)
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Дата", color = grayList)
                    Text(text = getLocalDateTime(dateOrder), color = whiteColor)
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Цена", color = grayList)
                    Text(text = summ + " руб", color = whiteColor)
                }
            }

            Column(modifier = Modifier.padding(top = 20.dp)) {
                var openStateDelete by remember {
                    mutableStateOf(false)
                }

                if (openStateDelete) {
                    AlertDeleteAddress(
                        confirmLogic = { comment ->
                            vmListorder.onEvent(UiOrderEvent.CancelOrder(idOrder.toLong(), comment))
                            openStateDelete = false
                        },
                        dismissLogic = { openStateDelete = false }
                    )
                }
                Button(shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(redActionColor),
                    onClick = { navController.navigate(MainRoute.Admin_OrderInfo.name + "/$idOrder") }) {
                    Text(text = "Открыть заказ",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        textAlign = TextAlign.Center)
                }

                Spacer(modifier = Modifier.height(6.dp))

                Button(shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(testButton),
                    onClick = {
                        openStateDelete = true
                    }) {
                    Text(text = "Отменить", color = redActionColor,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        textAlign = TextAlign.Center)
                }

                Spacer(modifier = Modifier.height(6.dp))
            }
        }
    }
}

@Composable
private fun AlertDeleteAddress(confirmLogic: (String) -> Unit, dismissLogic: () -> Unit) {

    val comment = remember {
        mutableStateOf("")
    }
    AlertDialog(
        containerColor = orderCreateCard,
        onDismissRequest = {  },
        title = { Text("Вы уверены, что хотите отменить заказ?!", fontSize = 20.sp, color = whiteColor) },
        text = {
            val colorET = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledContainerColor = lightGrayColor,
                focusedContainerColor = lightGrayColor,
                unfocusedContainerColor = lightGrayColor,
            )
            Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(orderCreateBackField),
            shape = RoundedCornerShape(14.dp)
        ) {
            TextField(
                value = comment.value,
                onValueChange = {
                    comment.value = it
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp)
                    .clip(RoundedCornerShape(20)),
                colors = colorET
            )
        } },
        confirmButton = {
            TextButton(colors = ButtonDefaults.buttonColors(redActionColor), onClick = {
                confirmLogic(comment.value)
            }) {
                Text("Удалить".uppercase(), color = whiteColor)
            }
        },
        dismissButton = {
            TextButton(onClick = { dismissLogic() }) {
                Text("Закрыть".uppercase(), color = grayList)
            }
        },
    )
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

@Preview
@Composable
fun cardActivityAdmin(){
    //CardActiveOrder("1", "10 февраля", "12266 руб.", false)
}