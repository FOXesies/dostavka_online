package com.wayplaner.learn_room.utils.navigation

import androidx.navigation.NavController

class AppActions(
    private val navController: NavController,
    private val routes: AppDestinations
) {

    val navigateHome: () -> Unit = {
        navController.navigate(AppDestinations.HOME_ROUTE)
    }

    val navigateToSelectedOrganization: (Long) -> Unit = {organizationId: Long ->
        navController.navigate("${AppDestinations.ORGANIZATION_ROUTE}/$organizationId")
    }

    val navigateToSelectedProduct: (Long) -> Unit = {productId: Long ->
        navController.navigate("${AppDestinations.PRODUCT_ROUTE}/$productId")
    }

    val navigateCBasketr: (Long) -> Unit = {
        navController.navigate(AppDestinations.BASKET_ROUTE)
    }

    val navigateToCreateOrder: () -> Unit = {
        navController.navigate(AppDestinations.CREATE_ORDER)
    }

    val navigateUp: () -> Unit = {
        navController.navigateUp()
    }
}