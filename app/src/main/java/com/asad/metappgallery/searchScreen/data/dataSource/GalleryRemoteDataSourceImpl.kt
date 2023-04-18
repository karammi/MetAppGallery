package com.asad.metappgallery.searchScreen.data.dataSource

import com.asad.metappgallery.core.data.DataResult
import com.asad.metappgallery.core.data.NetworkRequester
import com.asad.metappgallery.searchScreen.data.adapter.GalleryResponseConstant
import com.asad.metappgallery.searchScreen.data.adapter.GalleryResponseJsonAdapter
import com.asad.metappgallery.searchScreen.data.model.GalleryResponse
import java.net.URL

class GalleryRemoteDataSourceImpl constructor(
    private val networkRequester: NetworkRequester,
    private val galleryResponseJsonAdapter: GalleryResponseJsonAdapter,
) : GalleryRemoteDataSource {

    override suspend fun fetchList(query: String): DataResult<GalleryResponse> {
        val jsonResult = networkRequester
            .invoke(URL(GalleryResponseConstant.GalleryCollectionApi(query)))

        return when (jsonResult) {
            is DataResult.Error -> {
                DataResult.Error(jsonResult.exception)
            }

            is DataResult.Success -> {
                DataResult.Success(
                    value = galleryResponseJsonAdapter.createEntityFromJson(
                        jsonResult.value,
                    ),
                )
            }
        }
    }
}
