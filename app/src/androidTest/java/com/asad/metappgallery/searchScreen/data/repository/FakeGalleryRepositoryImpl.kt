package com.asad.metappgallery.searchScreen.data.repository

import com.asad.metappgallery.core.data.DataResult
import com.asad.metappgallery.searchScreen.domain.model.DepartmentResponseModel
import com.asad.metappgallery.searchScreen.domain.model.GalleryResponseModel
import com.asad.metappgallery.searchScreen.domain.repository.GalleryRepository

class FakeGalleryRepositoryImpl : GalleryRepository {
    override suspend fun fetchGalleryList(
        query: String,
        isHighlight: Boolean?,
    ): DataResult<GalleryResponseModel> {
        return DataResult.Success(
            value = GalleryResponseModel(
                total = 2,
                objectIDs = listOf(1, 2),
            ),
        )
    }

    override suspend fun fetchDepartments(): DataResult<DepartmentResponseModel> {
        return DataResult.Success(DepartmentResponseModel(departmentModels = emptyList()))
    }
}
