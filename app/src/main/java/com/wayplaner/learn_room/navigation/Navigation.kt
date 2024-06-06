package com.wayplaner.learn_room.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wayplaner.learn_room.DrawerContent
import com.wayplaner.learn_room.MainRoute
import com.wayplaner.learn_room.R
import com.wayplaner.learn_room.SettingsScreen
import com.wayplaner.learn_room.admin.basic_info.presentation.BasicInfo
import com.wayplaner.learn_room.admin.menu.presentation.MenuAddScreen
import com.wayplaner.learn_room.admin.menu.presentation.MenuList
import com.wayplaner.learn_room.admin.orders.presentation.AdminOrders
import com.wayplaner.learn_room.auth.presentation.LoginComponents
import com.wayplaner.learn_room.auth.presentation.RegisterScreen
import com.wayplaner.learn_room.basket.presentation.BasketScreen
import com.wayplaner.learn_room.createorder.presentation.CreateOrderScreen
import com.wayplaner.learn_room.home.presentation.HomeScreen
import com.wayplaner.learn_room.orderlist.presentation.ListOrderScreen
import com.wayplaner.learn_room.organization.presentation.OrganizationCardOrg
import com.wayplaner.learn_room.product.presentation.ProductScreen
import com.wayplaner.learn_room.utils.CustomerAccount
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
) {
    val menus = arrayOf(
        DrawerMenu(Icons.Filled.Face, "Главная", MainRoute.Home.name),
        DrawerMenu(Icons.Filled.ShoppingBasket, "Корзина", MainRoute.Basket.name),
        DrawerMenu(ImageVector.vectorResource(R.drawable.checklist_list_orderlist_order_icon_219982), "Заказы", MainRoute.Orders.name),
        DrawerMenu(Icons.Filled.Settings, "Settings", MainRoute.Admin_Home.name)
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerContent(menus) { route ->
                    coroutineScope.launch {
                        drawerState.close()
                    }

                    navController.navigate(route)
                }
            }
        }
    ) {
        NavHost(navController = navController, startDestination = if (CustomerAccount.info == null) MainRoute.LoginCustomer.name else MainRoute.Home.name) {
            composable(route = MainRoute.Home.name,
                enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn() },
                exitTransition = {slideOutHorizontally(targetOffsetX = { -1000 }) + fadeOut() }
                ) {
                HomeScreen(drawerState, navController)
            }
            composable(MainRoute.LoginCustomer.name,
                enterTransition = { fadeIn() },
                exitTransition = {fadeOut()}) {
                LoginComponents(navController)
            }
            composable(MainRoute.RegisterCustomer.name,
                enterTransition = { fadeIn() },
                exitTransition = { fadeOut() }) {
                RegisterScreen(navController)
            }
            composable(MainRoute.Basket.name) {
                BasketScreen(drawerState, navController)
            }
            composable(MainRoute.Settings.name) {
                SettingsScreen(drawerState)
            }
            composable(MainRoute.Organization.name + "/{id}") { navBackStack ->

                // Extracting the argument
                val counter = navBackStack.arguments?.getString("id")
                OrganizationCardOrg(counter!!.toLong(), navController)
            }
            composable(MainRoute.Product.name + "/{idOrd}/{idProduct}") { navBackStack ->

                // Extracting the argument
                val idOrg = navBackStack.arguments?.getString("idOrd")
                val idProduct = navBackStack.arguments?.getString("idProduct")
                ProductScreen(idOrg!!.toLong(), idProduct!!.toLong(), navController)
            }
            composable(MainRoute.CreateOrder.name) {
                CreateOrderScreen(navController)
            }
            composable(MainRoute.Orders.name) {
                ListOrderScreen(navController, drawerState)
            }

            //Admin
            composable(MainRoute.Admin_BasicInfo.name) {
                BasicInfo(navController)
            }
            composable(MainRoute.Admin_Orders.name) {
                AdminOrders(navController)
            }
            composable(MainRoute.Admin_Home.name) {
                com.wayplaner.learn_room.admin.home.presentation.HomeScreen(navController)
            }
            composable(MainRoute.Admin_MenuList.name) {
                MenuList(navController)
            }
            composable(MainRoute.Admin_MenuProduct.name) {
                MenuAddScreen(navController)
            }
        }
    }
}