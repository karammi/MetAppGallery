package com.asad.metappgallery.searchScreen.data.dataSource

import com.asad.metappgallery.core.data.DataResult
import com.asad.metappgallery.searchScreen.data.model.DepartmentResponse
import com.asad.metappgallery.searchScreen.data.model.GalleryResponse

interface GalleryRemoteDataSource {

    suspend fun fetchList(query: String, isHighlight: Boolean?): DataResult<GalleryResponse>

    suspend fun fetchDepartments(): DataResult<DepartmentResponse>
}

