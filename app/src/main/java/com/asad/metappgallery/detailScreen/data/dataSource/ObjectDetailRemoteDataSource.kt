package com.asad.metappgallery.detailScreen.data.dataSource

import com.asad.metappgallery.core.data.DataResult
import com.asad.metappgallery.detailScreen.data.model.ObjectModel

interface ObjectDetailRemoteDataSource {

    suspend fun fetchObjectDetail(objectID: Int): DataResult<ObjectModel>
}
