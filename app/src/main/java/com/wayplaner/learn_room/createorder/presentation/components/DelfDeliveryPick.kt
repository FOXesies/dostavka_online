package com.wayplaner.learn_room.createorder.presentation.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CommentBank
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.wayplaner.learn_room.createorder.presentation.CreateOrderModelView
import com.wayplaner.learn_room.ui.theme.grayColor
import com.wayplaner.learn_room.ui.theme.lightGrayColor
import com.wayplaner.learn_room.ui.theme.redLogoColor
import com.wayplaner.learn_room.ui.theme.textFieldHint
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.search.SearchFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelfDeliveryPick(vmCreateOrder: CreateOrderModelView) {
    var textColorValue = TextFieldDefaults.textFieldColors(
        containerColor = Color.Transparent,
        cursorColor = redLogoColor,
        focusedIndicatorColor = redLogoColor,
        unfocusedIndicatorColor = grayColor,
        focusedLabelColor = redLogoColor,
        unfocusedLabelColor = grayColor,
    )

    Column(modifier = Modifier
        .background(color = lightGrayColor)
        .padding(horizontal = 2.dp)
        .verticalScroll(rememberScrollState())) {

        PhoneViewCard(vmCreateOrder)

        Spacer(modifier = Modifier.height(10.dp))

        Card(
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(Color.White)
        ) {

            Column(modifier = Modifier.padding(horizontal = 20.dp)) {

                Spacer(modifier = Modifier.height(20.dp))

                Text(text = "Откуда", fontSize = 18.sp)

                Spacer(modifier = Modifier.height(20.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(lightGrayColor),
                    shape = RoundedCornerShape(14.dp),
                    onClick = { /*TODO*/ }) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 14.dp)
                            .padding(horizontal = 20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Москва, улица Московская, 30",
                            modifier = Modifier.weight(1f),
                            fontSize = 16.sp,
                            color = textFieldHint
                        )
                    }

                }

                Spacer(modifier = Modifier.height(20.dp))

                Card(
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)) {
                    AndroidView(factory = {

                        val mapFragment = MapFragment()
                        val inflater = LayoutInflater.from(it)
                        val container: ViewGroup? = null

                        mapFragment.onCreateView(inflater, container, Bundle())
                    })
                }

                Spacer(modifier = Modifier.height(30.dp))

                Row(
                    modifier = Modifier.padding(bottom = 30.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.CommentBank,
                        modifier = Modifier.size(21.dp),
                        tint = textFieldHint,
                        contentDescription = null
                    )

                    TextField(
                        colors = textColorValue,
                        value = vmCreateOrder.comment.value,
                        modifier = Modifier.fillMaxWidth(),
                        onValueChange = { vmCreateOrder.comment.value = it },
                        label = { Text(text = "Комментарий") },
                    )
                }

            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        PayViewCardOrder()
    }
}

@Composable
@Preview
fun previewcreateor(){
    //SelfDeliveryPick(vmCreateOrder)
}