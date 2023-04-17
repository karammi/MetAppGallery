package com.asad.metappgallery.galleryFinder.data.dataSource

import com.asad.metappgallery.core.data.DataResult
import com.asad.metappgallery.core.data.NetworkRequester
import com.asad.metappgallery.galleryFinder.data.adapter.GalleryFinderString
import com.asad.metappgallery.galleryFinder.data.adapter.ObjectIDSJsonAdapter
import com.asad.metappgallery.galleryFinder.data.model.ObjectIDResponse
import java.net.URL

class GalleryFinderRemoteDataSourceImpl constructor(
    private val networkRequester: NetworkRequester,
    private val objectIDSJsonAdapter: ObjectIDSJsonAdapter,
) : GalleryFinderRemoteDataSource {

    override suspend fun fetchGalleries(query: String): DataResult<ObjectIDResponse> {
        val jsonResult = networkRequester
            .invoke(URL(GalleryFinderString.FetchGalleriesEndPointApi(query)))

        return when (jsonResult) {
            is DataResult.Error -> {
                DataResult.Error(jsonResult.exception)
            }
            is DataResult.Success -> {
                DataResult.Success(value = objectIDSJsonAdapter.createEntityFromJson(jsonResult.value))
            }
        }
    }
}
