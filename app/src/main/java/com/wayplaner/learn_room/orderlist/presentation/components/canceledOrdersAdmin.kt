package com.wayplaner.learn_room.orderlist.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wayplaner.learn_room.orderlist.presentation.ListOrderModelView
import com.wayplaner.learn_room.orderlist.util.UiOrderEvent

@Composable
fun CanceledOrders(vmListorder: ListOrderModelView) {
    vmListorder.onEvent(UiOrderEvent.OpenCanceledOrder)
    val value = vmListorder.cancelOrder.observeAsState()

    if(value.value!!.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(value.value!!){
                createCancelOrderCard(it)
            }
        }
    }
}