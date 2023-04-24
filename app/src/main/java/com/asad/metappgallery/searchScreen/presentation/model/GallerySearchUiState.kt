package com.asad.metappgallery.searchScreen.presentation.model

import androidx.compose.ui.text.input.TextFieldValue
import com.asad.metappgallery.core.presentation.UiState
import com.asad.metappgallery.searchScreen.domain.model.DepartmentResponseModel
import com.asad.metappgallery.searchScreen.domain.model.GalleryResponseModel

data class GallerySearchUiState(
    val searchQuery: TextFieldValue = TextFieldValue(""),
    val searchResult: UiState<GalleryResponseModel, String> = UiState.Empty,
    val isSearching: Boolean = false,
    val isHighlightSelected: Boolean? = false,
    val departments: UiState<DepartmentResponseModel, String> = UiState.Empty,
)
