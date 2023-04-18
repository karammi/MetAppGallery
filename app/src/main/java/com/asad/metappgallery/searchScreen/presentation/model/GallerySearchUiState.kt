package com.asad.metappgallery.searchScreen.presentation.model

import androidx.compose.ui.text.input.TextFieldValue
import com.asad.metappgallery.app.UiState
import com.asad.metappgallery.searchScreen.data.model.GalleryResponse

data class GallerySearchUiState(
    val searchQuery: TextFieldValue = TextFieldValue(""),
    val searchResult: UiState<GalleryResponse, String> = UiState.Empty,
    val isSearching: Boolean = false,
)
