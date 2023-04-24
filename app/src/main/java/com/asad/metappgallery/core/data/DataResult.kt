package com.asad.metappgallery.core.data

/**
 * This is a type defined to wrap the data returned from API calls.
 * It could be [DataResult.Error] in case of the API not succeeding and
 * [DataResult.Success] in case of a successful API call.
 *
 * @param value is of a generic type and is different based on each API.
 * @param exception is the one that the api have thrown.
 * */
sealed class DataResult<out R>(
    open val value: R? = null,
    open val exception: Exception? = null,
) {
    class Success<T>(override val value: T) : DataResult<T>()
    class Error(override val exception: Exception?) : DataResult<Nothing>()
}

inline fun <reified R, reified T> DataResult<T>.map(transform: (value: T) -> R): DataResult<R> {
    return when (this) {
        is DataResult.Error -> DataResult.Success(transform.invoke(exception as T))
        is DataResult.Success -> DataResult.Success(transform.invoke(value as T))
    }
}
