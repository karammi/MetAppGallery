package com.asad.metappgallery.galleryDetail.dataSource.dataSource

import com.asad.metappgallery.core.data.DataResult
import com.asad.metappgallery.galleryDetail.dataSource.model.ArtObject

interface GalleryDetailRemoteDataSource {

    suspend fun fetchObjectDetail(objectID: Int): DataResult<ArtObject>
}
