package com.asad.metappgallery.searchScreen.data.repository

import com.asad.metappgallery.core.data.DataResult
import com.asad.metappgallery.searchScreen.data.dataSource.remote.GalleryRemoteDataSource
import com.asad.metappgallery.searchScreen.data.repository.mapper.DepartmentResponseModelMapper
import com.asad.metappgallery.searchScreen.data.repository.mapper.GalleryModelMapper
import com.asad.metappgallery.searchScreen.domain.model.DepartmentResponseModel
import com.asad.metappgallery.searchScreen.domain.model.GalleryResponseModel
import com.asad.metappgallery.searchScreen.domain.repository.GalleryRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class GalleryRepositoryImpl @Inject constructor(
    private val galleryRemoteDataSource: GalleryRemoteDataSource,
    private val galleryModelMapper: GalleryModelMapper,
    private val departmentResponseModelMapper: DepartmentResponseModelMapper,
) : GalleryRepository {
    override suspend fun fetchGalleryList(
        query: String,
        isHighlight: Boolean?,
    ): DataResult<GalleryResponseModel> {
        val response = galleryRemoteDataSource
            .fetchGalleryList(query = query, isHighlight = isHighlight)

        return when (response) {
            is DataResult.Error -> DataResult.Error(response.exception)
            is DataResult.Success -> DataResult.Success(galleryModelMapper.mapToModel(response.value))
        }
    }

    override suspend fun fetchDepartments(): DataResult<DepartmentResponseModel> {
        val response = galleryRemoteDataSource.fetchDepartments()
        return when (response) {
            is DataResult.Error -> response
            is DataResult.Success -> DataResult.Success(
                departmentResponseModelMapper.mapToModel(
                    response.value,
                ),
            )
        }
    }
}
