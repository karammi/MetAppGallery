package com.asad.metappgallery.detailScreen.dataSource.adapter

import com.google.common.truth.Truth.assertThat
import org.json.JSONObject
import org.junit.Before
import org.junit.Test

class ConstituentModelJsonAdapterTest {

    private lateinit var sut: ConstituentJsonAdapter

    @Before
    fun setup() {
        sut = ConstituentJsonAdapter()
    }

    @Test
    fun whenCreateEntityFromAObject_shouldReturnProperModel() {
        /**Arrange the requirement*/
        val json = JSONObject(
            """{
            "constituentID": 11986,
            "role": "Artist",
            "name": "Kiyohara Yukinobu",
            "constituentULAN_URL": "http://vocab.getty.edu/page/ulan/500034433",
            "constituentWikidata_URL": "https://www.wikidata.org/wiki/Q11560527",
            "gender": "Female"
        }
            """.trimIndent(),
        )

        val constituentIDExpected = 11986
        val roleExpected = "Artist"
        val nameExpected = "Kiyohara Yukinobu"
        val constituentULANURLExpected = "http://vocab.getty.edu/page/ulan/500034433"
        val constituentWikidataURLExpected = "https://www.wikidata.org/wiki/Q11560527"
        val genderExpected = "Female"

        /**Act the action (system under test)*/
        val result = sut.createEntityFromJson(json = json)

        /**Assert the result with your expected value*/
        assertThat(result.constituentId).isEqualTo(constituentIDExpected)
        assertThat(result.role).isEqualTo(roleExpected)
        assertThat(result.name).isEqualTo(nameExpected)
        assertThat(result.constituentULanURL).isEqualTo(constituentULANURLExpected)
        assertThat(result.constituentWikidataURL).isEqualTo(constituentWikidataURLExpected)
        assertThat(result.gender).isEqualTo(genderExpected)
    }
}
