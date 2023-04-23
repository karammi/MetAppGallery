package com.asad.metappgallery.searchScreen.data.dataSource

import com.asad.metappgallery.core.data.DataResult
import com.asad.metappgallery.core.data.NetworkRequester
import com.asad.metappgallery.searchScreen.data.adapter.DepartmentResponseJsonAdapter
import com.asad.metappgallery.searchScreen.data.adapter.GalleryResponseJsonAdapter
import com.asad.metappgallery.searchScreen.data.adapter.GallerySearchConstant
import com.asad.metappgallery.searchScreen.data.model.DepartmentResponse
import com.asad.metappgallery.searchScreen.data.model.GalleryResponse
import java.net.URL

class GalleryRemoteDataSourceImpl constructor(
    private val networkRequester: NetworkRequester,
    private val galleryResponseJsonAdapter: GalleryResponseJsonAdapter,
    private val departmentResponseJsonAdapter: DepartmentResponseJsonAdapter,
) : GalleryRemoteDataSource {

    override suspend fun fetchList(
        query: String,
        isHighlight: Boolean?,
    ): DataResult<GalleryResponse> {
        val jsonResponse = networkRequester
            .invoke(URL(GallerySearchConstant.GalleryCollectionApi(query, isHighlight)))

        return when (jsonResponse) {
            is DataResult.Error -> {
                DataResult.Error(jsonResponse.exception)
            }

            is DataResult.Success -> {
                DataResult.Success(
                    value = galleryResponseJsonAdapter.createEntityFromJson(
                        jsonResponse.value,
                    ),
                )
            }
        }
    }

    override suspend fun fetchDepartments(): DataResult<DepartmentResponse> {
        val jsonResponse =
            networkRequester.invoke(URL(GallerySearchConstant.DepartmentCollectionApi))

        return when (jsonResponse) {
            is DataResult.Error -> DataResult.Error(jsonResponse.exception)
            is DataResult.Success -> DataResult.Success(
                value = departmentResponseJsonAdapter.createEntityFromJson(jsonResponse.value),
            )
        }
    }
}
