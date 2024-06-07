package com.wayplaner.learn_room.admin.orders.presentation.components

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
import com.wayplaner.learn_room.admin.orders.presentation.AdminOrdersModelView
import com.wayplaner.learn_room.createorder.domain.model.StatusOrder
import com.wayplaner.learn_room.orderlist.util.UiOrderEvent

@Composable
fun ActiveOrdersAdmin(navController: NavController, vmListorder: AdminOrdersModelView) {
    Column(
        modifier = Modifier
            .padding(vertical = 5.dp, horizontal = 10.dp)
            .background(Color.Transparent)
    ) {
        vmListorder.onEvent(UiOrderEvent.OpenActiveOrder)
        val orders = vmListorder.activeOrder.observeAsState()
        if (!orders.value.isNullOrEmpty()) {
            LazyColumn(
                modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(orders.value!!) { order ->
                    CardActiveOrderAdmin(navController, order.idOrder.toString(),
                        order.fromTimeCooking!!,
                        order.summ.toString(),
                        order.status ?: StatusOrder.COMPLETE_ORDER,
                        order.isSelf,
                        loginCancel = { vmListorder.onEvent(UiOrderEvent.CancelOrder(order.idOrder!!)) },
                        logicSwitchStatus = { vmListorder.onEvent(UiOrderEvent.SwitchOrder(order.idOrder!!, order.status!!)) })
                }
            }
        }
    }
}

@Preview
@Composable
fun previewActiveOrdAdmin(){
}
