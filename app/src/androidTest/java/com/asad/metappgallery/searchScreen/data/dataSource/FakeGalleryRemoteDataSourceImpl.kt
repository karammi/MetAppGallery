package com.asad.metappgallery.searchScreen.data.dataSource

import com.asad.metappgallery.core.data.DataResult
import com.asad.metappgallery.searchScreen.data.dataSource.remote.GalleryRemoteDataSource
import com.asad.metappgallery.searchScreen.data.dataSource.remote.model.GalleryResponseEntity
import com.asad.metappgallery.searchScreen.data.dataSource.remote.model.DepartmentResponseEntity

class FakeGalleryRemoteDataSourceImpl : GalleryRemoteDataSource {

    override suspend fun fetchGalleryList(
        query: String,
        isHighlight: Boolean?,
    ): DataResult<GalleryResponseEntity> {
        return DataResult.Success(
            value = GalleryResponseEntity(
                total = 2,
                objectIDs = listOf(1, 2),
            ),
        )
    }

    override suspend fun fetchDepartments(): DataResult<DepartmentResponseEntity> {
        return DataResult.Success(DepartmentResponseEntity(departmentEntities = emptyList()))
    }
}
