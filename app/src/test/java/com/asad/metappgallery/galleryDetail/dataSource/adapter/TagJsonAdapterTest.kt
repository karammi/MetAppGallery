package com.asad.metappgallery.galleryDetail.dataSource.adapter

import com.google.common.truth.Truth.assertThat
import org.json.JSONObject
import org.junit.Before
import org.junit.Test

class TagJsonAdapterTest {

    private lateinit var sut: TagJsonAdapter

    @Before
    fun setup() {
        sut = TagJsonAdapter()
    }

    @Test
    fun whenCreateEntityFromAModel_shouldReturnAProperModel() {
        /**Arrange*/
        val json = JSONObject(
            """{
        "term": "Birds",
        "AAT_URL": "http://vocab.getty.edu/page/aat/300266506",
        "Wikidata_URL": "https://www.wikidata.org/wiki/Q5113"
    }""",
        )

        /**Act*/
        val result = sut.createEntityFromJson(json)
        /**Assert*/
        assertThat(result.term).isEqualTo("Birds")
        assertThat(result.AAT_URL).isEqualTo("http://vocab.getty.edu/page/aat/300266506")
        assertThat(result.Wikidata_URL).isEqualTo("https://www.wikidata.org/wiki/Q5113")
    }
}
