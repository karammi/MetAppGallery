package com.asad.metappgallery.app.navigation

sealed class Screen(val route: String) {

    open fun createRoute(vararg args: String): String {
        return route
    }

    object SearchScreen : Screen(route = ScreenName.SearchScreen)

    object DetailScreen :
        Screen(route = "${ScreenName.DetailScreen}/{${NavArgs.ObjectId}}") {
        override fun createRoute(vararg args: String): String {
            return "${ScreenName.DetailScreen}/${args[0]}"
        }
    }
}
