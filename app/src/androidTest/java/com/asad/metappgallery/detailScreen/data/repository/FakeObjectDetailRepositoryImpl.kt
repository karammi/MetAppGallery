package com.asad.metappgallery.detailScreen.data.repository

import com.asad.metappgallery.core.data.DataResult
import com.asad.metappgallery.detailScreen.domain.model.ObjectModel
import com.asad.metappgallery.detailScreen.domain.model.TagModel
import com.asad.metappgallery.detailScreen.domain.repository.ObjectDetailRepository

class FakeObjectDetailRepositoryImpl : ObjectDetailRepository {
    override suspend fun fetchObjectDetail(objectID: Int): DataResult<ObjectModel> {
        return DataResult.Success(
            value = ObjectModel(
                objectID = 1,
                isHighlight = false,
                isPublicDomain = true,
                primaryImage = "https://images.metmuseum.org/CRDImages/ad/original/85I_ACF3100R5.jpg",
                primaryImageSmall = " https://images.metmuseum.org/CRDImages/ad/web-large/85I_ACF3100R5.jpg",
                additionalImages = listOf(
                    "https://images.metmuseum.org/CRDImages/ad/original/85P_PAINTDEC01R4.jpg",
                    "https://images.metmuseum.org/CRDImages/ad/original/257477.jpg",
                    "https://images.metmuseum.org/CRDImages/ad/original/258476.jpg",
                    "https://images.metmuseum.org/CRDImages/ad/original/258475.jpg",
                    "https://images.metmuseum.org/CRDImages/ad/original/197998.jpg",
                    "https://images.metmuseum.org/CRDImages/ad/original/17636.jpg",
                ),
                constituentModels = null,
                department = "The American Wing",
                objectName = "Chest with drawer",
                title = "Chest with drawer",
                culture = "American",
                portfolio = "",
                artistDisplayName = "",
                artistDisplayBio = "",
                objectDate = "1705",
                objectBeginDate = 1705,
                objectEndDate = 1705,
                classification = "",
                metadataDate = "2023-02-07 T04 :46:51.34 Z",
                repository = "Metropolitan Museum of Art, New York, NY",
                objectURL = "https://www.metmuseum.org/art/collection/search/2032",
                tagModels = listOf(
                    TagModel(
                        term = "Flowers",
                        aatUrl = "http://vocab.getty.edu/page/aat/300132399",
                        wikidataURL = "https://www.wikidata.org/wiki/Q506",
                    ),
                ),
            ),
        )
    }
}
