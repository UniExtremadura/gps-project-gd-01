package es.unex.giiis.marvelbook.data.api

import es.unex.giiis.marvelbook.database.Comic

fun ComicMarvel.toComic() = Comic(
    id = id ?: 0,
    title = title ?: "",
    description = description ?: "",
    isbn = isbn ?: "",
    format = format ?: "",
    pageCount = pageCount ?: 0,
    imagen = thumbnail?.path + "." + thumbnail?.extension,
    characters = characters?.items?.mapNotNull { it.resourceURI?.substringAfterLast("/") } ?: emptyList(),
    creators = creators?.items?.mapNotNull { it.resourceURI?.substringAfterLast("/") } ?: emptyList()
)