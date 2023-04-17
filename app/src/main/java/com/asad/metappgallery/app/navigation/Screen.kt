package com.asad.metappgallery.app.navigation

sealed class Screen(val route: String) {

    open fun createRoute(vararg args: String): String {
        return route
    }

    object GalleryFinderScreen : Screen(route = ScreenName.GalleryFinderScreen)

    object GalleryDetailScreen :
        Screen(route = "${ScreenName.GalleryDetailScreen}/{${NavArgs.GalleryObjectId}}") {
        override fun createRoute(vararg args: String): String {
            return "${ScreenName.GalleryDetailScreen}/${args[0]}"
        }
    }
}
