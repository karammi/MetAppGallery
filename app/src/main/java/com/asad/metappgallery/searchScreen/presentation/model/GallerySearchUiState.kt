package com.asad.metappgallery.searchScreen.presentation.model

import androidx.compose.ui.text.input.TextFieldValue
import com.asad.metappgallery.core.presentation.UiState
import com.asad.metappgallery.searchScreen.data.dataSource.remote.model.GalleryResponseEntity
import com.asad.metappgallery.searchScreen.data.model.DepartmentResponse

data class GallerySearchUiState(
    val searchQuery: TextFieldValue = TextFieldValue(""),
    val searchResult: UiState<GalleryResponseEntity, String> = UiState.Empty,
    val isSearching: Boolean = false,
    val isHighlightSelected: Boolean? = false,
    val departments: UiState<DepartmentResponse, String> = UiState.Empty,
)
