package com.asad.metappgallery.detailScreen.presentation.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asad.metappgallery.core.data.DataResult
import com.asad.metappgallery.core.di.IoDispatcher
import com.asad.metappgallery.core.presentation.UiState
import com.asad.metappgallery.detailScreen.domain.model.ObjectModel
import com.asad.metappgallery.detailScreen.domain.repository.ObjectDetailRepository
import com.asad.metappgallery.detailScreen.presentation.model.ObjectDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "ObjectDetailViewModel"

/**SaveStateHandler just added to fetch object Id from it*/
@HiltViewModel
class ObjectDetailViewModel @Inject constructor(
    private val repository: ObjectDetailRepository,
    private val savedStateHandle: SavedStateHandle,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
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

    private fun setObjectDataResponse(data: DataResult<ObjectModel>) {
        viewModelScope.launch {
            when (data) {
                is DataResult.Error -> {
                    val newState =
                        uiState.value.copy(
                            objectDetailState = UiState.Error(message = data.errorMessage),
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

    fun fetchObjectDetail(objectId: Int): Job = viewModelScope.launch(ioDispatcher) {
        showLoading()
        val result = repository.fetchObjectDetail(objectId)
        setObjectDataResponse(result)
    }
}
