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
import es.unex.giiis.marvelbook.database.PersonajeMazo
import es.unex.giiis.marvelbook.database.PersonajeMazoDAO
import es.unex.giiis.marvelbook.database.Usuario
import es.unex.giiis.marvelbook.database.UsuarioDAO

class Repository(
    private val personajeDAO: PersonajeDAO,
    private val comicDAO: ComicDAO,
    private val creadorDAO: CreadorDAO,
    private val usuarioDAO: UsuarioDAO,
    private val personajeMazoDAO: PersonajeMazoDAO,
    private val marvelAPI: MarvelAPI,
) {
    val personajes = personajeDAO.getAll1()
    var comics = comicDAO.getAll1()
    val creador = creadorDAO.getAll1()
    var usuario: Usuario? = null

    fun setUsuario(id: Long) {
        usuario = usuarioDAO.getUserById(id)
    }

    fun findByEmail(email: String): Usuario? {
        return usuarioDAO.findByEmail(email)
    }

    fun getCharacterById(id:Long): Personaje?{
        return personajeDAO.getByID(id)
    }

    fun getComicById(id:Long): Comic?{
        return comicDAO.getById(id)
    }

    fun getCreatorById(id:Long): Creador?{
        return creadorDAO.getByID(id)
    }

    fun updateUsuario(usuario: Usuario) {
        usuarioDAO.updateUsuario(usuario)
        this.usuario = usuario
    }

    fun createUsuario(usuario: Usuario) {
        usuarioDAO.insertarUsuario(usuario)
        this.usuario = usuario
    }
    fun getAllPersonajeMazo(id: Long): List<PersonajeMazo> {
        return personajeMazoDAO.getAll(id)
    }
    fun getPersonajeMazoByID(id: Long): PersonajeMazo {
        return personajeMazoDAO.getById(id)
    }

    fun savePersonajeMazo(personajeMazo: PersonajeMazo) {
        return personajeMazoDAO.insertarPersonajeMazo(personajeMazo)
    }
    fun updatePersonajeMazo(personajeMazo: PersonajeMazo) {
        return personajeMazoDAO.updatePersonajeMazo(personajeMazo)
    }

    fun deletePersonajeMazo(id: Long) {
        return personajeMazoDAO.eliminarPersonajesMazo(id)
    }

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