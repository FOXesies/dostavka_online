package com.wayplaner.learn_room.product.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wayplaner.learn_room.R
import com.wayplaner.learn_room.basket.presentation.BasketModelView
import com.wayplaner.learn_room.product.domain.model.Product
import com.wayplaner.learn_room.ui.theme.grayColor
import com.wayplaner.learn_room.ui.theme.grayColor_Text
import com.wayplaner.learn_room.ui.theme.no_pickRedColor
import com.wayplaner.learn_room.ui.theme.redActionColor
import com.wayplaner.learn_room.ui.theme.redBlackColor
import com.wayplaner.learn_room.ui.theme.whiteColor

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductScreen(
    navigateUp: () -> Unit,
    productModelView: ProductModelView = hiltViewModel(),
    vmBasket: BasketModelView = hiltViewModel()

) {
    val images = listOf(painterResource(id = R.drawable.burger), (painterResource(id = R.drawable.burger_2)))

    val product = productModelView.getProduct().observeAsState()

    if (product.value != null) {
        val productValue = product.value!!
        Column {
            val state = rememberPagerState(pageCount = { 2 })
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
                            Image(
                                painter = images[page], contentDescription = "", Modifier
                                    .fillMaxSize(), contentScale = ContentScale.Crop
                            )
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
                    onClick = { navigateUp() }) {
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
                    CreateBottomView(productValue, vmBasket)
                }
            }
        }
    }
}

@Composable
fun CreateBottomView(productValue: Product, vmBasket: BasketModelView) {
    val countCurCroduct = remember {
        mutableIntStateOf(1)
    }

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 20.dp),
        verticalAlignment = Alignment.CenterVertically) {
        IconButton(modifier = Modifier
            .clip(CircleShape)
            .size(32.dp),
            colors = IconButtonDefaults.filledIconButtonColors(grayColor),
            onClick = {if(countCurCroduct.intValue != 1) countCurCroduct.intValue--}) {
            Icon(
                modifier = Modifier
                    .padding(3.dp),
                painter = painterResource(id = R.drawable.minus_111123),
                tint = whiteColor,
                contentDescription = "add_i_product"
            )
        }

        Text(
            text = countCurCroduct.intValue.toString(),
            fontSize = 18.sp,
            modifier = Modifier.padding(start = 10.dp, end = 10.dp)
        )

        IconButton(modifier = Modifier
            .clip(shape = RoundedCornerShape(10.dp))
            .size(32.dp),
            colors = IconButtonDefaults.filledIconButtonColors(redBlackColor),
            onClick = {if(countCurCroduct.intValue != 99) countCurCroduct.intValue++}) {
            Icon(
                imageVector = Icons.Filled.Add,
                tint = whiteColor,
                contentDescription = "add_i_product"
            )
        }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End) {

            Text(
                text = "${productValue.price!! * countCurCroduct.intValue} р.",
                fontSize = 18.sp,
            )

            Spacer(modifier = Modifier.width(15.dp))

            TextButton(
                colors = ButtonDefaults.buttonColors(redBlackColor),
                onClick = { vmBasket.addProduct(productValue.idProduct!!) })
            {
                Text(
                    text = "В корзину",
                    fontSize = 16.sp,
                    color = whiteColor
                )
            }

/*            Icon(
                modifier = Modifier
                    .padding(2.dp),
                painter = painterResource(id = R.drawable.add_bascket),
                tint = redActionColor,
                contentDescription = "add_i_product"
            )*/
        }
    }
}

@Composable
fun DotsIndicator(
    totalDots: Int,
    selectedIndex: Int
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(bottom = 15.dp), horizontalArrangement = Arrangement.Center
    ) {

        items(totalDots) { index ->
            if (index == selectedIndex) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(color = redBlackColor)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(color = no_pickRedColor)
                )
            }

            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun previewProduct(){
    //ProductScreen()
}