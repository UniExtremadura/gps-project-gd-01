package es.unex.giiis.marvelbook.utils

import android.util.Log
import es.unex.giiis.marvelbook.api.APIError
import es.unex.giiis.marvelbook.api.MarvelAPI
import es.unex.giiis.marvelbook.data.api.toComic
import es.unex.giiis.marvelbook.data.api.toCreador
import es.unex.giiis.marvelbook.data.api.toPersonaje
import es.unex.giiis.marvelbook.database.Comic
import es.unex.giiis.marvelbook.database.ComicDAO
import es.unex.giiis.marvelbook.database.Creador
import es.unex.giiis.marvelbook.database.CreadorDAO
import es.unex.giiis.marvelbook.database.Personaje
import es.unex.giiis.marvelbook.database.PersonajeDAO

class Repository(
    private val personajeDAO: PersonajeDAO,
    private val comicDAO: ComicDAO,
    private val creadorDAO: CreadorDAO,
    private val marvelAPI: MarvelAPI,
) {
    val personajes = personajeDAO.getAll1()
    var comics = comicDAO.getAll1()
    val creador = creadorDAO.getAll1()

    fun getAllCharacters(): List<Personaje>{
        return personajeDAO.getAll()
    }

    fun getAllComics(): List<Comic>{
        return comicDAO.getAll()
    }

    fun getAllCreadores(): List<Creador>{
        return creadorDAO.getAll()
    }

    private suspend fun fetchCharacters() {
        try {
            for (i in 0..100 step 20) {
                for (aux in marvelAPI.getPersonajes(i).data?.results ?: listOf()) {
                    personajeDAO.insertarPersonaje(aux.toPersonaje())
                }
            }
        } catch (cause: Throwable) {
            throw APIError("Unable to fetch data from API", cause)
        }
    }

    suspend fun shouldFetchComics() {
        if(comicDAO.numeroComics() == 0L){
            fetchComics()
        }
    }

    private suspend fun fetchComics() {
        try {
            for (i in 0..100 step 20) {
                for (aux in marvelAPI.getComics(i).data?.results ?: listOf()) {
                    comicDAO.insertarComic(aux.toComic())
                }
            }
        } catch (cause: Throwable) {
            throw APIError("Unable to fetch data from API", cause)
        }
    }

    suspend fun shouldFetchCreador() {
        if(creadorDAO.numeroCreadores() == 0L){
            fetchCreador()
        }
    }

    private suspend fun fetchCreador() {
        try {
            for (i in 0..100 step 20) {
                for (aux in marvelAPI.getCreadores(i).data?.results ?: listOf()) {
                    creadorDAO.insertarCreador(aux.toCreador())
                }
            }
        } catch (cause: Throwable) {
            Log.d("ERROR", cause.message.toString())
            throw APIError("Unable to fetch data from API"+cause.message, cause)
        }
    }

    suspend fun shouldFetchCharacters() {
        if(personajeDAO.numeroPersonajes() == 0L){
            fetchCharacters()
        }
    }
}