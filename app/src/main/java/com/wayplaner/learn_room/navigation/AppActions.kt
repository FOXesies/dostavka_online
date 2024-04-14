package com.wayplaner.learn_room.navigation

import androidx.navigation.NavController

class AppActions(
    private val navController: NavController,
    private val routes: AppDestinations
) {

    val navigateHome: () -> Unit = {
        navController.navigate(routes.HOME_ROUTE)
    }

    val navigateToSelectedOrganization: (Long) -> Unit = {organizationId: Long ->
        navController.navigate("${routes.ORGANIZATION_ROUTE}/$organizationId")
    }

    val navigateToSelectedProduct: (Long) -> Unit = {productId: Long ->
        navController.navigate("${routes.PRODUCT_ROUTE}/$productId")
    }

    val navigateUp: () -> Unit = {
        navController.navigateUp()
    }
}