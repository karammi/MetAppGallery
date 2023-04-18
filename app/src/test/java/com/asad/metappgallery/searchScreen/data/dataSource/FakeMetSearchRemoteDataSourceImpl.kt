package com.asad.metappgallery.searchScreen.data.dataSource

import com.asad.metappgallery.core.data.DataResult
import com.asad.metappgallery.searchScreen.data.model.MetSearchResponse

class FakeMetSearchRemoteDataSourceImpl : MetSearchRemoteDataSource {

    //TODO should remove condition from fetch function, just i put it for testing the state
    override suspend fun fetchObjects(query: String): DataResult<MetSearchResponse> {
        return if (query == "") {
            DataResult.Success(value = MetSearchResponse(total = 0, objectIDs = null))
        } else {
            DataResult.Success(
                value = MetSearchResponse(
                    total = 10,
                    objectIDs = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10),
                ),
            )
        }
    }
}
