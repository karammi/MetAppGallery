package com.asad.metappgallery.searchScreen.data.dataSource.remote

import com.asad.metappgallery.core.data.DataResult
import com.asad.metappgallery.searchScreen.data.dataSource.remote.model.GalleryResponseEntity
import com.asad.metappgallery.searchScreen.data.model.DepartmentResponse

interface GalleryRemoteDataSource {

    suspend fun fetchGalleryList(query: String, isHighlight: Boolean?): DataResult<GalleryResponseEntity>

    suspend fun fetchDepartments(): DataResult<DepartmentResponse>
}
