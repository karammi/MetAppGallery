package com.asad.metappgallery.searchScreen.data.dataSource

import com.asad.metappgallery.core.data.DataResult
import com.asad.metappgallery.searchScreen.data.model.GalleryResponse

class FakeGalleryRemoteDataSourceImpl : GalleryRemoteDataSource {
    override suspend fun fetchList(query: String): DataResult<GalleryResponse> {
        return DataResult.Success(value = GalleryResponse(total = 2, objectIDs = listOf(1, 2)))
    }
}