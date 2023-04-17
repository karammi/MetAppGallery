package com.asad.metappgallery.galleryDetail.dataSource.dataSource

import com.asad.metappgallery.core.data.DataResult
import com.asad.metappgallery.core.data.NetworkRequester
import com.asad.metappgallery.galleryDetail.dataSource.adapter.ArtObjectConstants
import com.asad.metappgallery.galleryDetail.dataSource.adapter.ArtObjectJsonAdapter
import com.asad.metappgallery.galleryDetail.dataSource.model.ArtObject
import java.net.URL

class GalleryDetailRemoteDataSourceImpl constructor(
    private val networkRequester: NetworkRequester,
    private val artObjectJsonAdapter: ArtObjectJsonAdapter,
) : GalleryDetailRemoteDataSource {
    override suspend fun fetchObjectDetail(objectID: Int): DataResult<ArtObject> {
        // fetching object form endpoint api
        val jsonResult = networkRequester.invoke(
            url = URL(
                ArtObjectConstants.FetchObjectDetailEndpointApi(objectID),
            ),
        )

        // handling response depends on its result
        return when (jsonResult) {
            is DataResult.Error -> {
                DataResult.Error(exception = jsonResult.exception)
            }
            is DataResult.Success -> {
                DataResult.Success(value = artObjectJsonAdapter.createEntityFromJson(jsonResult.value))
            }
        }
    }
}
