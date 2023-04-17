package com.asad.metappgallery.detailScreen.dataSource.dataSource

import com.asad.metappgallery.core.data.DataResult
import com.asad.metappgallery.core.data.NetworkRequester
import com.asad.metappgallery.detailScreen.dataSource.adapter.ObjectConstants
import com.asad.metappgallery.detailScreen.dataSource.adapter.ObjectModelJsonAdapter
import com.asad.metappgallery.detailScreen.dataSource.model.ObjectModel
import java.net.URL

class ObjectDetailRemoteDataSourceImpl constructor(
    private val networkRequester: NetworkRequester,
    private val objectModelJsonAdapter: ObjectModelJsonAdapter,
) : ObjectDetailRemoteDataSource {
    override suspend fun fetchObjectDetail(objectID: Int): DataResult<ObjectModel> {
        // fetching object form endpoint api
        val jsonResult = networkRequester.invoke(
            url = URL(ObjectConstants.FetchObjectDetailEndpointApi(objectID)),
        )

        // handling response depends on its result
        return when (jsonResult) {
            is DataResult.Error -> {
                DataResult.Error(exception = jsonResult.exception)
            }

            is DataResult.Success -> {
                DataResult.Success(value = objectModelJsonAdapter.createEntityFromJson(jsonResult.value))
            }
        }
    }
}
