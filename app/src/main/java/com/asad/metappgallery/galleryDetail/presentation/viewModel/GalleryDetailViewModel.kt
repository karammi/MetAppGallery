package com.asad.metappgallery.galleryDetail.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asad.metappgallery.app.UiState
import com.asad.metappgallery.core.data.DataResult
import com.asad.metappgallery.galleryDetail.dataSource.dataSource.GalleryDetailRemoteDataSource
import com.asad.metappgallery.galleryDetail.dataSource.model.ArtObject
import kotlinx.coroutines.Job
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class GalleryDetailViewModel constructor(
    private val galleryDetailRemoteDataSource: GalleryDetailRemoteDataSource,
) : ViewModel() {

    private val dataFetchingJob: Job? = null

    val uiState = MutableStateFlow(GalleryDetailUiState())

    fun cancelFetchingData() = dataFetchingJob?.cancel()

    private fun setLoading(value: Boolean) {
        viewModelScope.launch {
            val newState = uiState.value.copy(isRefreshing = value)
            uiState.emit(newState)
        }
    }

    private fun setGalleryData(data: DataResult<ArtObject>) {
        viewModelScope.launch {
            when (data) {
                is DataResult.Error -> {
                    val newState =
                        uiState.value.copy(galleryDetail = UiState.Error(error = data.exception?.message))
                    uiState.emit(newState)
                }
                is DataResult.Success -> {
                    val newState =
                        uiState.value.copy(galleryDetail = UiState.Success(data = data.value))
                    uiState.emit(newState)
                }
            }
        }
    }

    fun fetchGalleryDetail(objectId: Int): Job = viewModelScope.launch {
        setLoading(true)
        ensureActive()
        val result = galleryDetailRemoteDataSource.fetchObjectDetail(objectId)
        ensureActive()
        setGalleryData(result)
        setLoading(false)
    }
}
