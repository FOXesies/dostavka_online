package com.wayplaner.learn_room.admin.infoorder.presintation

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.wayplaner.learn_room.R
import com.wayplaner.learn_room.admin.infoorder.domain.model.SendBasicInfoOrder
import com.wayplaner.learn_room.admin.util.AdminAccount
import com.wayplaner.learn_room.createorder.domain.model.StatusOrder
import com.wayplaner.learn_room.createorder.domain.model.StatusOrder.Companion.getBack
import com.wayplaner.learn_room.createorder.domain.model.StatusOrder.Companion.getBackColor
import com.wayplaner.learn_room.createorder.domain.model.StatusOrder.Companion.getNext
import com.wayplaner.learn_room.createorder.domain.model.StatusOrder.Companion.getText
import com.wayplaner.learn_room.createorder.domain.model.StatusOrder.Companion.getTextColor
import com.wayplaner.learn_room.orderlist.presentation.components.getLocalDateTime
import com.wayplaner.learn_room.organization.domain.model.ResponseProductOrg
import com.wayplaner.learn_room.organization.presentation.components.ProductCard
import com.wayplaner.learn_room.ui.theme.backOrgHome
import com.wayplaner.learn_room.ui.theme.grayList
import com.wayplaner.learn_room.ui.theme.orderCreateBackField
import com.wayplaner.learn_room.ui.theme.orderCreateCard
import com.wayplaner.learn_room.ui.theme.phoneCallColor
import com.wayplaner.learn_room.ui.theme.redActionColor
import com.wayplaner.learn_room.ui.theme.summRedColor
import com.wayplaner.learn_room.ui.theme.whiteColor

@Composable
fun InfoOrderAdmin(navController: NavController, infoOrderAdminModelView: InfoOrderAdminModelView = hiltViewModel()) {
    val infoOrder = infoOrderAdminModelView.info.observeAsState()

    Box(
        modifier = Modifier
            .background(backOrgHome)
    ) {

        if (infoOrder.value != null) {

            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
            ) {
                item {
                    Row {
                        Column {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.CenterHorizontally)
                                    .padding(top = 18.dp),
                                text = if (infoOrder.value!!.isSelf!!) "Заказ на самовывоз" else "Заказ на доставку",
                                fontSize = 22.sp,
                                color = whiteColor,
                                fontFamily = FontFamily(
                                    Font(
                                        R.font.ag_cooper_cyr,
                                        FontWeight.Medium
                                    )
                                ),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.CenterHorizontally)
                                    .padding(top = 4.dp),
                                text = "№ ${infoOrder.value!!.orderId}", fontSize = 22.sp,
                                color = whiteColor,
                                fontFamily = FontFamily(
                                    Font(
                                        R.font.ag_cooper_cyr,
                                        FontWeight.Medium
                                    )
                                ),
                                textAlign = TextAlign.Center
                            )
                            
                            Spacer(modifier = Modifier.height(25.dp))

                        }
                    }
                }
                item {
                    DeliverySummary(infoOrder.value!!, navController)
                }
            }
        }

        FloatingActionButton(
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .padding(top = 12.dp, start = 16.dp, bottom = 5.dp, end = 5.dp)
                .size(45.dp),
            containerColor = summRedColor,
            onClick = { navController.navigateUp() }) {
            Icon(
                Icons.Filled.KeyboardArrowLeft,
                tint = whiteColor,
                modifier = Modifier.size(32.dp),
                contentDescription = "Добавить"
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeliverySummary(value: SendBasicInfoOrder, navController: NavController) {
    val textColorValue = TextFieldDefaults.textFieldColors(
        focusedTextColor = whiteColor,
        unfocusedTextColor = grayList,
        containerColor = Color.Transparent,
        cursorColor = whiteColor,
        focusedIndicatorColor = whiteColor,
        unfocusedIndicatorColor = grayList,
        focusedLabelColor = whiteColor,
        unfocusedLabelColor = grayList,
    )
    val modifierButton = Modifier
        .fillMaxWidth()
        .height(50.dp)

    CustomCard("Имя пользователя", value.userName!!){}

    Spacer(modifier = Modifier.height(10.dp))

    CustomCard("Телефон", value.phoneUser!!) {

        val context = LocalContext.current
        Button(shape = RoundedCornerShape(15.dp),
            modifier = modifierButton,
            colors = ButtonDefaults.buttonColors(phoneCallColor),
            onClick = {
                val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:+${value.phoneUser!!}")
            }
                context.startActivity(intent) }) {
            Text(text = "Позвонить", fontSize = 16.sp)
        }
    }

    Spacer(modifier = Modifier.height(10.dp))

    if(value.isSelf!!){
        CustomCard("Адрес нашей точки", value.idLocation!!.address!!) {
            Button(shape = RoundedCornerShape(15.dp),
                modifier = modifierButton,
                colors = ButtonDefaults.buttonColors(redActionColor),
                onClick = { /*TODO*/ }) {
                Text(text = "Открыть на карте", fontSize = 16.sp)
            }
        }
    }
    else{
        CustomCard("Адрес пользователя", value.addressUser!!.displayText!!) {
            Button(shape = RoundedCornerShape(15.dp),
                modifier = modifierButton,
                colors = ButtonDefaults.buttonColors(redActionColor),
                onClick = { /*TODO*/ }) {
                Text(text = "Открыть на карте", fontSize = 16.sp)
            }
        }
    }

    Spacer(modifier = Modifier.height(10.dp))

    CustomCard("Время создания заказа", getLocalDateTime(value.fromTimeDelivery!!)) {
        Text(text = "Ожидаемое время доставки", fontSize = 18.sp, color = grayList)

        Spacer(modifier = Modifier.height(10.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(orderCreateBackField),
            shape = RoundedCornerShape(14.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = getLocalDateTime(value.toTimeDelivery!!),
                    modifier = Modifier.weight(1f),
                    fontSize = 16.sp,
                    color = grayList
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(10.dp))

    CustomStatusCard("Статус доставки", value.status!!)

    Spacer(modifier = Modifier.height(10.dp))

    CustomAdminListProduct(value.productOrder, navController)

    Spacer(modifier = Modifier.height(10.dp))

    CustomCard("Комментарий", value.comment ?: ""){}

    Spacer(modifier = Modifier.height(10.dp))

    CustomCard("Сумма", value.summ!!.toString().plus(" руб.")){}

    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun CustomAdminListProduct(products: List<ResponseProductOrg>, navController: NavController){
    Card(
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(orderCreateCard)
    ) {
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "Список продуктов", fontSize = 18.sp, color = grayList)

            Spacer(modifier = Modifier.height(5.dp))

            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp)) {
                products.forEach {
                    ProductCard(
                        product = it,
                        orgId = AdminAccount.idOrg,
                        navController = navController
                    )

                }
            }

            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}

@Composable
fun CustomStatusCard(label: String, value: StatusOrder){
    var status by remember { mutableStateOf(value) }
    Card(
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(orderCreateCard)
    ) {
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            Spacer(modifier = Modifier.height(20.dp))

            Text(text = label, fontSize = 18.sp, color = grayList)

            Spacer(modifier = Modifier.height(10.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(status.getBackColor()),
                shape = RoundedCornerShape(14.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = status.getText(),
                        modifier = Modifier.weight(1f),
                        fontSize = 16.sp,
                        color = status.getTextColor()
                    )
                }

            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "Для переключения статуса заказа нажмите кнопку ниже", fontSize = 14.sp, color = grayList)

            Spacer(modifier = Modifier.height(4.dp))

            val nextValue = (status.getNext()?: StatusOrder.COMPLETE_ORDER)
            Button(shape = RoundedCornerShape(15.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(nextValue.getBackColor()),
                onClick = { status = nextValue }) {
                Text(text = nextValue.getText(), fontSize = 16.sp, color = nextValue.getTextColor())
            }

            val backValue = status.getBack()
            if(backValue != null) {
                Spacer(modifier = Modifier.height(10.dp))

                Button(shape = RoundedCornerShape(15.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(backValue.getBackColor()),
                    onClick = { status = backValue }) {
                    Text(text = "Вернуть назад", fontSize = 16.sp, color = backValue.getTextColor())
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}



@Composable
fun CustomCard(label: String, value: String, content: @Composable (() -> Unit?)? = null) {
    Card(
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(orderCreateCard)
    ) {
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            Spacer(modifier = Modifier.height(20.dp))

            Text(text = label, fontSize = 18.sp, color = grayList)

            Spacer(modifier = Modifier.height(10.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(orderCreateBackField),
                shape = RoundedCornerShape(14.dp),
                ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = value,
                        modifier = Modifier.weight(1f),
                        fontSize = 16.sp,
                        color = grayList
                    )
                }

            }

            if(content != null){
                Spacer(modifier = Modifier.height(10.dp))
                content()
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun prewivasdmionfoorder(){
}