package com.asad.metappgallery.detailScreen.data.dataSource.remote

import com.asad.metappgallery.core.data.DataResult
import com.asad.metappgallery.core.util.ApiRunner
import com.asad.metappgallery.detailScreen.data.dataSource.api.ObjectDetailApi
import com.asad.metappgallery.detailScreen.data.dataSource.remote.model.ObjectEntity
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ObjectDetailRemoteDataSourceImpl @Inject constructor(
    private val api: ObjectDetailApi,
    private val apiRunner: ApiRunner,
) : ObjectDetailRemoteDataSource {
    override suspend fun fetchObjectDetail(objectID: Int): DataResult<ObjectEntity> {
        return apiRunner.invokeSuspended {
            api.fetchObjectDetail(objectID)
        }
    }
}
