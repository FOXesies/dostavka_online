package com.wayplaner.learn_room.basket.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.wayplaner.learn_room.MainRoute
import com.wayplaner.learn_room.basket.presentation.components.ProductItemBasket
import com.wayplaner.learn_room.basket.util.UiBasketEvent
import com.wayplaner.learn_room.order.data.model.BasketItem
import com.wayplaner.learn_room.ui.theme.grayColor_Text
import com.wayplaner.learn_room.ui.theme.gray_light
import com.wayplaner.learn_room.ui.theme.lightGrayColor
import com.wayplaner.learn_room.ui.theme.redBlackColor

@Composable
fun BasketScreen(drawerState: DrawerState?,
    navController: NavController,
    vmBasket: BasketModelView = hiltViewModel(),
){
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Red)) {

        Row(modifier = Modifier
            .fillMaxSize()
            .padding(start = 15.dp, top = 20.dp)){
            IconButton(onClick = { navController.navigateUp() }, modifier = Modifier.size(30.dp)){
                Icon(imageVector = Icons.Filled.ArrowBackIosNew,
                    tint = Color.White,
                    contentDescription = null,
                )
            }
        }

        val basket = vmBasket.basketItem.observeAsState()
        val uiEvent = vmBasket.uiBasketEvent.observeAsState()
        when(uiEvent.value!!){
            is UiBasketEvent.EmptyBasket -> ViewEmptyBasket()
            is UiBasketEvent.NormalBasket -> ViewNormalBasket(navController, basket.value!!, vmBasket)
            is UiBasketEvent.ErrorAction -> {
                ViewNormalBasket(navController, basket.value!!, vmBasket)
                Toast.makeText(LocalContext.current, (uiEvent.value as UiBasketEvent.ErrorAction).error, Toast.LENGTH_LONG).show()
            }
        }
    }
}

@Composable
fun ViewEmptyBasket(){
    Card(modifier = Modifier
        .fillMaxSize()
        .padding(top = 80.dp),
        elevation = CardDefaults.cardElevation(20.dp),
        shape = RoundedCornerShape(
            topEnd = 30.dp,
            topStart = 30.dp,
            bottomEnd = 0.dp,
            bottomStart = 0.dp
        ),
        colors = CardDefaults.cardColors(lightGrayColor)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Корзина пуста",
                fontSize = 16.sp,
                color = grayColor_Text
            )
        }
    }
}

@Composable
fun ViewNormalBasket(navController: NavController, value: BasketItem, vmBasket: BasketModelView) {
    Card(modifier = Modifier
        .fillMaxSize()
        .padding(top = 80.dp),
        elevation = CardDefaults.cardElevation(20.dp),
        shape = RoundedCornerShape(
            topEnd = 30.dp,
            topStart = 30.dp,
            bottomEnd = 0.dp,
            bottomStart = 0.dp
        ),
        colors = CardDefaults.cardColors(gray_light)
    ) {
        Column {
            ProductList(value, vmBasket)
        }
    }

    Card(colors = CardDefaults.cardColors(Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(
            topEnd = 20.dp,
            topStart = 20.dp,
            bottomEnd = 0.dp,
            bottomStart = 0.dp
        )
    ) {
        Row(modifier = Modifier.padding(top = 40.dp, start = 20.dp, end = 20.dp)) {
            Text(text = "Сумма", fontSize = 16.sp)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = value.summ.toString(), fontSize = 16.sp)
        }

            Button(
                onClick = {
                    vmBasket.saveInfoInOrder()
                    navController.navigate(MainRoute.CreateOrder.name)
                },
                colors = ButtonDefaults.buttonColors(redBlackColor),
                shape = RoundedCornerShape(20),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(top = 15.dp, start = 20.dp, end = 20.dp),
                elevation = ButtonDefaults.buttonElevation(6.dp)
            ) {
                Text(text = "Продолжить", fontSize = 16.sp)
            }
        }
}

@Composable
fun ProductList(value: BasketItem, vmBasket: BasketModelView) {
    LazyColumn(modifier = Modifier
        .padding(
            start = 20.dp,
            end = 20.dp,
            bottom = 130.dp,
            top = 20.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)){
        items(value.productsPick.sortedBy { it.product!!.name }){
            Spacer(modifier = Modifier.height(2.dp))
            ProductItemBasket(it, vmBasket)
            Spacer(modifier = Modifier.height(2.dp))
        }

    }
}

@Preview
@Composable
fun previewBasket(){
    //BasketScreen()
}