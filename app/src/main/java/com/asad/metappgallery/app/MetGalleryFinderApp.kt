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
import com.asad.metappgallery.galleryDetail.dataSource.adapter.ArtObjectJsonAdapter
import com.asad.metappgallery.galleryDetail.dataSource.adapter.ConstituentJsonAdapter
import com.asad.metappgallery.galleryDetail.dataSource.adapter.TagJsonAdapter
import com.asad.metappgallery.galleryDetail.dataSource.dataSource.GalleryDetailRemoteDataSourceImpl
import com.asad.metappgallery.galleryDetail.presentation.screen.GalleryDetailScreen
import com.asad.metappgallery.galleryDetail.presentation.viewModel.GalleryDetailViewModel
import com.asad.metappgallery.galleryFinder.data.adapter.ObjectIDSJsonAdapter
import com.asad.metappgallery.galleryFinder.data.dataSource.GalleryFinderRemoteDataSourceImpl
import com.asad.metappgallery.galleryFinder.presentation.screen.GalleryFinderScreen
import com.asad.metappgallery.galleryFinder.presentation.viewModel.GalleryFinderViewModel

@Composable
fun MetGalleryFinderApp() {
    val navController = rememberNavController()

    // Dependencies
    val networkRequester = NetworkRequester()

    val objectIDSJsonAdapter = ObjectIDSJsonAdapter()
    val dataSource =
        GalleryFinderRemoteDataSourceImpl(networkRequester, objectIDSJsonAdapter)
    val galleryFinderViewModel = GalleryFinderViewModel(dataSource)

    val tagJsonAdapter = TagJsonAdapter()
    val constituentJsonAdapter = ConstituentJsonAdapter()
    val artObjectJsonAdapter = ArtObjectJsonAdapter(
        constituentJsonAdapter = constituentJsonAdapter,
        tagJsonAdapter = tagJsonAdapter,
    )

    val galleryDetailRemoteDataSource = GalleryDetailRemoteDataSourceImpl(
        networkRequester = networkRequester,
        artObjectJsonAdapter = artObjectJsonAdapter,
    )

    val galleryDetailViewModel = GalleryDetailViewModel(
        galleryDetailRemoteDataSource = galleryDetailRemoteDataSource,
    )

    /**
     * This callback must be passed to [GalleryFinderScreen] so that
     * it would be able to navigate to [GalleryDetailScreen]
     * */
    val onNavigationToGalleryDetail: (Int) -> Unit = { objectId ->
        navController.navigate(route = Screen.GalleryDetailScreen.createRoute(objectId.toString()))
    }

    val onNavigationBack: () -> Unit = {
        navController.navigateUp()
    }

    NavHost(navController = navController, startDestination = Screen.GalleryFinderScreen.route) {
        composable(Screen.GalleryFinderScreen.route) {
            GalleryFinderScreen(
                viewModel = galleryFinderViewModel,
                onNavigationToGalleryDetail = onNavigationToGalleryDetail,
            )
        }

        composable(
            Screen.GalleryDetailScreen.route,
            arguments = listOf(
                navArgument(name = NavArgs.GalleryObjectId) {
                    nullable = false
                    type = NavType.StringType
                },
            ),
        ) { entry ->
            val objectId = entry.arguments?.getString(NavArgs.GalleryObjectId)

            GalleryDetailScreen(
                viewModel = galleryDetailViewModel,
                currentObjectId = objectId!!,
                onNavigationBack = onNavigationBack,
            )
        }
    }
}
