package com.asad.metappgallery.detailScreen.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asad.metappgallery.app.UiState
import com.asad.metappgallery.core.data.DataResult
import com.asad.metappgallery.detailScreen.dataSource.dataSource.ObjectDetailRemoteDataSource
import com.asad.metappgallery.detailScreen.dataSource.model.ObjectModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ObjectDetailViewModel constructor(
    private val objectDetailRemoteDataSource: ObjectDetailRemoteDataSource,
) : ViewModel() {

    private val dataFetchingJob: Job? = null

    val uiState = MutableStateFlow(ObjectDetailUiState())

    fun cancelFetchingData() = dataFetchingJob?.cancel()

    private fun setLoading(value: Boolean) {
        viewModelScope.launch {
            val newState = uiState.value.copy(isRefreshing = value)
            uiState.emit(newState)
        }
    }

    private fun setObjectData(data: DataResult<ObjectModel>) {
        viewModelScope.launch {
            when (data) {
                is DataResult.Error -> {
                    val newState =
                        uiState.value.copy(objectDetailState = UiState.Error(error = data.exception?.message))
                    uiState.emit(newState)
                }
                is DataResult.Success -> {
                    val newState =
                        uiState.value.copy(objectDetailState = UiState.Success(data = data.value))
                    uiState.emit(newState)
                }
            }
        }
    }

    fun fetchObjectDetail(objectId: Int): Job = viewModelScope.launch {
        setLoading(true)
        ensureActive()
        val result = objectDetailRemoteDataSource.fetchObjectDetail(objectId)
        ensureActive()
        setObjectData(result)
        setLoading(false)
    }
}
