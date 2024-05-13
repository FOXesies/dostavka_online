package com.wayplaner.learn_room


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wayplaner.learn_room.navigation.CustomAppBar
import com.wayplaner.learn_room.navigation.DrawerMenu
import com.wayplaner.learn_room.navigation.Navigation
import com.wayplaner.learn_room.ui.theme.Learn_roomTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivityPage: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Learn_roomTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //MainNavigation()
                    Navigation()
                }
            }
        }
    }
}

enum class MainRoute(value: String) {
    Home("home"),
    Organization("organization"),
    Product("product"),

    Basket("basket"),
    CreateOrder("createorder"),
    Orders("orders"),

    Settings("settings"),

    //ADMIN
    Admin_BasicInfo("admin_basic_info"),
    Admin_MenuProduct("admin_menu_product")
}

@Composable
fun DrawerContent(
    menus: Array<DrawerMenu>,
    onMenuClick: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(150.dp),
                imageVector = Icons.Filled.AccountCircle,
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        menus.forEach {
            NavigationDrawerItem(
                label = { Text(text = it.title) },
                icon = { Icon(imageVector = it.icon, contentDescription = null) },
                selected = false,
                onClick = {
                    onMenuClick(it.route)
                }
            )
        }
    }
}

@Composable
fun SettingsScreen(drawerState: DrawerState) {
    Scaffold(
        topBar = { CustomAppBar(drawerState = drawerState, title = "Settings") }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = " Settings Screen")
        }
    }
}

@Preview(widthDp = 300)
@Composable
fun PreviewCustomAppBar() {
    Learn_roomTheme {
        CustomAppBar(drawerState = null, title = "Title")
    }
}
