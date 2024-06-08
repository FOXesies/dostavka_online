package com.wayplaner.learn_room.admin.menu.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.wayplaner.learn_room.MainRoute
import com.wayplaner.learn_room.R
import com.wayplaner.learn_room.admin.menu.presentation.components.AdminProductItem
import com.wayplaner.learn_room.admin.menu.util.UiEventMenuAdd
import com.wayplaner.learn_room.ui.theme.backHome
import com.wayplaner.learn_room.ui.theme.redActionColor
import com.wayplaner.learn_room.ui.theme.summRedColor
import com.wayplaner.learn_room.ui.theme.whiteColor

@Composable
fun MenuList(navController: NavController, modelView: MenuModelView = hiltViewModel()) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(backHome)) {

        Column(Modifier.padding(top = 80.dp)) {

            LaunchedEffect(Unit) {
                modelView.onEvent(UiEventMenuAdd.ListProducts)
            }

            val listProducts = modelView.listProducts.observeAsState()

            if (listProducts.value != null) {
                LazyColumn(modifier = Modifier.padding(horizontal = 20.dp)) {
                    item {
                        Text(
                            text = "Добавить блюдо",
                            modifier = Modifier.padding(
                                start = 5.dp,
                                end = 5.dp,
                                top = 10.dp
                            ),
                            color = whiteColor,
                            style = MaterialTheme.typography.labelMedium,
                            fontSize = 18.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = {
                                navController.navigate(MainRoute.Admin_MenuProduct.name + "/${null}/${null}")
                            },
                            Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            colors = ButtonDefaults.buttonColors(redActionColor),
                            shape = RoundedCornerShape(20)
                        ) {
                            Text(text = "Добавить блюдо", fontSize = 16.sp)
                        }

                        Spacer(modifier = Modifier.height(20.dp))
                    }
                    listProducts.value!!.forEach { (category, products) ->

                        item {
                            Text(
                                text = category,
                                modifier = Modifier.padding(
                                    start = 5.dp,
                                    end = 5.dp,
                                    top = 10.dp
                                ),
                                color = whiteColor,
                                style = MaterialTheme.typography.labelMedium,
                                fontSize = 18.sp
                            )
                        }
                        items(products) { product ->
                            AdminProductItem(product) {
                                navController.navigate(MainRoute.Admin_MenuProduct.name + "/${product.id}/${category}")
                            }
                        }
                    }
                }
            }
        }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            text = "Список блюд",
            fontSize = 22.sp,
            color = whiteColor,
            fontFamily = FontFamily(
                Font(
                    R.font.ag_cooper_cyr,
                    FontWeight.Medium
                )
            ),
            textAlign = TextAlign.Center
        )

        FloatingActionButton(
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .padding(top = 12.dp, start = 16.dp, bottom = 5.dp, end = 5.dp)
                .size(45.dp),
            containerColor = summRedColor,
            onClick = { navController.navigateUp() }) {
            Icon(
                Icons.Filled.KeyboardArrowLeft,
                tint = whiteColor,
                modifier = Modifier.size(32.dp),
                contentDescription = "Добавить"
            )
        }
    }
}

@Preview
@Composable
fun asda(){
    //MenuList()
}