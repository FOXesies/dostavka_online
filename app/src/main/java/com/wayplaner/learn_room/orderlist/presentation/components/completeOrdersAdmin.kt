package com.wayplaner.learn_room.orderlist.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CompleteOrders() {
    LazyColumn(
        modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)){
        items(4){
            CardCompleteOrder()
        }
    }
}