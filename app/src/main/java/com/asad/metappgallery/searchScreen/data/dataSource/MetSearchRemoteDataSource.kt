package com.asad.metappgallery.searchScreen.data.dataSource

import com.asad.metappgallery.core.data.DataResult
import com.asad.metappgallery.searchScreen.data.model.MetSearchResponse

interface MetSearchRemoteDataSource {

    suspend fun fetchObjects(query: String): DataResult<MetSearchResponse>
}
