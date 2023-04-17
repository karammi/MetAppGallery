package com.asad.metappgallery.galleryFinder.presentation.viewModel

import androidx.compose.ui.text.input.TextFieldValue
import com.asad.metappgallery.app.UiState
import com.asad.metappgallery.galleryFinder.data.model.ObjectIDResponse

data class GalleryFinderUiState(
    val searchedText: TextFieldValue = TextFieldValue(""),
    val searchResult: UiState<ObjectIDResponse, String> = UiState.Empty,
    val isSearching: Boolean = false,
)
