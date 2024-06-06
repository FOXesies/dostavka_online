package com.wayplaner.learn_room.product.presentation

import android.graphics.BitmapFactory
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.wayplaner.learn_room.R
import com.wayplaner.learn_room.basket.presentation.BasketModelView
import com.wayplaner.learn_room.product.domain.model.Product
import com.wayplaner.learn_room.ui.theme.grayColor_Text
import com.wayplaner.learn_room.ui.theme.redActionColor
import com.wayplaner.learn_room.ui.theme.redBlackColor
import com.wayplaner.learn_room.ui.theme.whiteColor

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductScreen(
    idOrg: Long,
    idProduct: Long,
    navController: NavController,
    productModelView: ProductModelView = hiltViewModel(),
    vmBasket: BasketModelView = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        productModelView.loadProductById(idProduct)
        vmBasket.loadBasket()
        vmBasket.getInBasket(idProduct)
    }

    val product = productModelView.getProduct().observeAsState()

    if (product.value != null) {
        val productValue = product.value!!
        Column {
            val state = rememberPagerState(pageCount = { productValue.images?.size?: 1 })
            Box {
                HorizontalPager(
                    state = state, modifier = Modifier
                        .height(280.dp)
                        .fillMaxWidth()
                ) { page ->

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(contentAlignment = Alignment.BottomCenter) {

                            val images = remember { mutableListOf<ImageBitmap>() }
                                product.value!!.images?.forEach {
                                    images.add(BitmapFactory.decodeByteArray(it!!.value, 0, it.value.size).asImageBitmap())
                                }
                            if(images.size > 0) {
                                Image(
                                    bitmap = images[page], contentDescription = "", Modifier
                                        .fillMaxSize(), contentScale = ContentScale.Crop
                                )
                            }
                            else {
                                Image(
                                    painter = painterResource(id = R.drawable.no_fof), contentDescription = "", Modifier
                                        .fillMaxSize(), contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                }
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 254.dp),
                    shape = MaterialTheme.shapes.extraLarge,
                    colors = CardDefaults.cardColors(whiteColor)
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                modifier = Modifier.weight(1f),
                                fontSize = 22.sp,
                                color = Color.Black,
                                text = productValue.name,
                                fontFamily = FontFamily(
                                    Font(
                                        R.font.ag_cooper_cyr,
                                        FontWeight.Medium
                                    )
                                )
                            )

                            Text(
                                fontSize = 22.sp,
                                color = redBlackColor,
                                text = "${productValue.price} р.",
                                fontFamily = FontFamily(
                                    Font(
                                        R.font.ag_cooper_cyr,
                                        FontWeight.Medium
                                    )
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        productValue.description?.let {
                            Text(
                                fontSize = 18.sp,
                                color = grayColor_Text,
                                maxLines = 12,
                                text = it//"это горячее блюдо, обычно состоящее из котлеты из измельчённого мяса, как правило, говядины, помещённой внутрь нарезанной булочки. Гамбургеры часто подают с сыром, салатом, помидорами, луком, маринованными огурцами, беконом или чили; соусами, такими как кетчуп, горчица, майонез, релиш; и часто их кладут на булочки с кунжутом.",
                            )
                        }
                    }
                }
                FloatingActionButton(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .clip(MaterialTheme.shapes.small)
                        .padding(top = 8.dp, start = 10.dp)
                        .size(45.dp),
                    containerColor = whiteColor,
                    onClick = { navController.navigateUp() }) {
                    Icon(
                        Icons.Filled.KeyboardArrowLeft,
                        tint = redActionColor,
                        modifier = Modifier.size(32.dp),
                        contentDescription = "Добавить"
                    )
                }
                FloatingActionButton(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .clip(MaterialTheme.shapes.small)
                        .padding(top = 8.dp, end = 10.dp)
                        .size(45.dp),
                    containerColor = redActionColor,
                    onClick = {  }) {
                    Icon(
                        Icons.Filled.FavoriteBorder,
                        tint = whiteColor,
                        modifier = Modifier.size(32.dp),
                        contentDescription = "Добавить"
                    )
                }

                BottomAppBar(
                    containerColor = whiteColor,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .shadow(20.dp)
                        .height(60.dp)
                ) {
                    CreateBottomView(productValue, vmBasket, idOrg)
                }
            }
        }
    }
}

@Composable
fun CreateBottomView(productValue: Product, vmBasket: BasketModelView, idOrg: Long) {

    val productBasketCount = vmBasket.isInBasket().observeAsState()

    if (productBasketCount.value != 0) {
        Row(
            modifier = Modifier
                .background(redActionColor)
                .clip(RoundedCornerShape(0.dp))
                .height(55.dp),
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .clickable {
                            if (productBasketCount.value != 1) {
                                vmBasket.minusProduct(productValue)
                                vmBasket.setCountInProducts(productBasketCount.value!! - 1)
                            }
                        }
                        .size(24.dp),
                    painter = painterResource(id = R.drawable.minus_111123),
                    tint = whiteColor,
                    contentDescription = "add_i_product"
                )
                Text(
                    text = productBasketCount.value.toString(),
                    fontSize = 18.sp,
                    color = whiteColor,
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                )
                Icon(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .clickable {
                            if (productBasketCount.value != 99) {
                                vmBasket.plusProduct(productValue)
                                vmBasket.setCountInProducts(productBasketCount.value!! + 1)
                            }
                        }
                        .size(24.dp),
                    imageVector = Icons.Filled.Add,
                    tint = whiteColor,
                    contentDescription = "add_i_product"
                )
            }

            Button(shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 12.dp)
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(whiteColor),
                onClick = {
                    vmBasket.deleteProduct(productValue)
                    vmBasket.setCountInProducts(0)
                }) {
                Text(
                    text = "Удалить",
                    fontSize = 14.sp,
                    color = redBlackColor,
                    textAlign = TextAlign.Center
                )

            }
        }
    } else {
        val openAlert = remember { mutableStateOf(false) }
        if(openAlert.value){
            ConfirmClearCartDialog(
                onDismiss = { openAlert.value = false },
                onConfirm = { vmBasket.replaceAll(idOrg, productValue) })
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(redActionColor)
                .clip(RoundedCornerShape(0.dp))
                .clickable {
                    if (vmBasket.basketItem.value!!.idRestoraunt == null || vmBasket.basketItem.value!!.idRestoraunt == idOrg) {
                        vmBasket.addProduct(idOrg, productValue)
                        vmBasket.setCountInProducts(1)
                    } else {
                        openAlert.value = true
                    }
                }
                .height(55.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .size(32.dp),
                imageVector = Icons.Filled.Add,
                tint = whiteColor,
                contentDescription = "add_i_product"
            )
        }
    }
}

@Composable
fun ConfirmClearCartDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onConfirm()
                onDismiss()
            }) {
                Text("Очистить корзину")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Оставить как есть")
            }
        },
        title = {
            Text(text = "Очистить корзину?", fontSize = 20.sp)
        },
        text = {
            Text("В один заказ можно добалять блюда одного ресторана. Можете очистить корзину или заказать это блюдо следующим заказом")
        },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    )
}

@Composable
@Preview(showSystemUi = true)
fun previewProduct(){
}