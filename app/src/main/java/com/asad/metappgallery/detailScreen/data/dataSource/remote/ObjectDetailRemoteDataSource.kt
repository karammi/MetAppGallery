package com.asad.metappgallery.detailScreen.data.dataSource.remote

import com.asad.metappgallery.core.data.DataResult
import com.asad.metappgallery.detailScreen.data.dataSource.remote.model.ObjectEntity

interface ObjectDetailRemoteDataSource {

    suspend fun fetchObjectDetail(objectID: Int): DataResult<ObjectEntity>
}
