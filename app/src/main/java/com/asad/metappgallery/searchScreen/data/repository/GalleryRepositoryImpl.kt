package com.asad.metappgallery.searchScreen.data.repository

import com.asad.metappgallery.core.data.DataResult
import com.asad.metappgallery.core.data.map
import com.asad.metappgallery.core.di.IoDispatcher
import com.asad.metappgallery.searchScreen.data.dataSource.remote.GalleryRemoteDataSource
import com.asad.metappgallery.searchScreen.data.repository.mapper.DepartmentResponseModelMapper
import com.asad.metappgallery.searchScreen.data.repository.mapper.GalleryModelMapper
import com.asad.metappgallery.searchScreen.domain.model.DepartmentResponseModel
import com.asad.metappgallery.searchScreen.domain.model.GalleryResponseModel
import com.asad.metappgallery.searchScreen.domain.repository.GalleryRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class GalleryRepositoryImpl @Inject constructor(
    private val galleryRemoteDataSource: GalleryRemoteDataSource,
    private val galleryModelMapper: GalleryModelMapper,
    private val departmentResponseModelMapper: DepartmentResponseModelMapper,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : GalleryRepository {
    override suspend fun fetchGalleryList(
        query: String,
        isHighlight: Boolean?,
    ): DataResult<GalleryResponseModel> {
        return withContext(ioDispatcher) {
            galleryRemoteDataSource
                .fetchGalleryList(query = query, isHighlight = isHighlight)
                .map {
                    galleryModelMapper.mapToModel(it)
                }
        }
    }

    override suspend fun fetchDepartments(): DataResult<DepartmentResponseModel> =
        galleryRemoteDataSource
            .fetchDepartments()
            .map {
                departmentResponseModelMapper.mapToModel(it)
            }
}
