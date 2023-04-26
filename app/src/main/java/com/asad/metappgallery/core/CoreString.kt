package com.asad.metappgallery.core

object CoreString {

    const val CustomNetworkImageLoadingTitle = "network_image_loading"
    const val CustomNetworkImageErrorTitle = "network_image_error"

    const val IO_EXCEPTION = "IO_EXCEPTION"
    const val SOCKET_TIMEOUT_EXCEPTION = "SOCKET_TIMEOUT_EXCEPTION"
    const val UNKNOWN_HOST_ERROR = "UNKNOWN_HOST_ERROR"
    const val UNKNOWN_ERROR = "UNKNOWN_ERROR"
    const val HTTP_EXCEPTION = "HttpException"
    const val CANCELLATION_EXCEPTION = "CancellationException"

    val buildErrorMessageBody: (String) -> String = { errorTitle ->
        "$errorTitle has occurred!"
    }
}
