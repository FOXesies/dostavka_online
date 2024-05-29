package com.wayplaner.learn_room.admin.menu.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.wayplaner.learn_room.MainRoute
import com.wayplaner.learn_room.admin.menu.data.model.ResponseProduct
import com.wayplaner.learn_room.admin.menu.presentation.components.AdminProductItem
import com.wayplaner.learn_room.ui.theme.redActionColor
import com.wayplaner.learn_room.ui.theme.whiteColor

@Composable
fun MenuList(navController: NavController, modelView: MenuModelView = hiltViewModel()) {
    Column {

        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Список блюд", fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth().padding(20.dp),
                textAlign = TextAlign.Center
            )

            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .clip(MaterialTheme.shapes.small)
                    .padding(top = 8.dp, start = 10.dp, end = 2.dp, bottom = 2.dp)
                    .size(45.dp),
                containerColor = whiteColor,
                onClick = { navController.navigateUp() }) {
                Icon(
                    Icons.Filled.KeyboardArrowLeft,
                    tint = redActionColor,
                    modifier = Modifier.size(32.dp),
                    contentDescription = null
                )
            }
        }

        val listProducts = modelView.listProducts.observeAsState()
        if (listProducts.value != null) {
            LazyColumn(modifier = Modifier.padding(horizontal = 10.dp)) {
                listProducts.value!!.forEach { (category, products) ->
                    item {
                        Text(text = category,
                            modifier = Modifier.padding(start = 5.dp, end = 5.dp, top = 10.dp),
                            style = MaterialTheme.typography.labelMedium,
                            fontSize = 18.sp)
                    }
                    items(products) { product ->
                        AdminProductItem(product){
                            MenuModelView.setPickProduct(ResponseProduct(
                                product = product,
                                category = category,
                            ))
                            navController.navigate(MainRoute.Admin_MenuProduct.name)
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun asda(){
    //MenuList()
}