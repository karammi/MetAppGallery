package com.asad.metappgallery.detailScreen.presentation.viewModel

import com.asad.metappgallery.app.UiState
import com.asad.metappgallery.detailScreen.dataSource.model.ObjectModel

data class ObjectDetailUiState(
    val isRefreshing: Boolean = false,
    val objectDetailState: UiState<ObjectModel, String> = UiState.Loading,
)
