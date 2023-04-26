package com.asad.metappgallery.searchScreen.data.repository

import com.asad.metappgallery.core.data.DataResult
import com.asad.metappgallery.searchScreen.domain.model.DepartmentModel
import com.asad.metappgallery.searchScreen.domain.model.DepartmentResponseModel
import com.asad.metappgallery.searchScreen.domain.model.GalleryResponseModel
import com.asad.metappgallery.searchScreen.domain.repository.GalleryRepository

class FakeSuccessGalleryRepositoryImpl : GalleryRepository {
    override suspend fun fetchGalleryList(
        query: String,
        isHighlight: Boolean?,
    ): DataResult<GalleryResponseModel> {
        return DataResult.Success(
            value = GalleryResponseModel(
                total = 10,
                objectIDs = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10),
            ),
        )
    }

    override suspend fun fetchDepartments(): DataResult<DepartmentResponseModel> {
        return DataResult.Success(
            value = DepartmentResponseModel(
                departmentModels = listOf(
                    DepartmentModel(1, "art"),
                    DepartmentModel(2, "museum"),
                    DepartmentModel(3, "music"),
                    DepartmentModel(4, "movie"),
                ),
            ),
        )
    }
}
