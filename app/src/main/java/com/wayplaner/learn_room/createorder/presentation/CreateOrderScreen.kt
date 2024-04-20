package com.wayplaner.learn_room.createorder.presentation

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import com.wayplaner.learn_room.R
import com.wayplaner.learn_room.basket.presentation.ProductList
import com.wayplaner.learn_room.organization.presentation.ProductCard
import com.wayplaner.learn_room.createorder.presentation.components.DeliveryPick
import com.wayplaner.learn_room.createorder.presentation.components.SelfDeliveryPick
import com.wayplaner.learn_room.ui.theme.categoryColor
import com.wayplaner.learn_room.ui.theme.grayColor_Text
import com.wayplaner.learn_room.ui.theme.gray_light
import com.wayplaner.learn_room.ui.theme.headphoneColor
import com.wayplaner.learn_room.ui.theme.redActionColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CreateOrderScreen(navigateUp:() -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxSize(),
        shape = RoundedCornerShape(20.dp)
    ) {
        val picking = listOf("Доставка", "Самовывоз")
        var scope = rememberCoroutineScope()
        val pagerState = rememberPagerState {
            2
        }
        Box(contentAlignment = Alignment.BottomCenter) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Card(
                    colors = CardDefaults.cardColors(Color.White),
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(10.dp),
                    shape = RoundedCornerShape(20.dp)
                )
                {
                    TabRow(
                        modifier = Modifier.wrapContentHeight(),
                        selectedTabIndex = pagerState.currentPage,
                        containerColor = Color.Transparent,
                        contentColor = Color(0xFFFEFEFA),
                        indicator = {
                            Spacer(
                                Modifier
                                    .tabIndicatorOffset(it[pagerState.currentPage])
                                    .height(2.5.dp)
                                    .background(redActionColor)
                            )
                        }
                    ) {
                        picking.forEachIndexed { index, tab ->
                            Tab(
                                selectedContentColor = headphoneColor,
                                onClick = {
                                    scope.launch {
                                        pagerState.animateScrollToPage(index)
                                    }
                                },
                                selected = index == pagerState.currentPage,
                            ) {
                                Image(
                                    modifier = Modifier
                                        .padding(top = 6.dp)
                                        .size(25.dp),
                                    painter =
                                    if (index == 1) {
                                        if (pagerState.currentPage == index) painterResource(id = (R.drawable.focus_selfdelivery))
                                        else painterResource(id = R.drawable.unfocus_selfdelivery)
                                    } else {
                                        if (pagerState.currentPage == index) painterResource(id = (R.drawable.focus_delivery))
                                        else painterResource(id = R.drawable.unfocus_delivery)
                                    },
                                    contentDescription = null
                                )
                                Text(
                                    color = if (pagerState.currentPage == index) redActionColor else categoryColor,
                                    text = tab,
                                    fontSize = 15.sp,
                                    modifier = Modifier.padding(bottom = 8.dp, top = 2.dp)
                                )
                            }
                        }
                    }

                }

                HorizontalPager(
                    state = pagerState,
                    beyondBoundsPageCount = 2,
                ) { page ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 110.dp)
                            .clip(RoundedCornerShape(10.dp))
                    ) {
                        if(page == 0)
                            DeliveryPick()
                        else
                            SelfDeliveryPick()
                    }
                }

            }

            BottomPayCard()
        }
    }
}

@Composable
private fun BottomPayCard(){

        Card(modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(20.dp),
            colors = CardDefaults.cardColors(Color.White),
            shape = RoundedCornerShape(
                topEnd = 30.dp,
                topStart = 30.dp,
                bottomEnd = 0.dp,
                bottomStart = 0.dp
            )
        ) {
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                Row {
                    Column(modifier = Modifier.padding(top = 18.dp)) {
                        Text(text = "1490 руб.", fontSize = 17.sp, fontWeight = FontWeight.Bold)
                        Text(text = "с доставкой", fontSize = 12.sp)
                    }

                    Spacer(modifier = Modifier.width(20.dp))

                    Button(onClick = { /*TODO*/ },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(redActionColor),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 14.dp, bottom = 14.dp)
                            .size(50.dp)
                    ){
                        Text(text = "Оплатить")
                    }
                }
            }
        }
}

@Composable
@Preview
fun previewcreateor(){
}