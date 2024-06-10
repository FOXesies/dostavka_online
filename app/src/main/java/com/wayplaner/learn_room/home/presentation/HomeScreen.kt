package com.wayplaner.learn_room.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.wayplaner.learn_room.MainRoute
import com.wayplaner.learn_room.home.domain.model.OrganizationDTO
import com.wayplaner.learn_room.home.presentation.components.CategoryList
import com.wayplaner.learn_room.home.presentation.components.Organization
import com.wayplaner.learn_room.home.presentation.components.TopBarHome
import com.wayplaner.learn_room.organization.domain.model.FiltercategoryOrg
import com.wayplaner.learn_room.ui.theme.backHeader
import com.wayplaner.learn_room.ui.theme.backHome
import com.wayplaner.learn_room.ui.theme.blackGrayColor
import com.wayplaner.learn_room.ui.theme.lightGrayColor
import com.wayplaner.learn_room.ui.theme.redActionColor
import com.wayplaner.learn_room.ui.theme.whiteColor


@Composable
fun HomeScreen(drawerState: DrawerState?,
    navController: NavController,
    homeViewModel: MainModelView = hiltViewModel()){

    val organizations = homeViewModel.getCountry().observeAsState()
    if (organizations.value != null && organizations.value!!.isNotEmpty()) {
        val city = homeViewModel.selectedText.observeAsState()
        Scaffold(modifier = Modifier.background(backHome),
            containerColor = backHeader,
            topBar = { TopBarHome(drawerState, homeViewModel, navController) })
        { innerPadding ->

            val lazyListState = rememberLazyListState()
            val filer_id = homeViewModel.filter_id.observeAsState()
            Box(
                Modifier
                    .background(backHome)
                    .fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                ) {
                    Card(
                        colors = CardDefaults.cardColors(backHeader),
                        shape = RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 0.dp,
                            bottomStart = 25.dp,
                            bottomEnd = 25.dp
                        )
                    ) {

                        val category_list = homeViewModel.categories.observeAsState().value

                        if (category_list != null)
                            CategoryList(
                                category_list,
                                category_list.map { false }.toMutableList(),
                                homeViewModel,
                                FiltercategoryOrg(city = city.value!!)
                            )
                        Spacer(Modifier.height(8.dp))
                    }

                    LazyColumn(
                        state = lazyListState,
                    ) {
                        item {
                            //Spacer(Modifier.height(10.dp))

                            //SearchView()

                            Spacer(Modifier.height(6.dp))
                        }

                        items(organizations.value!!) { organization ->
                            if (filer_id.value!!.isEmpty() || filer_id.value!!.contains(organization.idOrganization!!))
                                Organization(
                                    navController,
                                    organization,
                                    "${MainRoute.Organization.name}/${city.value}/${organization.idOrganization}"
                                )
                        }

                    }
                }

                FloatingActionButton(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .clip(MaterialTheme.shapes.small)
                        .padding(bottom = 8.dp, end = 10.dp)
                        .size(48.dp),
                    containerColor = redActionColor,
                    onClick = { navController.navigate(MainRoute.Loveli.name) }) {
                    Icon(
                        Icons.Filled.FavoriteBorder,
                        tint = whiteColor,
                        modifier = Modifier.size(32.dp),
                        contentDescription = "Добавить"
                    )
                }
            }
        }
        } else {
        Box(
            Modifier
                .background(backHome)
                .fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                trackColor = whiteColor,
                color = backHome
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView() {
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val searchResults = remember { mutableStateListOf<OrganizationDTO>() }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .padding(horizontal = 20.dp)
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(12.dp)
            )
            .background(lightGrayColor),
        contentAlignment = Alignment.Center
    ) {
        DockedSearchBar(
            modifier = Modifier.fillMaxSize(),
            colors = SearchBarDefaults.colors(Color.Transparent),
            query = query,
            onQueryChange = { query = it },
            onSearch = { active = false },
            active = active,
            onActiveChange = { active = it },
            placeholder = { Text(text = "Поиск ресторана", color = blackGrayColor) },
            leadingIcon = {
                if (active) {
                    Icon(
                        tint = blackGrayColor,
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .clickable {
                                active = false
                                query = ""
                            },
                    )
                } else {
                    Icon(
                        tint = blackGrayColor,
                        imageVector = Icons.Default.Search,
                        contentDescription = "",
                        modifier = Modifier.padding(start = 16.dp),
                    )
                }
            },
        ) {

        }
    }
}


@Preview(name = "Preview1", device = "id:pixel_xl", showSystemUi = true)
@Composable
fun Preview() {
    //HomeScreen()
}


