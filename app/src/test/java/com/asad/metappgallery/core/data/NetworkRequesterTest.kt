package com.asad.metappgallery.core.data

import com.asad.metappgallery.searchScreen.data.adapter.MetSearchString
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.json.JSONObject
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.robolectric.RobolectricTestRunner
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.net.URL
import javax.net.ssl.HttpsURLConnection

@RunWith(RobolectricTestRunner::class)
class NetworkRequesterTest {
//    @Mock
//    private lateinit var mockUrl: URL
//
//    @Mock
//    private lateinit var mockConnection: HttpsURLConnection
    private lateinit var sut: NetworkRequester
    private lateinit var url: URL

    /** Creates a coroutine execution context using a single thread with built-in yield support.*/
    private val mainDispatcher = newSingleThreadContext("Ui Thread")

    @Before
    fun setup() {
        Dispatchers.setMain(mainDispatcher)
//        mockUrl = Mockito.mock(URL::class.java)
//        mockConnection = Mockito.mock(HttpsURLConnection::class.java)

        val query = "sunflower"
        url = URL(MetSearchString.MetCollectionApi(query))
        sut = NetworkRequester()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainDispatcher.close()
    }

   /* @Test
    fun whenStatusCode200_thenReturnResponse() = runBlocking {
        *//**Arrange*//*
        `when`(mockUrl.openConnection()).thenReturn(mockConnection)
        `when`(mockConnection.responseCode).thenReturn(200)

        val fakeResponse =
            """
                {
                    "total":28,
                    "objectIDs":436524
                }
            """.trimIndent()
        val fakeResponseStream: InputStream = ByteArrayInputStream(fakeResponse.toByteArray())
        `when`(mockConnection.inputStream).thenReturn(fakeResponseStream)

        *//**Act*//*
        val fakeModelJsonAdapter = FakeApiSuccessResponseJsonAdapter()

        val result = sut.invoke(mockUrl)

        val response = fakeModelJsonAdapter.createEntityFromJson(result.value!!)

        *//**Assert*//*
        assertThat(response).isEqualTo(FakeApiSuccessResponse(total = 28, objectIDs = 436524))
    }*/
}

private data class FakeApiSuccessResponse(
    val total: Int,
    val objectIDs: Int,
)

private class FakeApiSuccessResponseJsonAdapter : JsonAdapter<FakeApiSuccessResponse> {
    override fun createEntityFromJson(json: JSONObject): FakeApiSuccessResponse {
        return FakeApiSuccessResponse(
            json.getInt("total"),
            json.getInt("objectIDs"),
        )
    }
}
