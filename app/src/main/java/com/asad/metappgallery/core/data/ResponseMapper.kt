package com.asad.metappgallery.core.data

interface ResponseMapper<E, M> {
    fun mapToModel(entity: E): M
}
