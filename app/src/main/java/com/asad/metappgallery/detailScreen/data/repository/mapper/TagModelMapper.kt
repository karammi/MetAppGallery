package com.asad.metappgallery.detailScreen.data.repository.mapper

import com.asad.metappgallery.core.data.ResponseMapper
import com.asad.metappgallery.detailScreen.data.dataSource.remote.model.TagEntity
import com.asad.metappgallery.detailScreen.domain.model.TagModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class TagModelMapper @Inject constructor() : ResponseMapper<TagEntity, TagModel> {
    override fun mapToModel(entity: TagEntity): TagModel {
        return TagModel(
            term = entity.term,
            aatUrl = entity.aatUrl,
            wikidataURL = entity.wikidataURL,
        )
    }
}
