package com.asad.metappgallery.detailScreen.data.repository.mapper

import com.asad.metappgallery.core.data.ResponseMapper
import com.asad.metappgallery.detailScreen.data.dataSource.remote.model.ConstituentEntity
import com.asad.metappgallery.detailScreen.domain.model.ConstituentModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ConstituentModelMapper @Inject constructor() :
    ResponseMapper<ConstituentEntity, ConstituentModel> {
    override fun mapToModel(entity: ConstituentEntity): ConstituentModel {
        return with(entity) {
            ConstituentModel(
                constituentId,
                role,
                name,
                constituentULanURL,
                constituentWikidataURL,
                gender,
            )
        }
    }
}
