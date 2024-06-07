package com.wayplaner.learn_room.createorder.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wayplaner.learn_room.ui.theme.grayList
import com.wayplaner.learn_room.ui.theme.orderCreateBackField
import com.wayplaner.learn_room.ui.theme.orderCreateCard



@Composable
fun PayViewCardOrder() {

    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(orderCreateCard)
    ) {
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "Оплата", fontSize = 18.sp, color = grayList)

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(orderCreateBackField),
                shape = RoundedCornerShape(14.dp),
                onClick = { /*TODO*/ }) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Добьте карту ",
                        modifier = Modifier.weight(1f),
                        fontSize = 16.sp,
                        color = grayList
                    )
                    Icon(
                        imageVector = Icons.Filled.Add,
                        modifier = Modifier.size(21.dp),
                        tint = grayList,
                        contentDescription = null
                    )
                }

            }

            Spacer(modifier = Modifier.height(25.dp))

        }
    }
}
