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
        return objectDetailRemoteDataSource
            .fetchObjectDetail(objectID = objectID).map {
                objectModelMapper.mapToModel(it)
            }
    }
}

public inline fun <reified R, reified T> DataResult<T>.map(transform: (value: T) -> R): DataResult<R> {
    return when (this) {
        is DataResult.Error -> DataResult.Success(transform.invoke(exception as T))
        is DataResult.Success -> DataResult.Success(transform.invoke(value))
    }
}
