package com.asad.metappgallery.searchScreen.data.dataSource

import com.asad.metappgallery.core.data.DataResult
import com.asad.metappgallery.searchScreen.data.model.GalleryResponse

class FakeErrorGalleryRemoteDataSourceImpl : GalleryRemoteDataSource {
    override suspend fun fetchList(query: String): DataResult<GalleryResponse> {
        return DataResult.Error(exception = Exception("Oops!,An error occurred!"))
    }
}
