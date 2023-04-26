package com.asad.metappgallery.searchScreen.data.repository

import com.asad.metappgallery.core.data.DataResult
import com.asad.metappgallery.searchScreen.domain.model.DepartmentResponseModel
import com.asad.metappgallery.searchScreen.domain.model.GalleryResponseModel
import com.asad.metappgallery.searchScreen.domain.repository.GalleryRepository

class FakeErrorGalleryRepositoryImpl : GalleryRepository {

    override suspend fun fetchGalleryList(
        query: String,
        isHighlight: Boolean?,
    ): DataResult<GalleryResponseModel> {
        return DataResult.Error(errorMessage = "Oops!,An error occurred!")
    }

    override suspend fun fetchDepartments(): DataResult<DepartmentResponseModel> {
        return DataResult.Error(errorMessage = "Oops!,An error occurred!")
    }
}
