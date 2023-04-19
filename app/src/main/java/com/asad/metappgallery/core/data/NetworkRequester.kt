package com.asad.metappgallery.core.data

import com.asad.metappgallery.core.CoreString
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.nio.charset.MalformedInputException
import java.nio.charset.StandardCharsets
import javax.net.ssl.HttpsURLConnection

/**
 * This class is handle to all of our network requests and:
 *  - Ensures that every connection is secured using HTTPS protocol
 *  - Handles exceptions
 *  - Returns a [DataResult] of type [JSONObject]
 * */

private const val TAG = "NetworkRequester"

class NetworkRequester {

    suspend fun invoke(
        url: URL,
        coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
    ): DataResult<JSONObject> {
        return withContext(coroutineDispatcher) {
            var urlConnection: HttpsURLConnection? = null
            try {
                ensureActive()
                urlConnection = url.openConnection() as HttpsURLConnection
//                urlConnection.readTimeout = 60
//                urlConnection.connectTimeout = 60
                ensureActive()
                when (urlConnection.responseCode) {
                    200 -> {
                        val responseStream: InputStream =
                            BufferedInputStream(urlConnection.inputStream)
                        ensureActive()
                        val responseString =
                            String(responseStream.readBytes(), charset = StandardCharsets.UTF_8)
                        ensureActive()
                        DataResult.Success(JSONObject(responseString))
                    }

                    304 -> {
                        DataResult.Error(CustomNetworkException(CoreString.HTTP_304_ERROR_MESSAGE))
                    }

                    404 -> {
                        DataResult.Error(CustomNetworkException(CoreString.HTTP_404_ERROR_MESSAGE))
                    }

                    422 -> {
                        DataResult.Error(CustomNetworkException(CoreString.HTTP_422_ERROR_MESSAGE))
                    }

                    503 -> {
                        DataResult.Error(CustomNetworkException(CoreString.HTTP_503_ERROR_MESSAGE))
                    }

                    else -> {
                        DataResult.Error(CustomNetworkException(CoreString.HTTP_UNKNOWN_ERROR_MESSAGE))
                    }
                }
            } catch (e: MalformedInputException) {
                // This exception could be throw when creating URL
                DataResult.Error(CustomNetworkException(e.message))
            } catch (e: IOException) {
                // This exception could be thrown when calling `url.openConnection`
                DataResult.Error(CustomNetworkException(e.message))
            } finally {
                // [NonCancellable] is used so that when the coroutine is cancelled
                // we make sure that the urlConnection is closed
                withContext(NonCancellable) {
                    urlConnection?.disconnect()
                }
            }
        }
    }
}
