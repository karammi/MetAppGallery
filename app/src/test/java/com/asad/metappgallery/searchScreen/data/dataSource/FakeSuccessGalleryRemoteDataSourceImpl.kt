package com.asad.metappgallery.searchScreen.data.dataSource

import com.asad.metappgallery.core.data.DataResult
import com.asad.metappgallery.searchScreen.data.dataSource.remote.GalleryRemoteDataSource
import com.asad.metappgallery.searchScreen.data.dataSource.remote.model.DepartmentResponseEntity
import com.asad.metappgallery.searchScreen.data.model.GalleryResponse

class FakeSuccessGalleryRemoteDataSourceImpl : GalleryRemoteDataSource {
    override suspend fun fetchGalleryList(
        query: String,
        isHighlight: Boolean?,
    ): DataResult<GalleryResponse> {
        return DataResult.Success(
            value = GalleryResponse(
                total = 10,
                objectIDs = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10),
            ),
        )
    }

    override suspend fun fetchDepartments(): DataResult<DepartmentResponseEntity> {
        TODO("Not yet implemented")
    }
}
