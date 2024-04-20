package com.wayplaner.learn_room.utils.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.wayplaner.learn_room.basket.presentation.BasketScreen
import com.wayplaner.learn_room.createorder.presentation.CreateOrderScreen
import com.wayplaner.learn_room.home.presentation.HomeScreen
import com.wayplaner.learn_room.organization.presentation.OrganizationCardOrg
import com.wayplaner.learn_room.product.presentation.ProductScreen

@Composable
fun AppNavigation(
    startDestination: String = AppDestinations.HOME_ROUTE,
    routes: AppDestinations = AppDestinations
) {

    val navController = rememberNavController()
    val actions = remember(navController) {
        AppActions(navController, routes)
    }
    
    NavHost(navController = navController, startDestination = startDestination){

        composable(
            AppDestinations.HOME_ROUTE
        ){
            HomeScreen(
                navigateToOrganization = actions.navigateToSelectedOrganization,
                navigateToBasket = actions.navigateCBasketr
            )
        }

        composable(
            route = "${AppDestinations.ORGANIZATION_ROUTE}/{${AppDestinations.ORGANIZATION_ID_KEY}}",
            arguments = listOf(
                navArgument(AppDestinations.ORGANIZATION_ID_KEY){
                    type = NavType.IntType
                }
            )
        ){
            OrganizationCardOrg(
                navigateUp = actions.navigateUp,
                navigateToSelectedProduct = actions.navigateToSelectedProduct
            )
        }

        composable(
            route = "${AppDestinations.PRODUCT_ROUTE}/{${AppDestinations.PRODUCT_ID_KEY}}",
            arguments = listOf(
                navArgument(AppDestinations.PRODUCT_ID_KEY){
                    type = NavType.IntType
                }
            )
        ){
            ProductScreen(navigateUp = actions.navigateUp)
        }

        composable(AppDestinations.BASKET_ROUTE){
            BasketScreen(navigateUp = actions.navigateUp, navigateCreateOrder = actions.navigateToCreateOrder)
        }

        composable(AppDestinations.CREATE_ORDER){
            CreateOrderScreen(navigateUp = actions.navigateUp)
        }


    }

}