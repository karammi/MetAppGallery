package com.asad.metappgallery.detailScreen.domain.repository

import com.asad.metappgallery.core.data.DataResult
import com.asad.metappgallery.detailScreen.domain.model.ObjectModel

interface ObjectDetailRepository {
    suspend fun fetchObjectDetail(objectID: Int): DataResult<ObjectModel>
}
