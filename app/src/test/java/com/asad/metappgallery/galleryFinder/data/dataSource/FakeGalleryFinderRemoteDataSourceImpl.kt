package com.asad.metappgallery.galleryFinder.data.dataSource

import com.asad.metappgallery.core.data.DataResult
import com.asad.metappgallery.galleryFinder.data.model.ObjectIDResponse

class FakeGalleryFinderRemoteDataSourceImpl : GalleryFinderRemoteDataSource {

    override suspend fun fetchGalleries(query: String): DataResult<ObjectIDResponse> {
        return if (query == "") {
            DataResult.Success(value = ObjectIDResponse(total = 0, objectIDs = null))
        } else {
            DataResult.Success(
                value = ObjectIDResponse(
                    total = 10,
                    objectIDs = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10),
                ),
            )
        }
    }
}
