package com.asad.metappgallery.detailScreen.data.dataSource.api

import com.asad.metappgallery.detailScreen.data.dataSource.remote.model.ObjectEntity
import retrofit2.http.GET
import retrofit2.http.Path

interface ObjectDetailApi {

    @GET("objects/{objectId}")
    suspend fun fetchObjectDetail(@Path("objectId") objectId: Int): ObjectEntity
}
