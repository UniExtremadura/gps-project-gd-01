package es.unex.giiis.marvelbook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import es.unex.giiis.marvelbook.database.PersonajeMazo
import es.unex.giiis.marvelbook.database.Usuario
import es.unex.giiis.marvelbook.utils.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class BatallaViewModel(
    private val repository: Repository
) : ViewModel() {

    fun getUsuario(): Usuario {
        return repository.usuario!!
    }

    fun gestionarBatalla(personaje1: PersonajeMazo, personaje2: PersonajeMazo): Boolean {
        val ganador = personaje1.rating!! >= personaje2.rating!!

        val user = getUsuario()
        if (!ganador) {
            if (user.monedas!! < 5) {
                user.monedas = 0
            } else {
                user.monedas = user.monedas!! - 5
            }
        } else {
            user.monedas = user.monedas!! + 3
        }
        viewModelScope.launch(Dispatchers.IO){
            repository.updateUsuario(user)
        }
        return ganador
    }

    fun obtenerPersonajeID(id: Long, callback: (PersonajeMazo) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val personaje = repository.getPersonajeMazoByID(id)

            withContext(Dispatchers.Main) {
                callback(personaje)
            }
        }
    }

    fun obtenerPersonajeAleatorio(callback: (PersonajeMazo) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val listadoPersonajes = repository.getAllCharacters()

            val personajeElegido = listadoPersonajes[Random.nextInt(listadoPersonajes.size)]

            val personaje = PersonajeMazo(
                usuarioID = null,
                name = personajeElegido.name,
                imagen = personajeElegido.imagen,
                speed = Random.nextInt(100),
                defense = Random.nextInt(100),
                power = Random.nextInt(100),
                rating = 0,
                fav = false
            )

            personaje.rating = (personaje.speed!! + personaje.defense!! + personaje.power!!) / 3
            withContext(Dispatchers.Main) {
                callback(personaje)
            }
        }
    }

    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])

                return BatallaViewModel(
                    (application as MarvelApplication).appContainer.repository,

                    ) as T
            }
        }
    }
}