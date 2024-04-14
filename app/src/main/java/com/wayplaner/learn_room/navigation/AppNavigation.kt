package com.wayplaner.learn_room.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.wayplaner.learn_room.presentation.home.HomeScreen
import com.wayplaner.learn_room.presentation.organization.OrganizationCardOrg
import com.wayplaner.learn_room.presentation.product.ProductScreen

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
                navigateToOrganization = actions.navigateToSelectedOrganization
            )
        }

        composable(
            route = "${AppDestinations.ORGANIZATION_ROUTE}/{${routes.ORGANIZATION_ID_KEY}}",
            arguments = listOf(
                navArgument(routes.ORGANIZATION_ID_KEY){
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
            route = "${AppDestinations.PRODUCT_ROUTE}/{${routes.PRODUCT_ID_KEY}}",
            arguments = listOf(
                navArgument(routes.PRODUCT_ID_KEY){
                    type = NavType.IntType
                }
            )
        ){
            ProductScreen(navigateUp = actions.navigateUp)
        }

    }

}