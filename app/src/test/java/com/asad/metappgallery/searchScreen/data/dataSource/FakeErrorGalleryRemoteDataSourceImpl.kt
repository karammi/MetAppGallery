package com.asad.metappgallery.searchScreen.data.dataSource

import com.asad.metappgallery.core.data.DataResult
import com.asad.metappgallery.searchScreen.data.dataSource.remote.GalleryRemoteDataSource
import com.asad.metappgallery.searchScreen.data.dataSource.remote.model.DepartmentResponseEntity
import com.asad.metappgallery.searchScreen.data.model.GalleryResponse

class FakeErrorGalleryRemoteDataSourceImpl : GalleryRemoteDataSource {

    override suspend fun fetchGalleryList(
        query: String,
        isHighlight: Boolean?,
    ): DataResult<GalleryResponse> {
        return DataResult.Error(exception = Exception("Oops!,An error occurred!"))
    }

    override suspend fun fetchDepartments(): DataResult<DepartmentResponseEntity> {
        TODO("Not yet implemented")
    }
}
