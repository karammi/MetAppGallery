package com.asad.metappgallery.core

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.io.InputStreamReader

class MockServerDispatcher {

    fun successDispatcher(map: Map<String, String>): Dispatcher {
        return object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when (request.path) {
                    "search" -> {
                        var json = ""
                        if (map.containsKey("search")) {
                            json = map["search"]!!
                        }

                        val body = getJsonContent(json)
                        MockResponse()
                            .setResponseCode(200)
                            .setBody(body)
                    }

                    "objects/" -> {
                        var json = "objects/"
                        if (map.containsKey("objects/")) {
                            json = map["objects/"]!!
                        }

                        val body = getJsonContent(json)
                        MockResponse()
                            .setResponseCode(200)
                            .setBody(body)
                    }

                    else -> {
                        MockResponse().setResponseCode(200).setBody("")
                    }
                }
            }
        }
    }

    private fun getJsonContent(fileName: String): String {
        // Returns the runtime Java class of this object.
        return InputStreamReader(this.javaClass.classLoader!!.getResourceAsStream(fileName)).use { it.readText() }
    }
}
