package com.asad.metappgallery.detailScreen.data.repository

import com.asad.metappgallery.core.data.DataResult
import com.asad.metappgallery.detailScreen.domain.model.ObjectModel
import com.asad.metappgallery.detailScreen.domain.repository.ObjectDetailRepository

class FakeErrorObjectDetailRepositoryImpl : ObjectDetailRepository {
    override suspend fun fetchObjectDetail(objectID: Int): DataResult<ObjectModel> {
        return DataResult.Error(errorMessage = "Oops! An error occurred!")
    }
}