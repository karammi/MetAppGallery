package com.asad.metappgallery.detailScreen.presentation.model

import com.asad.metappgallery.core.presentation.UiState
import com.asad.metappgallery.detailScreen.data.dataSource.remote.model.ObjectEntity

data class ObjectDetailUiState(
    val objectDetailState: UiState<ObjectEntity, String> = UiState.Empty,
)
