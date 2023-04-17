package com.asad.metappgallery.app

sealed class UiState<out T, out R>(open val data: T? = null, open val error: R? = null) {
    data class Success<T>(override val data: T) : UiState<T, Nothing>(data = data)
    data class Error<E>(override val error: E?) : UiState<Nothing, E>(error = error)
    object Loading : UiState<Nothing, Nothing>()
    object Empty : UiState<Nothing, Nothing>()
}
