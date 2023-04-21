package com.asad.metappgallery

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.asad.metappgallery.app.navigation.NavArgs
import com.asad.metappgallery.app.navigation.Screen
import com.asad.metappgallery.detailScreen.data.dataSource.FakeObjectDetailRemoteDataSourceImpl
import com.asad.metappgallery.detailScreen.presentation.screen.ObjectDetailScreen
import com.asad.metappgallery.detailScreen.presentation.viewModel.ObjectDetailViewModel
import com.asad.metappgallery.searchScreen.data.dataSource.FakeGalleryRemoteDataSourceImpl
import com.asad.metappgallery.searchScreen.presentation.screen.GallerySearchScreen
import com.asad.metappgallery.searchScreen.presentation.viewModel.GallerySearchViewModel

@Composable
fun FakeMetGalleryFinderApp() {
    val navController = rememberNavController()

    // Dependencies

    val dataSource = FakeGalleryRemoteDataSourceImpl()
    val gallerySearchViewModel = GallerySearchViewModel(dataSource)

    val objectDetailRemoteDataSource = FakeObjectDetailRemoteDataSourceImpl()

    val objectDetailViewModel = ObjectDetailViewModel(
        objectDetailRemoteDataSource = objectDetailRemoteDataSource,
    )

    /**
     * This callback must be passed to [GallerySearchScreen] so that
     * it would be able to navigate to [ObjectDetailScreen]
     * */
    val onNavigationToDetailScreen: (Int) -> Unit = { objectId ->
        navController.navigate(route = Screen.DetailScreen.createRoute(objectId.toString()))
    }

    val onNavigationBack: () -> Unit = {
        navController.navigateUp()
    }

    NavHost(navController = navController, startDestination = Screen.SearchScreen.route) {
        composable(Screen.SearchScreen.route) {
            GallerySearchScreen(
                viewModel = gallerySearchViewModel,
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

            ObjectDetailScreen(
                viewModel = objectDetailViewModel,
                currentObjectId = objectId!!,
                onNavigationBack = onNavigationBack,
            )
        }
    }
}
