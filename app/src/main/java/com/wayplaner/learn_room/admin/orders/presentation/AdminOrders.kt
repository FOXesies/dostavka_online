package com.wayplaner.learn_room.admin.orders.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
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
import com.wayplaner.learn_room.R
import com.wayplaner.learn_room.admin.orders.presentation.components.ActiveOrdersAdmin
import com.wayplaner.learn_room.admin.orders.presentation.components.CanceledOrdersAdmin
import com.wayplaner.learn_room.admin.orders.presentation.components.CompleteOrdersAdmin
import com.wayplaner.learn_room.ui.theme.backHeader
import com.wayplaner.learn_room.ui.theme.grayList
import com.wayplaner.learn_room.ui.theme.orderCreateCard
import com.wayplaner.learn_room.ui.theme.redActionColor
import com.wayplaner.learn_room.ui.theme.whiteColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AdminOrders(navController: NavController, vmListorder: AdminOrdersModelView = hiltViewModel()) {

    Scaffold(
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            val typesOrder = listOf("Активные", "Завершённые", "Отмененые")
            val pagerState = rememberPagerState { 3 }
            val coroutineScope = rememberCoroutineScope()
            Column() {
                Box(modifier = Modifier.fillMaxWidth().background(backHeader)){
                    Text(modifier = Modifier
                        .fillMaxWidth().padding(top = 18.dp),
                        text = "Список заказов", fontSize = 22.sp,
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
                            .align(Alignment.TopStart)
                            .clip(MaterialTheme.shapes.small)
                            .padding(top = 8.dp, start = 10.dp, end = 5.dp, bottom = 5.dp)
                            .size(45.dp),
                        containerColor = whiteColor,
                        onClick = { navController.navigateUp() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            tint = redActionColor,
                            modifier = Modifier.size(32.dp),
                            contentDescription = null
                        )
                    }
                }

                ScrollableTabRow(
                    modifier = Modifier.fillMaxWidth().padding(0.dp),
                    selectedTabIndex = pagerState.currentPage,
                    containerColor = backHeader,
                    contentColor = backHeader,
                    indicator = {
                        Spacer(
                            Modifier
                                .tabIndicatorOffset(it[pagerState.currentPage])
                                .height(2.5.dp)
                                .background(redActionColor)
                        )
                    }) {
                    typesOrder.forEachIndexed { index, tab ->
                        Tab(modifier = Modifier.fillMaxWidth().weight(1f),
                            selected = pagerState.currentPage == index,
                            onClick = {
                                coroutineScope.launch { pagerState.animateScrollToPage(index) }
                            }) {
                            Text(
                                color = if (pagerState.currentPage == index) redActionColor else grayList,
                                text = tab,
                                modifier = Modifier.padding(14.dp)
                            )
                        }
                    }
                }

                HorizontalPager(
                    verticalAlignment = Alignment.Top,
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(orderCreateCard)
                ) {
                    when (pagerState.currentPage) {
                        0 -> ActiveOrdersAdmin(navController, vmListorder)
                        1 -> CompleteOrdersAdmin(vmListorder, navController)
                        2 -> CanceledOrdersAdmin(vmListorder, navController)
                    }

                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun MenuBasic_f(){
    //MenuBasic()
}