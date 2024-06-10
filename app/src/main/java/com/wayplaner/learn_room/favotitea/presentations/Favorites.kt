package com.wayplaner.learn_room.favotitea.presentations

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.wayplaner.learn_room.MainRoute
import com.wayplaner.learn_room.home.presentation.components.Organization
import com.wayplaner.learn_room.navigation.CustomAppBar
import com.wayplaner.learn_room.organization.presentation.components.ProductCard
import com.wayplaner.learn_room.ui.theme.backHeader
import com.wayplaner.learn_room.ui.theme.grayList
import com.wayplaner.learn_room.ui.theme.orderCreateCard
import com.wayplaner.learn_room.ui.theme.redActionColor
import com.wayplaner.learn_room.ui.theme.whiteColor
import com.wayplaner.learn_room.utils.CustomerAccount
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoritesScreen(navController: NavController, drawerState: DrawerState, viewModel: FavoriteModelView = hiltViewModel()) {
    Scaffold(
        topBar = { CustomAppBar(drawerState = drawerState, title = "Любимое") }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            val typesOrder = listOf("Рестораны", "блюда")
            val pagerState = rememberPagerState { 2 }
            val coroutineScope = rememberCoroutineScope()
            Column {
                ScrollableTabRow(
                    modifier = Modifier.fillMaxWidth(),
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
                        Tab(modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
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

                val orgs = viewModel.all_organizations.observeAsState()
                val products = viewModel.all_products.observeAsState()

                HorizontalPager(
                    verticalAlignment = Alignment.Top,
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(orderCreateCard)
                ) {
                    when (pagerState.currentPage) {
                        1 ->
                        {
                            if(!products.value.isNullOrEmpty()) {
                                LazyColumn {
                                    items(products.value!!){
                                        ProductCard(product = it , orgId = 0, navController = navController)
                                    }
                                }
                            }
                            else{
                                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                                    Text(text = "Пусто", color = whiteColor, fontSize = 16.sp)
                                }
                            }
                        }
                        0 -> {
                            if(!orgs.value.isNullOrEmpty()) {
                                LazyColumn(modifier = Modifier.padding(vertical = 20.dp)) {
                                    items(orgs.value!!){

                                        Organization(navController = navController, organization = it, route = "${MainRoute.Organization.name}/${CustomerAccount.info!!.city}/${it.idOrganization}")
                                    }
                                }
                            }
                            else{
                                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                                    Text(text = "Пусто", color = whiteColor, fontSize = 16.sp)
                                }
                            }
                        }
                    }

                }
            }
        }
    }
}