package com.asad.metappgallery.searchScreen.presentation.viewModel

import androidx.compose.ui.text.input.TextFieldValue
import com.asad.metappgallery.app.UiState
import com.asad.metappgallery.searchScreen.data.model.MetSearchResponse

data class MetSearchUiState(
    val searchedText: TextFieldValue = TextFieldValue(""),
    val searchResult: UiState<MetSearchResponse, String> = UiState.Empty,
    val isSearching: Boolean = false,
)
