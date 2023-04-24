package com.asad.metappgallery.detailScreen.data.repository.mapper

import com.asad.metappgallery.core.data.ResponseMapper
import com.asad.metappgallery.detailScreen.data.dataSource.remote.model.ObjectEntity
import com.asad.metappgallery.detailScreen.domain.model.ObjectModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ObjectModelMapper @Inject constructor(
    private val tagModelMapper: TagModelMapper,
    private val constituentModelMapper: ConstituentModelMapper,
) : ResponseMapper<ObjectEntity, ObjectModel> {
    override fun mapToModel(entity: ObjectEntity): ObjectModel {
        return with(entity) {
            ObjectModel(
                objectID = objectID,
                isHighlight = isHighlight,
                isPublicDomain = isPublicDomain,
                primaryImage = primaryImage,
                primaryImageSmall = primaryImageSmall,
                additionalImages = additionalImages,
                constituentModels = constituentEntities?.map {
                    constituentModelMapper.mapToModel(
                        it,
                    )
                },
                department = department,
                objectName = objectName,
                title = title,
                culture = culture,
                portfolio = portfolio,
                artistDisplayName = artistDisplayName,
                artistDisplayBio = artistDisplayBio,
                objectDate = objectDate,
                objectBeginDate = objectBeginDate,
                objectEndDate = objectEndDate,
                classification = classification,
                metadataDate = metadataDate,
                repository = repository,
                objectURL = objectURL,
                tagModels = tagEntities?.map { tagModelMapper.mapToModel(it) },
            )
        }
    }
}
