package com.asad.metappgallery.detailScreen.presentation.model

import com.asad.metappgallery.app.UiState
import com.asad.metappgallery.detailScreen.data.model.ObjectModel

data class ObjectDetailUiState(
    val isRefreshing: Boolean = false,
    val objectDetailState: UiState<ObjectModel, String> = UiState.Loading,
)
