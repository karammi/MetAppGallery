package com.asad.metappgallery.app

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.asad.metappgallery.app.navigation.NavArgs
import com.asad.metappgallery.app.navigation.Screen
import com.asad.metappgallery.core.data.NetworkRequester
import com.asad.metappgallery.detailScreen.dataSource.adapter.ObjectModelJsonAdapter
import com.asad.metappgallery.detailScreen.dataSource.adapter.ConstituentJsonAdapter
import com.asad.metappgallery.detailScreen.dataSource.adapter.TagJsonAdapter
import com.asad.metappgallery.detailScreen.dataSource.dataSource.ObjectDetailRemoteDataSourceImpl
import com.asad.metappgallery.detailScreen.presentation.screen.DetailScreen
import com.asad.metappgallery.detailScreen.presentation.viewModel.ObjectDetailViewModel
import com.asad.metappgallery.searchScreen.data.adapter.MetSearchResponseJsonAdapter
import com.asad.metappgallery.searchScreen.data.dataSource.MetSearchRemoteDataSourceImpl
import com.asad.metappgallery.searchScreen.presentation.screen.ObjectSearchScreen
import com.asad.metappgallery.searchScreen.presentation.viewModel.MetSearchViewModel

@Composable
fun MetGalleryFinderApp() {
    val navController = rememberNavController()

    // Dependencies
    val networkRequester = NetworkRequester()

    val metSearchResponseJsonAdapter = MetSearchResponseJsonAdapter()
    val dataSource =
        MetSearchRemoteDataSourceImpl(networkRequester, metSearchResponseJsonAdapter)
    val metSearchViewModel = MetSearchViewModel(dataSource)

    val tagJsonAdapter = TagJsonAdapter()
    val constituentJsonAdapter = ConstituentJsonAdapter()
    val objectModelJsonAdapter = ObjectModelJsonAdapter(
        constituentJsonAdapter = constituentJsonAdapter,
        tagJsonAdapter = tagJsonAdapter,
    )

    val objectDetailRemoteDataSource = ObjectDetailRemoteDataSourceImpl(
        networkRequester = networkRequester,
        objectModelJsonAdapter = objectModelJsonAdapter,
    )

    val objectDetailViewModel = ObjectDetailViewModel(
        objectDetailRemoteDataSource = objectDetailRemoteDataSource,
    )

    /**
     * This callback must be passed to [ObjectSearchScreen] so that
     * it would be able to navigate to [DetailScreen]
     * */
    val onNavigationToDetailScreen: (Int) -> Unit = { objectId ->
        navController.navigate(route = Screen.DetailScreen.createRoute(objectId.toString()))
    }

    val onNavigationBack: () -> Unit = {
        navController.navigateUp()
    }

    NavHost(navController = navController, startDestination = Screen.SearchScreen.route) {
        composable(Screen.SearchScreen.route) {
            ObjectSearchScreen(
                viewModel = metSearchViewModel,
                onNavigationToObjectDetail = onNavigationToDetailScreen,
            )
        }

        composable(
            Screen.DetailScreen.route,
            arguments = listOf(
                navArgument(name = NavArgs.ObjectId) {
                    nullable = false
                    type = NavType.StringType
                },
            ),
        ) { entry ->
            val objectId = entry.arguments?.getString(NavArgs.ObjectId)

            DetailScreen(
                viewModel = objectDetailViewModel,
                currentObjectId = objectId!!,
                onNavigationBack = onNavigationBack,
            )
        }
    }
}
