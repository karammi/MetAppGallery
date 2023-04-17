package com.asad.metappgallery.galleryDetail.presentation.viewModel

import com.asad.metappgallery.app.UiState
import com.asad.metappgallery.galleryDetail.dataSource.model.ArtObject

data class GalleryDetailUiState(
    val isRefreshing: Boolean = false,
    val galleryDetail: UiState<ArtObject, String> = UiState.Loading,
)
