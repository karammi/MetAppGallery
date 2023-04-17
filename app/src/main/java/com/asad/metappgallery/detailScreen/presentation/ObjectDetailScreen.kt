package com.asad.metappgallery.detailScreen.presentation

import com.asad.metappgallery.detailScreen.dataSource.model.TagModel

object ObjectDetailScreen {

    val objectName: (String?) -> String = {
        "Object name: ${it ?: "-"}"
    }

    val artistDisplayName: (String?) -> String = {
        "Artist display name: ${it ?: "-"}"
    }

    val department: (String?) -> String = {
        "Department: ${it ?: "-"}"
    }

    val culture: (String?) -> String = {
        "Culture: ${it ?: "-"}"
    }

    val classification: (String?) -> String = {
        "Classification: ${it ?: "-"}"
    }

    val portfolio: (String?) -> String = {
        "Portfolio: ${it ?: "-"}"
    }

    val objectDate: (String?) -> String = {
        "ObjectDate: ${it ?: "-"}"
    }

    val tagList: (List<TagModel>?) -> String = { tags ->
        var temp = ""
        var result = tags?.forEach {
            temp = "$temp, #${it.term}"
        }
        if (result == null) {
            temp = "-"
        }
        temp
    }
}
