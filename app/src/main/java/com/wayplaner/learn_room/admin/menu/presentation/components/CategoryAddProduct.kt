package com.wayplaner.learn_room.admin.menu.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wayplaner.learn_room.ui.theme.grayColor
import com.wayplaner.learn_room.ui.theme.whiteColor

@Composable
fun CategoryAdminView(){
    val listCategory = mutableListOf("Супы", "Шашлык", "Алкоголь", "Закуски")

    LazyRow(Modifier.fillMaxWidth()){
        items(listCategory){
            Button(modifier = Modifier
                .padding(horizontal = 5.dp),
                colors = ButtonDefaults.buttonColors(grayColor),
                onClick = { /*TODO*/ }) {
                Text(
                    text = it,
                    color = whiteColor,
                    fontSize = 14.sp)
            }
        }
        item{
            Button(modifier = Modifier
                .padding(horizontal = 5.dp),
                colors = ButtonDefaults.buttonColors(grayColor),
                contentPadding = PaddingValues(start = 10.dp, end = 18.dp),
                onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null, Modifier.padding(start = 0.dp))
                Text(
                    text = "Добавить",
                    color = whiteColor,
                    fontSize = 14.sp)
            }
        }
    }
}