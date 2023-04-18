package com.asad.metappgallery.detailScreen.data.adapter

import com.google.common.truth.Truth.assertThat
import org.json.JSONObject
import org.junit.Before
import org.junit.Test

class ObjectModelJsonAdapterTest {

    private lateinit var sut: ObjectModelJsonAdapter
    private lateinit var constituentJsonAdapter: ConstituentJsonAdapter
    private lateinit var tagJsonAdapter: TagJsonAdapter

    @Before
    fun setup() {
        constituentJsonAdapter = ConstituentJsonAdapter()
        tagJsonAdapter = TagJsonAdapter()
        sut = ObjectModelJsonAdapter(
            constituentJsonAdapter = constituentJsonAdapter,
            tagJsonAdapter = tagJsonAdapter,
        )
    }

    @Test
    fun whenCrateEntityFromAObject_shouldReturnAProperObjectModel() {
        /**Arrange*/
        val json = JSONObject(
            """
    {
        "objectID": 45734,
        "isHighlight": false,
        "isPublicDomain": true,
        "primaryImage": "https://images.metmuseum.org/CRDImages/as/original/DP251139.jpg",
        "primaryImageSmall": "https://images.metmuseum.org/CRDImages/as/web-large/DP251139.jpg",
        "additionalImages": [
            "https://images.metmuseum.org/CRDImages/as/original/DP251138.jpg",
            "https://images.metmuseum.org/CRDImages/as/original/DP251120.jpg"
        ],
        "constituents": [
            {
                "constituentID": 11986,
                "role": "Artist",
                "name": "Kiyohara Yukinobu",
                "constituentULAN_URL": "http://vocab.getty.edu/page/ulan/500034433",
                "constituentWikidata_URL": "https://www.wikidata.org/wiki/Q11560527",
                "gender": "Female"
            }
        ],
        "department": "Asian Art",
        "objectName": "Hanging scroll",
        "title": "Quail and Millet",
        "culture": "Japan",
        "portfolio": "",
        "artistDisplayName": "Kiyohara Yukinobu",
        "artistDisplayBio": "Japanese, 1643–1682",
        "objectDate": "late 17th century",
        "objectBeginDate": 1667,
        "objectEndDate": 1682,
        "geographyType": "",
        "classification": "Paintings",
        "metadataDate": "2020-09-14T12:26:37.48Z",
        "repository": "Metropolitan Museum of Art, New York, NY",
        "objectURL": "https://www.metmuseum.org/art/collection/search/45734",
         "tags": [
                {
                    "term": "Birds",
                    "AAT_URL": "http://vocab.getty.edu/page/aat/300266506",
                    "Wikidata_URL": "https://www.wikidata.org/wiki/Q5113"
                }
            ],
         "GalleryNumber": ""
        }
            """.trimIndent(),
        )

        val expectedObjectId = 45734

        /**Act*/
        val result = sut.createEntityFromJson(json)

        /**Assert*/
        assertThat(result.objectID).isEqualTo(expectedObjectId)
    }
}
