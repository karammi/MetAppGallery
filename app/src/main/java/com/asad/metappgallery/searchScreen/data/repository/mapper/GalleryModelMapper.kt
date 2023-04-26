package com.asad.metappgallery.searchScreen.data.repository.mapper

import com.asad.metappgallery.core.data.ResponseMapper
import com.asad.metappgallery.searchScreen.data.dataSource.remote.model.GalleryResponseEntity
import com.asad.metappgallery.searchScreen.domain.model.GalleryResponseModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class GalleryModelMapper @Inject constructor() :
    ResponseMapper<GalleryResponseEntity, GalleryResponseModel> {
    override fun mapToModel(entity: GalleryResponseEntity): GalleryResponseModel {
        return with(entity) {
            GalleryResponseModel(
                total = total,
                objectIDs = objectIDs,
            )
        }
    }
}
