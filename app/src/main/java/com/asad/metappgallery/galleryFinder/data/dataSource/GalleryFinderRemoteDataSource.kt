package com.asad.metappgallery.galleryFinder.data.dataSource

import com.asad.metappgallery.core.data.DataResult
import com.asad.metappgallery.galleryFinder.data.model.ObjectIDResponse

interface GalleryFinderRemoteDataSource {

    suspend fun fetchGalleries(query: String): DataResult<ObjectIDResponse>
}
