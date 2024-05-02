package com.wayplaner.learn_room.orderlist.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wayplaner.learn_room.createorder.domain.model.OrderSelfDelivery
import com.wayplaner.learn_room.orderlist.presentation.ListOrderModelView
import com.wayplaner.learn_room.orderlist.util.UiOrderEvent
import org.example.order.model.Order

@Composable
fun ActiveOrders(vmListorder: ListOrderModelView) {

/*    val options = listOf(
        "Доставка",
        "Самовывоз"
    )
    var selectedOption by remember {
        mutableIntStateOf(0)
    }
    */
    Column(modifier = Modifier
        .padding(vertical = 5.dp, horizontal = 10.dp)
        .background(Color.Transparent)) {

        /*Row(horizontalArrangement = Arrangement.Center, modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 15.dp, top = 10.dp)) {
            options.forEachIndexed { index, s ->
                Button(shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(if(selectedOption == index) redActionColor else testButton),
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 10.dp)
                        .height(42.dp),
                    onClick = {
                        selectedOption = index
                    }) {
                    Text(text = options[index], color = if(selectedOption == index) Color.White else testText)
                }
            }
        }

        if(selectedOption == 1) {*/
            vmListorder.onEvent(UiOrderEvent.OpenActiveOrder)
            val orders = vmListorder.combineOrder.observeAsState()
            if(!orders.value.isNullOrEmpty()) {
                LazyColumn(
                    modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(orders.value!!) {
                        when (it) {
                            is Order -> createCardOrderActive(it)
                            is OrderSelfDelivery -> createCardOrderActive(it)
                        }
                    }
                }
            }
        /*}
        else{
            vmListorder.onEvent(UiOrderEvent.OpenActiveOrder)
            val orders = vmListorder.orders.observeAsState()
            if(!orders.value.isNullOrEmpty()) {
                LazyColumn(
                    modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(orders.value!!) {
                        createCardOrderActive(it)
                    }
                }
            }
        }*/
    }
}

@Preview
@Composable
fun previewActiveOrd(){
}
