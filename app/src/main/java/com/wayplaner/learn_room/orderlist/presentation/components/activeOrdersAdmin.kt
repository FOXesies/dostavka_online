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
import androidx.navigation.NavController
import com.wayplaner.learn_room.MainRoute
import com.wayplaner.learn_room.orderlist.presentation.ListOrderModelView
import com.wayplaner.learn_room.orderlist.util.UiOrderEvent

@Composable
fun ActiveOrders(vmListorder: ListOrderModelView, navController: NavController) {
    Column(modifier = Modifier
        .padding(vertical = 5.dp, horizontal = 10.dp)
        .background(Color.Transparent)) {
            vmListorder.onEvent(UiOrderEvent.OpenActiveOrder)
            val orders = vmListorder.activeOrder.observeAsState()
            if(!orders.value.isNullOrEmpty()) {
                LazyColumn(
                    modifier = Modifier.padding(vertical = 5.dp, horizontal = 6.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(orders.value!!) { order ->
                        CardActiveOrder(vmListorder, order.idOrder!!.toString(), order.organizationName!!,order.fromTimeCooking!!, order.summ.toString(), order.status!!, order.isSelf){
                            navController.navigate(MainRoute.OrdersInfo.name + "/${order.idOrder}")
                        }
                    }
                }
            }
    }
}

@Preview
@Composable
fun previewActiveOrd(){
}
