package com.asad.metappgallery.searchScreen.presentation.viewModel

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asad.metappgallery.app.UiState
import com.asad.metappgallery.core.data.DataResult
import com.asad.metappgallery.searchScreen.data.dataSource.MetSearchRemoteDataSource
import com.asad.metappgallery.searchScreen.data.model.MetSearchResponse
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

private const val TAG = "GalleryFinder"

class MetSearchViewModel constructor(
    private val metSearchRemoteDataSource: MetSearchRemoteDataSource,
) : ViewModel() {

    val initialState = MetSearchUiState()
    val uiState = MutableStateFlow(initialState)

    private var fetchObjectsSearchJob: Job? = null

    init {
        observedSearchText()
    }

    private fun observedSearchText() = viewModelScope.launch {
        uiState.distinctUntilChanged { old, new -> old.searchedText.text == new.searchedText.text }
            .collectLatest {
                fetchObjectsSearchJob?.cancel()
                if (it.searchedText.text.isBlank()) {
                    resetUiState()
                } else {
                    delay(600)

                    fetchObjectsSearchJob = fetchObjects(it.searchedText.text)
                }
            }
    }

    /**
     * This is used to reset the UI state when user have cleared the text fields
     * */
    fun resetUiState() {
        viewModelScope.launch {
            val newState = uiState.value.copy(
                searchedText = TextFieldValue(""),
                searchResult = UiState.Empty,
                isSearching = false,
            )
            uiState.emit(newState)
        }
    }

    fun setIsSearching(value: Boolean) {
        viewModelScope.launch {
            val newState = uiState.value.copy(isSearching = value)
            uiState.emit(newState)
        }
    }

    fun setSearchResponse(value: DataResult<MetSearchResponse>) {
        viewModelScope.launch {
            when (value) {
                is DataResult.Error -> {
                    val newState =
                        uiState.value.copy(searchResult = UiState.Error(value.exception?.message))
                    uiState.emit(newState)
                }

                is DataResult.Success -> {
                    val newState = if (value.value.total == 0) {
                        uiState.value.copy(searchResult = UiState.Empty)
                    } else {
                        uiState.value.copy(searchResult = UiState.Success(value.value))
                    }
                    uiState.emit(newState)
                }
            }
        }
    }

    fun setSearchText(value: TextFieldValue) {
        viewModelScope.launch {
            val newState = uiState.value.copy(searchedText = value)
            uiState.emit(newState)
        }
    }

    /**
     * This method requests server to fetch the collection which contains current [queryString].
     * */
    fun fetchObjects(queryString: String): Job =
        viewModelScope.launch {
            setIsSearching(true)
            ensureActive() // This ensures that the coroutine is not cancelled
            val result = metSearchRemoteDataSource.fetchObjects(queryString)
            ensureActive() // This ensures that the coroutine is not cancelled
            setSearchResponse(result)
            setIsSearching(false)
        }
}
