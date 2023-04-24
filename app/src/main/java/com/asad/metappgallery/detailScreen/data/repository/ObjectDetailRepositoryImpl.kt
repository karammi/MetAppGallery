package com.asad.metappgallery.detailScreen.data.repository

import com.asad.metappgallery.core.data.DataResult
import com.asad.metappgallery.detailScreen.data.dataSource.remote.ObjectDetailRemoteDataSource
import com.asad.metappgallery.detailScreen.data.repository.mapper.ObjectModelMapper
import com.asad.metappgallery.detailScreen.domain.model.ObjectModel
import com.asad.metappgallery.detailScreen.domain.repository.ObjectDetailRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ObjectDetailRepositoryImpl @Inject constructor(
    private val objectDetailRemoteDataSource: ObjectDetailRemoteDataSource,
    private val objectModelMapper: ObjectModelMapper,

) : ObjectDetailRepository {
    override suspend fun fetchObjectDetail(objectID: Int): DataResult<ObjectModel> {
        val response = objectDetailRemoteDataSource
            .fetchObjectDetail(objectID = objectID)
        return when (response) {
            is DataResult.Error -> DataResult.Error(response.exception)
            is DataResult.Success -> DataResult.Success(objectModelMapper.mapToModel(response.value))
        }
    }
}
