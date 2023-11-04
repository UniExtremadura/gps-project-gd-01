package es.unex.giiis.marvelbook.data.api

import es.unex.giiis.marvelbook.database.Creador

fun CreadorMarvel.toCreador() = Creador(
    id = id ?: 0,
    name = fullName ?: "",
    imagen = thumbnail?.path + "." + thumbnail?.extension,
    comics = comics?.items?.mapNotNull { it.resourceURI?.substringAfterLast("/") } ?: emptyList()
)