package com.asad.metappgallery.detailScreen.presentation.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asad.metappgallery.core.presentation.UiState
import com.asad.metappgallery.core.data.DataResult
import com.asad.metappgallery.detailScreen.data.dataSource.remote.ObjectDetailRemoteDataSource
import com.asad.metappgallery.detailScreen.data.dataSource.remote.model.ObjectEntity
import com.asad.metappgallery.detailScreen.presentation.model.ObjectDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "ObjectDetailViewModel"

@HiltViewModel
class ObjectDetailViewModel @Inject constructor(
    private val objectDetailRemoteDataSource: ObjectDetailRemoteDataSource,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val dataFetchingJob: Job? = null

    val uiState = MutableStateFlow(ObjectDetailUiState())

    fun cancelFetchingData() = dataFetchingJob?.cancel()

    fun showLoading() {
        viewModelScope.launch {
            val newState = uiState.value.copy(objectDetailState = UiState.Loading)
            uiState.emit(newState)
        }
    }

    private fun setObjectDataResponse(data: DataResult<ObjectEntity>) {
        viewModelScope.launch {
            when (data) {
                is DataResult.Error -> {
                    val newState =
                        uiState.value.copy(
                            objectDetailState = UiState.Error(message = data.exception?.message),
                        )
                    uiState.emit(newState)
                }

                is DataResult.Success -> {
                    val newState = uiState.value.copy(
                        objectDetailState = UiState.Success(data = data.value),
                    )
                    uiState.emit(newState)
                }
            }
        }
    }

    fun fetchObjectDetail(objectId: Int): Job = viewModelScope.launch {
        showLoading()
        val result = objectDetailRemoteDataSource.fetchObjectDetail(objectId)
        setObjectDataResponse(result)
    }
}
