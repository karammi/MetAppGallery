package com.asad.metappgallery.core.util

import com.asad.metappgallery.core.data.DataResult
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ApiRunner @Inject constructor() {

    suspend fun <T> invokeSuspended(job: suspend () -> T): DataResult<T> {
        return try {
            DataResult.Success(job.invoke())
        } catch (exception: Exception) {
            DataResult.Error(exception)
        }
    }
}
