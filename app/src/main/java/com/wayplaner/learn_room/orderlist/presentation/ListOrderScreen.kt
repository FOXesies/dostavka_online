package com.wayplaner.learn_room.orderlist.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.wayplaner.learn_room.navigation.CustomAppBar
import com.wayplaner.learn_room.orderlist.presentation.components.ActiveOrders
import com.wayplaner.learn_room.orderlist.presentation.components.CanceledOrders
import com.wayplaner.learn_room.orderlist.presentation.components.CompleteOrders
import com.wayplaner.learn_room.ui.theme.categoryColor
import com.wayplaner.learn_room.ui.theme.lightGrayColor
import com.wayplaner.learn_room.ui.theme.redActionColor

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListOrderScreen(navController: NavController,
                    drawerState: DrawerState,
                    vmListorder: ListOrderModelView = hiltViewModel()) {
    Scaffold(
        topBar = { CustomAppBar(drawerState = drawerState, title = "Список заказов") }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            val typesOrder = listOf("Активные", "Завершённые", "Отмененые")
            var selectedType by remember { mutableStateOf(typesOrder[0]) }
            var selectedIndex by remember { mutableIntStateOf(0) }
            val pagerState = rememberPagerState { 3 }
            Column() {
                ScrollableTabRow(
                    modifier = Modifier.fillMaxWidth(),
                    selectedTabIndex = selectedIndex,
                    containerColor = Color.Transparent,
                    contentColor = Color(0xFFFEFEFA),
                    indicator = {
                        Spacer(
                            Modifier
                                .tabIndicatorOffset(it[selectedIndex])
                                .height(2.5.dp)
                                .background(redActionColor)
                        )
                    }) {
                    typesOrder.forEachIndexed { index, tab ->
                        Tab(modifier = Modifier.fillMaxWidth().weight(1f),
                            selected = selectedIndex == index,
                            onClick = {
                                selectedIndex = index
                                selectedType = tab
                            }) {
                            Text(
                                color = if (selectedIndex == index) redActionColor else categoryColor,
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
                        .background(lightGrayColor)
                ) { index ->
                    when (selectedIndex) {
                        0 -> ActiveOrders(vmListorder)
                        1 -> CompleteOrders()
                        2 -> CanceledOrders(vmListorder)
                    }

                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun preciewList(){
    //ListOrderScreen()
}