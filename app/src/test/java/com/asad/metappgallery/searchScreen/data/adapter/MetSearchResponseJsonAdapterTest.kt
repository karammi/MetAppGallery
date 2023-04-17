package com.asad.metappgallery.searchScreen.data.adapter

import com.google.common.truth.Truth.assertThat
import org.json.JSONObject
import org.junit.Before
import org.junit.Test

class MetSearchResponseJsonAdapterTest {

    private lateinit var sut: MetSearchResponseJsonAdapter

    @Before
    fun setup() {
        sut = MetSearchResponseJsonAdapter()
    }

    @Test
    fun whenCreateEntityFromJson_thenReturnProperModel() {
        // arrange
        val expectedTotal = 90
        val expectedObjectIDsSize = 28
        val expectedFirstElement = 436524
        val json = JSONObject(
            """
                {
                    "total":90,
                    "objectIDs":
                        [
                            436524,484935,437112,210191,
                            431264,397949,656530,480725,486590,485308,375281,705155,
                            11922,2032,343052,20141,2019,347980,208554,403496,437115,
                            207869,360837,400581,423400,707887,223828,682927
                        ]
                }
            """.trimIndent(),
        )

        // act
        val result = sut.createEntityFromJson(json)

        // assert
        assertThat(result.total).isEqualTo(expectedTotal)
        assertThat(result.objectIDs?.size).isEqualTo(expectedObjectIDsSize)
        assertThat(result.objectIDs?.first()).isEqualTo(expectedFirstElement)
    }

    @Test
    fun whenCreateEntityFromJson_thenReturnProperEmptyModel() {
        // arrange
        val expectedTotal = 0
        val json = JSONObject(
            """
               {"total":0,"objectIDs":null}
            """.trimIndent(),
        )

        // act
        val result = sut.createEntityFromJson(json)

        // assert
        assertThat(result.total).isEqualTo(expectedTotal)
        assertThat(result.objectIDs).isNull()
    }
}
