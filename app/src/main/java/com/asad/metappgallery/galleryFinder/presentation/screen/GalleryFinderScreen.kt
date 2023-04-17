package com.asad.metappgallery.galleryFinder.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.asad.metappgallery.core.util.ComposeUtil
import com.asad.metappgallery.core.util.SystemUiUtil
import com.asad.metappgallery.galleryFinder.presentation.component.CustomAppBar
import com.asad.metappgallery.galleryFinder.presentation.component.EmptyResultContent
import com.asad.metappgallery.galleryFinder.presentation.component.ErrorResultContent
import com.asad.metappgallery.galleryFinder.presentation.component.GalleryFinderBottomSearchBar
import com.asad.metappgallery.galleryFinder.presentation.viewModel.GalleryFinderViewModel
import com.asad.metappgallery.app.UiState

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun GalleryFinderScreen(
    viewModel: GalleryFinderViewModel,
    onNavigationToGalleryDetail: (Int) -> Unit,
) {
    SystemUiUtil.ConfigStatusBar(color = MaterialTheme.colors.primaryVariant)

    val uiState by ComposeUtil.rememberStateWithLifecycle(stateFlow = viewModel.uiState)

    val onSearchedValueChanged: (TextFieldValue) -> Unit = { value ->
        viewModel.setSearchText(value)
    }

    val onItemClicked: (Int) -> Unit = { objectId ->
        onNavigationToGalleryDetail.invoke(objectId)
    }

    val onRetiredFetchingData: () -> Unit = {
        viewModel.fetchGalleries(uiState.searchedText.text)
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
                    LazyColumn {
                        if (uiState.searchResult.data?.objectIDs?.size == 0) {
                            item {
                                EmptyResultContent()
                            }
                        } else {
                            items(uiState.searchResult.data?.objectIDs?.size ?: 0) { index ->
                                MetGalleryItem(
                                    title = uiState.searchResult.data?.objectIDs?.get(
                                        index,
                                    ).toString(),
                                    onItemClicked = onItemClicked,
                                )
                            }
                        }
                    }
                }
                is UiState.Error -> {
                    ErrorResultContent(errorMessage = uiState.searchResult.error ?: "Error")
                    onRetiredFetchingData.invoke()
                }
                is UiState.Empty -> {
                    EmptyResultContent()
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
        CustomAppBar(title = "Gallery app finder")

        // Bottom Search Bar
        GalleryFinderBottomSearchBar(
            searchedValue = uiState.searchedText,
            onSearchedValueChanged = onSearchedValueChanged,
        )
    }
}

@Composable
fun MetGalleryItem(title: String, onItemClicked: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onItemClicked.invoke(title.toInt()) },
        shape = RoundedCornerShape(12.dp),
        elevation = 8.dp,
    ) {
        Text(
            text = title,
            modifier = Modifier.fillMaxSize(),
            textAlign = TextAlign.Center,
            style = TextStyle(textAlign = TextAlign.Center),
        )
    }
}
