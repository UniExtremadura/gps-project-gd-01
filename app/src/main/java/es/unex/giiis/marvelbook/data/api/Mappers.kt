package es.unex.giiis.marvelbook.data.api

import es.unex.giiis.marvelbook.database.Personaje

fun PersonajeMarvel.toPersonaje() = Personaje(
    id = id ?: 0,
    name = name ?: "",
    description = description ?: "",
    imagen = thumbnail?.path + "." + thumbnail?.extension,
    comics = comics?.items?.mapNotNull { it.resourceURI?.substringAfterLast("/") } ?: emptyList()
)