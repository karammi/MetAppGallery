package com.asad.metappgallery.detailScreen.dataSource.dataSource

import com.asad.metappgallery.core.data.DataResult
import com.asad.metappgallery.detailScreen.dataSource.model.ObjectModel

interface ObjectDetailRemoteDataSource {

    suspend fun fetchObjectDetail(objectID: Int): DataResult<ObjectModel>
}
