package com.asad.metappgallery.searchScreen.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.asad.metappgallery.R
import com.asad.metappgallery.app.UiState
import com.asad.metappgallery.core.util.ComposeUtil
import com.asad.metappgallery.core.util.SystemUiUtil
import com.asad.metappgallery.searchScreen.data.adapter.GalleryResponseConstant.FailedToFetchDataDefaultMessage
import com.asad.metappgallery.searchScreen.presentation.component.CustomAppBar
import com.asad.metappgallery.searchScreen.presentation.component.CustomBottomSearchBar
import com.asad.metappgallery.searchScreen.presentation.component.CustomEmptyContent
import com.asad.metappgallery.searchScreen.presentation.component.GallerySearchData
import com.asad.metappgallery.searchScreen.presentation.component.GallerySearchError
import com.asad.metappgallery.searchScreen.presentation.util.UiConstant
import com.asad.metappgallery.searchScreen.presentation.viewModel.GallerySearchViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun GallerySearchScreen(
    viewModel: GallerySearchViewModel,
    onNavigationToObjectDetail: (Int) -> Unit,
) {
    SystemUiUtil.ConfigStatusBar(color = MaterialTheme.colors.primaryVariant)

    val uiState by ComposeUtil.rememberStateWithLifecycle(stateFlow = viewModel.uiState)

    // This is used to close the keyboard, when navigate to detail screen
    val localFocusManager = LocalFocusManager.current

    val onSearchedValueChanged: (TextFieldValue) -> Unit = { value ->
        viewModel.setSearchText(value)
    }

    val onItemClicked: (Int) -> Unit = { objectId ->
        onNavigationToObjectDetail.invoke(objectId)
        localFocusManager.clearFocus()
    }

    val onRetiredFetchingData: () -> Unit = {
        viewModel.fetchGalleryList(uiState.searchQuery.text)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .padding(top = 64.dp),
        ) {
            when (uiState.searchResult) {
                is UiState.Success -> {
                    val data = uiState.searchResult.data?.objectIDs!!

                    GallerySearchData(data = data, onItemClicked = onItemClicked)
                }

                is UiState.Error -> {
                    GallerySearchError(
                        errorMessage = uiState.searchResult.error
                            ?: "Oops! An error occurred!\n$FailedToFetchDataDefaultMessage",
                        tryAgain = onRetiredFetchingData,
                    )
                }

                is UiState.Empty -> {
                    CustomEmptyContent(body = UiConstant.GallerySearchEmptyStateTitle)
                }

                is UiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .requiredSize(48.dp)
                            .align(Alignment.Center),
                        color = Color.Black,
                        strokeWidth = 4.dp,
                    )
                }
            }
        }

        // App Bar
        CustomAppBar(title = stringResource(id = R.string.app_name))

        // Bottom Search Bar
        CustomBottomSearchBar(
            searchedValue = uiState.searchQuery,
            onSearchedValueChanged = onSearchedValueChanged,
        )
    }
}
