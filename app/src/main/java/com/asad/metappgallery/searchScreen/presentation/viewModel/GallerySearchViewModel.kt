package com.asad.metappgallery.searchScreen.presentation.viewModel

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asad.metappgallery.core.data.DataResult
import com.asad.metappgallery.core.presentation.UiState
import com.asad.metappgallery.searchScreen.data.dataSource.remote.GalleryRemoteDataSource
import com.asad.metappgallery.searchScreen.data.dataSource.remote.model.GalleryResponseEntity
import com.asad.metappgallery.searchScreen.data.model.DepartmentResponse
import com.asad.metappgallery.searchScreen.presentation.model.GallerySearchUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "GalleryFinder"

@HiltViewModel
class GallerySearchViewModel @Inject constructor(
    private val galleryRemoteDataSource: GalleryRemoteDataSource,
) : ViewModel() {

    val initialState = GallerySearchUiState()
    val uiState = MutableStateFlow(initialState)

    private var fetchObjectsSearchJob: Job? = null

    init {
//        fetchDepartmentList()
        observedSearchText()
    }

    /**
     * Returns flow where all subsequent repetitions of the same value are filtered out,
     * when compared with each other via the provided areEquivalent function.
     */
    private fun observedSearchText() = viewModelScope.launch {
        uiState.distinctUntilChanged { old, new -> old.searchQuery.text == new.searchQuery.text }
            .collectLatest {
                fetchObjectsSearchJob?.cancel()
                if (it.searchQuery.text.isBlank()) {
                    resetUiState()
                } else {
                    delay(timeMillis = 500)
                    fetchObjectsSearchJob =
                        fetchGalleryList(queryString = it.searchQuery.text, isHighlight = true)
                }
            }
    }

    /**
     * This method requests server to fetch the collection which contains current [queryString].
     * */
    fun fetchGalleryList(queryString: String, isHighlight: Boolean? = null): Job =
        viewModelScope.launch {
            showLoading()
            val result =
                galleryRemoteDataSource.fetchGalleryList(
                    query = queryString,
                    isHighlight = isHighlight,
                )
            setGallerySearchResponse(result)
        }

    fun fetchDepartmentList() {
        viewModelScope.launch {
            val result = galleryRemoteDataSource.fetchDepartments()
            setDepartmentResponse(result)
        }
    }

    /**
     * This is used to reset the UI state when user have cleared the text fields
     * */
    fun resetUiState() {
        viewModelScope.launch {
            val newState = uiState.value.copy(
                searchQuery = TextFieldValue(""),
                searchResult = UiState.Empty,
            )
            uiState.emit(newState)
        }
    }

    fun showLoading() {
        viewModelScope.launch {
            val newState = uiState.value.copy(searchResult = UiState.Loading)
            uiState.emit(newState)
        }
    }

    fun setIsHighlightValue(value: Boolean) {
        viewModelScope.launch {
            val newState = uiState.value.copy(isHighlightSelected = value)
            uiState.emit(newState)
        }
    }

    fun setSearchText(value: TextFieldValue) {
        viewModelScope.launch {
            val newState = uiState.value.copy(searchQuery = value)
            uiState.emit(newState)
        }
    }

    fun setGallerySearchResponse(value: DataResult<GalleryResponseEntity>) {
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

    fun setDepartmentResponse(value: DataResult<DepartmentResponse>) {
        viewModelScope.launch {
            when (value) {
                is DataResult.Error -> {
                    val newState =
                        uiState.value.copy(
                            departments = UiState.Error(
                                message = value.exception?.message ?: "Error has happened!",
                            ),
                        )
                    uiState.emit(newState)
                }

                is DataResult.Success -> {
                    val newState = if (value.value.departments.isEmpty()) {
                        uiState.value.copy(departments = UiState.Empty)
                    } else {
                        uiState.value.copy(departments = UiState.Success(data = value.value))
                    }
                    uiState.emit(newState)
                }
            }
        }
    }

    /**
     * This method requests server to fetch the collection which contains current [queryString].
     * */
    fun fetchGalleryList(queryString: String): Job =
        viewModelScope.launch {
            showLoading()
            val result = galleryRemoteDataSource.fetchGalleryList(queryString, isHighlight = false)
            setGallerySearchResponse(result)
        }
}
