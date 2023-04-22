package com.asad.metappgallery.searchScreen.data.dataSource.remote

import com.asad.metappgallery.core.data.DataResult
import com.asad.metappgallery.core.util.ApiRunner
import com.asad.metappgallery.searchScreen.data.dataSource.api.GalleryApi
import com.asad.metappgallery.searchScreen.data.dataSource.remote.model.GalleryResponseEntity
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class GalleryRemoteDataSourceImpl @Inject constructor(
    private val apiRunner: ApiRunner,
    private val galleryApi: GalleryApi,
) : GalleryRemoteDataSource {

    override suspend fun fetchList(query: String): DataResult<GalleryResponseEntity> {
        return apiRunner.invokeSuspended {
            galleryApi.fetchList(query)
        }
    }
}
