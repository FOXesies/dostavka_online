package com.wayplaner.learn_room.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.HomeRepairService
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
import com.wayplaner.learn_room.admin.infoorder.presintation.InfoOrderAdmin
import com.wayplaner.learn_room.admin.menu.presentation.MenuAddScreen
import com.wayplaner.learn_room.admin.menu.presentation.MenuList
import com.wayplaner.learn_room.admin.orders.presentation.AdminOrders
import com.wayplaner.learn_room.admin.util.AdminAccount
import com.wayplaner.learn_room.auth.presentation.LoginComponents
import com.wayplaner.learn_room.auth.presentation.LoginOrgComponents
import com.wayplaner.learn_room.auth.presentation.RegisterOrgScreen
import com.wayplaner.learn_room.auth.presentation.RegisterScreen
import com.wayplaner.learn_room.basket.presentation.BasketScreen
import com.wayplaner.learn_room.createorder.presentation.CreateOrderScreen
import com.wayplaner.learn_room.favotitea.presentations.FavoritesScreen
import com.wayplaner.learn_room.feedbasks.presentation.ReviewsScreen
import com.wayplaner.learn_room.home.presentation.HomeScreen
import com.wayplaner.learn_room.order_info.presentation.InfoOrderUser
import com.wayplaner.learn_room.orderlist.presentation.ListOrderScreen
import com.wayplaner.learn_room.organization.presentation.OrganizationCardOrg
import com.wayplaner.learn_room.product.presentation.ProductScreen
import com.wayplaner.learn_room.settings.presentation.EditProfileScreen
import com.wayplaner.learn_room.ui.theme.backHome
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
        DrawerMenu(Icons.Filled.Favorite, "Любимое", MainRoute.Loveli.name),
        DrawerMenu(Icons.Filled.ShoppingBasket, "Корзина", MainRoute.Basket.name),
        DrawerMenu(ImageVector.vectorResource(R.drawable.checklist_list_orderlist_order_icon_219982), "Заказы", MainRoute.Orders.name),
        DrawerMenu(Icons.Filled.Settings, "Нстройка", MainRoute.Settings.name),
        DrawerMenu(Icons.Filled.HomeRepairService, "Для ресторана", MainRoute.Admin_Home.name)
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = backHome
            ) {
                DrawerContent(menus) { route ->
                    coroutineScope.launch {
                        drawerState.close()
                    }

                    navController.navigate(route)
                }
            }
        }
    ) {
        NavHost(navController = navController,
            startDestination = if(CustomerAccount.info != null) MainRoute.Home.name
            else if(AdminAccount.idOrg != null) MainRoute.Admin_Home.name
        else MainRoute.LoginCustomer.name) {
            composable(route = MainRoute.Home.name
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
            composable(MainRoute.OrdersInfo.name + "/{id}") { navBackStack ->
                val id = navBackStack.arguments?.getString("id")
                InfoOrderUser(id!!.toLong(), navController)
            }
            composable(MainRoute.Settings.name) {
                SettingsScreen(drawerState)
            }
            composable(MainRoute.Loveli.name) {
                FavoritesScreen(navController, drawerState)
            }
            composable(MainRoute.Organization.name + "/{city}/{id}") { navBackStack ->

                // Extracting the argument
                val city = navBackStack.arguments?.getString("city")
                val id = navBackStack.arguments?.getString("id")
                OrganizationCardOrg(id!!.toLong(), city!!, navController)
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
            composable(MainRoute.Settings.name) {
                EditProfileScreen(navController)
            }

            //Admin
            composable(MainRoute.Admin_Register.name) {
                RegisterOrgScreen(navController)
            }
            composable(MainRoute.Admin_Login.name) {
                LoginOrgComponents(navController)
            }
            composable(MainRoute.Admin_BasicInfo.name) {
                BasicInfo(navController)
            }
            composable(MainRoute.Admin_Orders.name) {
                AdminOrders(navController)
            }
            composable(MainRoute.Admin_Home.name) {
                com.wayplaner.learn_room.admin.home.presentation.HomeScreen(navController)
            }
            composable(MainRoute.Admin_OrderInfo.name + "/{id}") { navBackStack ->
                val id = navBackStack.arguments?.getString("id")
                InfoOrderAdmin(id!!.toLong(), navController)
            }
            composable(MainRoute.Admin_MenuList.name) {
                MenuList(navController)
            }
            composable(MainRoute.FeedBacks.name + "/{id}") {navBackStack ->
                val id = navBackStack.arguments?.getString("id")
                ReviewsScreen(id!!.toLong(), navController)
            }
            composable(MainRoute.Admin_MenuProduct.name + "/{id}/{category}") {navBackStack ->
                val idProduct = navBackStack.arguments?.getString("id")
                val category = navBackStack.arguments?.getString("category")
                MenuAddScreen(idProduct!!.toLong(), category, navController)
            }
        }
    }
}