package com.wayplaner.learn_room.home.presentation.components
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wayplaner.learn_room.R
import com.wayplaner.learn_room.home.presentation.MainModelView
import com.wayplaner.learn_room.ui.theme.backHeader
import com.wayplaner.learn_room.ui.theme.backHome
import com.wayplaner.learn_room.ui.theme.grayList
import com.wayplaner.learn_room.ui.theme.whiteColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarHome(drawerState: DrawerState?, homeViewModel: MainModelView) {
    val coroutineScope = rememberCoroutineScope()

    TopAppBar(
        modifier = Modifier.height(55.dp),
        title = {
            val cities = homeViewModel.getCity().value
            DropDownCity(cities!!, homeViewModel)
                },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = backHeader,
            titleContentColor = whiteColor,
        ),
        navigationIcon = {
            if (drawerState != null) {
                IconButton(modifier = Modifier.padding(start = 5.dp, top = 8.dp).size(40.dp), onClick = {
                    coroutineScope.launch {
                        drawerState.open()
                    }
                }) {
                    Icon(painterResource(id = R.drawable.hamburger_button_menu_icon_155296), tint = whiteColor, contentDescription = "")
                }
            }
        },
        actions = {
            IconButton(modifier = Modifier.padding(end = 6.dp, top = 10.dp).size(33.dp), onClick = { /*navigateToBasket(1)*/ }) {
                Icon(painterResource(id = R.drawable.bag_buy_cart_market_shop_shopping_tote_icon_123191), tint = whiteColor, contentDescription = "")

            }
        }
    )
}

@Composable
fun DropDownCity(state: List<String>, homeViewModel: MainModelView){
    var expanded by remember { mutableStateOf(false) }
    val selectCity = homeViewModel.selectedText.observeAsState()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        // Back arrow here
        Row(
            Modifier.padding(top = 5.dp)
                .clickable { // Anchor view
                    expanded = !expanded
                }) { // A
            // nchor view
            Text(text = selectCity.value!!, fontSize = 14.sp) // City name label
            Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null)
            DropdownMenu(modifier = Modifier.background(backHome),
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }) {
                state.forEach { city ->

                    val isSelected = city == selectCity.value!!
                    val style = if (isSelected) {
                        MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = whiteColor
                        )
                    } else {
                        MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Normal,
                            color = grayList
                        )
                    }

                    DropdownMenuItem(
                        text =  { Text(city, style = style) },
                        onClick = {
                            expanded = false
                            homeViewModel.selectedText.postValue(city)
                            homeViewModel.getOrganizationsByCity(city)
                        }
                    )
                }
                //Text("Popup content \nhere", Modifier.padding(24.dp))
            }
        }
    }
}
