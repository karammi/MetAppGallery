package com.asad.metappgallery.searchScreen.data.dataSource

import com.asad.metappgallery.core.data.DataResult
import com.asad.metappgallery.searchScreen.data.dataSource.remote.GalleryRemoteDataSource
import com.asad.metappgallery.searchScreen.data.dataSource.remote.model.GalleryResponseEntity

class FakeGalleryRemoteDataSourceImpl : GalleryRemoteDataSource {

    override suspend fun fetchList(query: String): DataResult<GalleryResponseEntity> {
        return DataResult.Success(
            value = GalleryResponseEntity(
                total = 10,
                objectIDs = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10),
            ),
        )
    }
}
