package com.wayplaner.learn_room.orderlist.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.wayplaner.learn_room.MainRoute
import com.wayplaner.learn_room.orderlist.presentation.ListOrderModelView

@Composable
fun CompleteOrders(vmListorder: ListOrderModelView, navController: NavController) {
    val orders = vmListorder.completeOrder.observeAsState()
    if(!orders.value.isNullOrEmpty()) {
        LazyColumn(
            modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(orders.value!!) { order ->
                CardCompleteOrder(order.orderPreview!!.organizationName!!, order.orderPreview!!.idOrder!!, order.orderPreview!!.fromTimeCooking!!, order.orderPreview!!.summ,  order.orderPreview!!.isSelf, order.rating) {
                    navController.navigate(MainRoute.OrdersInfo.name + "/${order.orderPreview!!.idOrder!!}")
                }
            }
        }
    }
}