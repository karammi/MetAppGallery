package com.asad.metappgallery.searchScreen.data.dataSource.api

import com.asad.metappgallery.searchScreen.data.dataSource.remote.model.GalleryResponseEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface GalleryApi {

    @GET("search")
    suspend fun fetchList(@Query("q") queryString: String): GalleryResponseEntity
}
