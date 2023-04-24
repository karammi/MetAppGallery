package com.asad.metappgallery.detailScreen.presentation.model

import com.asad.metappgallery.core.presentation.UiState
import com.asad.metappgallery.detailScreen.domain.model.ObjectModel

data class ObjectDetailUiState(
    val objectDetailState: UiState<ObjectModel, String> = UiState.Empty,
)
