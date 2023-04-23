package com.asad.metappgallery.detailScreen.data.dataSource

import com.asad.metappgallery.core.data.DataResult
import com.asad.metappgallery.detailScreen.data.model.ObjectModel

class FakeErrorObjectDetailRemoteDataSourceImpl : ObjectDetailRemoteDataSource {
    override suspend fun fetchObjectDetail(objectID: Int): DataResult<ObjectModel> {
        return DataResult.Error(exception = Exception("Oops! An error occurred!"))
    }
}
