package com.wayplaner.learn_room.basket.presentation

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.wayplaner.learn_room.MainRoute
import com.wayplaner.learn_room.R
import com.wayplaner.learn_room.basket.presentation.components.ProductItemBasket
import com.wayplaner.learn_room.order.data.model.BasketItem
import com.wayplaner.learn_room.product.domain.model.Product
import com.wayplaner.learn_room.ui.theme.backHeader
import com.wayplaner.learn_room.ui.theme.backHome
import com.wayplaner.learn_room.ui.theme.backOrgHome
import com.wayplaner.learn_room.ui.theme.grayList
import com.wayplaner.learn_room.ui.theme.redBlackColor
import com.wayplaner.learn_room.ui.theme.whiteColor

@Composable
fun BasketScreen(drawerState: DrawerState?,
    navController: NavController,
    vmBasket: BasketModelView = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        vmBasket.loadBasket()
    }

    val basket = vmBasket.basketItem.observeAsState()
    if (basket.value != null) {
        LaunchedEffect(Unit) {
            vmBasket.parseInfo()
        }

        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .fillMaxSize()
        ) {

            Column {
            Image(painter = painterResource(id = R.drawable.fon_basket),
                alignment = Alignment.TopCenter,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop,
                contentDescription = null)

                Spacer(modifier = Modifier.weight(1f))
            }

            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .clip(MaterialTheme.shapes.small)
                    .padding(top = 8.dp, start = 10.dp)
                    .size(45.dp),
                containerColor = backHeader,
                onClick = { navController.navigateUp() }) {
                Icon(
                    Icons.Filled.KeyboardArrowLeft,
                    tint = whiteColor,
                    modifier = Modifier.size(32.dp),
                    contentDescription = "Добавить"
                )
            }

            val listProduct = vmBasket.products.observeAsState().value
            if(listProduct != null) {
                when (basket.value?.productsPick?.size == 0) {
                    true -> ViewEmptyBasket()
                    false -> ViewNormalBasket(navController, basket.value!!, vmBasket, listProduct)
                }
            }
        }
    }
}

@Composable
fun ViewEmptyBasket(){
    Card(modifier = Modifier
        .fillMaxSize()
        .padding(top = 140.dp),
        elevation = CardDefaults.cardElevation(20.dp),
        shape = RoundedCornerShape(
            topEnd = 30.dp,
            topStart = 30.dp,
            bottomEnd = 0.dp,
            bottomStart = 0.dp
        ),
        colors = CardDefaults.cardColors(backOrgHome)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Корзина пуста",
                fontSize = 16.sp,
                color = grayList
            )
        }
    }
}

@Composable
fun ViewNormalBasket(navController: NavController, value: BasketItem, vmBasket: BasketModelView, listProduct: MutableList<Product>) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 140.dp),
            elevation = CardDefaults.cardElevation(20.dp),
            shape = RoundedCornerShape(
                topEnd = 30.dp,
                topStart = 30.dp,
                bottomEnd = 0.dp,
                bottomStart = 0.dp
            ),
            colors = CardDefaults.cardColors(backOrgHome)
        ) {
            Column(Modifier
                .padding(bottom = 160.dp)) {
                ProductList(value, vmBasket, listProduct)
            }
        }

        Card(
            colors = CardDefaults.cardColors(backHome),
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            shape = RoundedCornerShape(
                topEnd = 20.dp,
                topStart = 20.dp,
                bottomEnd = 0.dp,
                bottomStart = 0.dp
            )
        ) {
            Row(modifier = Modifier.padding(top = 30.dp, start = 20.dp, end = 20.dp)) {
                Text(text = "Сумма", fontSize = 16.sp, color = whiteColor)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = value.summ.toString(), fontSize = 16.sp, color = whiteColor)
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
fun ProductList(value: BasketItem, vmBasket: BasketModelView, listProduct: MutableList<Product>) {
    LazyColumn(modifier = Modifier
        .padding(
            start = 20.dp,
            end = 20.dp,
            top = 20.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)){
        items(value.productsPick.sortedBy { it.product }){ ids ->
            Spacer(modifier = Modifier.height(2.dp))
            ProductItemBasket(ids, vmBasket, listProduct.find { it.idProduct == ids.product }!!)
            Spacer(modifier = Modifier.height(2.dp))
        }

    }
}

@Preview
@Composable
fun previewBasket(){
    //BasketScreen()
}