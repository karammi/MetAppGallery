package com.asad.metappgallery.searchScreen.data.dataSource

import com.asad.metappgallery.core.data.DataResult
import com.asad.metappgallery.searchScreen.data.model.GalleryResponse

class FakeGalleryRemoteDataSourceImpl : GalleryRemoteDataSource {

    //TODO should remove condition from fetch function, just i put it for testing the state
    override suspend fun fetchList(query: String): DataResult<GalleryResponse> {
        return if (query == "") {
            DataResult.Success(value = GalleryResponse(total = 0, objectIDs = null))
        } else {
            DataResult.Success(
                value = GalleryResponse(
                    total = 10,
                    objectIDs = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10),
                ),
            )
        }
    }
}
