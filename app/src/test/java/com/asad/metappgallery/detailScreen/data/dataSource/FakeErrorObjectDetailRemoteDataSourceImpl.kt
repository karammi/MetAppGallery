package com.asad.metappgallery.detailScreen.data.dataSource

import com.asad.metappgallery.core.data.DataResult
import com.asad.metappgallery.detailScreen.data.dataSource.remote.ObjectDetailRemoteDataSource
import com.asad.metappgallery.detailScreen.data.dataSource.remote.model.ObjectEntity

class FakeErrorObjectDetailRemoteDataSourceImpl : ObjectDetailRemoteDataSource {
    override suspend fun fetchObjectDetail(objectID: Int): DataResult<ObjectEntity> {
        return DataResult.Error(errorMessage = "Oops! An error occurred!")
    }
}
