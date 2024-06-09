package com.wayplaner.learn_room.admin.orders.presentation.components

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
import com.wayplaner.learn_room.admin.orders.presentation.AdminOrdersModelView

@Composable
fun CanceledOrdersAdmin(vmListorder: AdminOrdersModelView, navController: NavController) {
    val value = vmListorder.cancelOrder.observeAsState()

    if(value.value!!.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(value.value!!){
                CardCanceledOrderAdmin(
                    it.orderPreview!!.organizationName,
                    it.orderPreview!!.idOrder!!,
                    it.timeCandeled,
                    it.orderPreview!!.summ,
                    it.orderPreview!!.isSelf,
                    it.comment!!){
                    navController.navigate(MainRoute.Admin_OrderInfo.name + "/${it.orderPreview!!.idOrder!!}")
                }
            }
        }
    }
}