package com.asad.metappgallery.searchScreen.data.dataSource

import com.asad.metappgallery.core.data.DataResult
import com.asad.metappgallery.core.data.NetworkRequester
import com.asad.metappgallery.searchScreen.data.adapter.MetSearchString
import com.asad.metappgallery.searchScreen.data.adapter.MetSearchResponseJsonAdapter
import com.asad.metappgallery.searchScreen.data.model.MetSearchResponse
import java.net.URL

class MetSearchRemoteDataSourceImpl constructor(
    private val networkRequester: NetworkRequester,
    private val metSearchResponseJsonAdapter: MetSearchResponseJsonAdapter,
) : MetSearchRemoteDataSource {

    override suspend fun fetchObjects(query: String): DataResult<MetSearchResponse> {
        val jsonResult = networkRequester
            .invoke(URL(MetSearchString.MetCollectionApi(query)))

        return when (jsonResult) {
            is DataResult.Error -> {
                DataResult.Error(jsonResult.exception)
            }

            is DataResult.Success -> {
                DataResult.Success(
                    value = metSearchResponseJsonAdapter.createEntityFromJson(
                        jsonResult.value,
                    ),
                )
            }
        }
    }
}
