package es.unex.giiis.marvelbook.data.api

import es.unex.giiis.marvelbook.database.Comic
import es.unex.giiis.marvelbook.database.Personaje

import es.unex.giiis.marvelbook.database.Creador

fun CreadorMarvel.toCreador() = Creador(
    id = id ?: 0,
    name = fullName ?: "",
    imagen = thumbnail?.path + "." + thumbnail?.extension,
    comics = comics?.items?.mapNotNull { it.resourceURI?.substringAfterLast("/") } ?: emptyList()
)

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


fun PersonajeMarvel.toPersonaje() = Personaje(
    id = id ?: 0,
    name = name ?: "",
    description = description ?: "",
    imagen = thumbnail?.path + "." + thumbnail?.extension,
    comics = comics?.items?.mapNotNull { it.resourceURI?.substringAfterLast("/") } ?: emptyList()


)