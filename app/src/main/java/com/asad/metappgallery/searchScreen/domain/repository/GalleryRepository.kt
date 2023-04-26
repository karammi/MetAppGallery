package com.asad.metappgallery.searchScreen.domain.repository

import com.asad.metappgallery.core.data.DataResult
import com.asad.metappgallery.searchScreen.domain.model.DepartmentResponseModel
import com.asad.metappgallery.searchScreen.domain.model.GalleryResponseModel

interface GalleryRepository {

    suspend fun fetchGalleryList(
        query: String,
        isHighlight: Boolean?,
    ): DataResult<GalleryResponseModel>

    suspend fun fetchDepartments(): DataResult<DepartmentResponseModel>
}
