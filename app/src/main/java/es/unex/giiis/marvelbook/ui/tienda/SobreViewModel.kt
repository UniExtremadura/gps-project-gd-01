package es.unex.giiis.marvelbook.ui.tienda

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import es.unex.giiis.marvelbook.MarvelApplication
import es.unex.giiis.marvelbook.database.PersonajeMazo
import es.unex.giiis.marvelbook.utils.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class SobreViewModel(
    private val repository: Repository
) : ViewModel() {

    fun generarSobreBasico(callback: (List<PersonajeMazo>) -> Unit) {

        viewModelScope.launch(Dispatchers.IO){
            val listPersonajes = repository.getAllCharacters()

            val numMax = listPersonajes.size
            val numeroAleatorio = Random.nextInt(numMax)

            val personajeSeleccionado = listPersonajes[numeroAleatorio]

            val personaje = PersonajeMazo(
                usuarioID = repository.usuario!!.id,
                name = personajeSeleccionado.name,
                imagen = personajeSeleccionado.imagen,
                speed = Random.nextInt(100),
                defense = Random.nextInt(100),
                power = Random.nextInt(100),
                rating = 0,
                fav = false
            )

            personaje.rating = (personaje.speed!! + personaje.defense!! + personaje.power!!) / 3

            val personajesMazo = mutableListOf<PersonajeMazo>()
            personajesMazo.add(personaje)

            repository.savePersonajeMazo(personaje)

            withContext(Dispatchers.Main) {
                callback(personajesMazo)
            }
        }
    }


    fun generarSobreEspecial(callback: (List<PersonajeMazo>) -> Unit) {

        viewModelScope.launch(Dispatchers.IO){
            val listPersonajes = repository.getAllCharacters().toMutableList()

            val personajesMazo = mutableListOf<PersonajeMazo>()
            for (i in 0..2 ){
                val numMax = listPersonajes.size
                val numeroAleatorio = Random.nextInt(numMax)

                val personajeSeleccionado = listPersonajes[numeroAleatorio]

                val personaje = PersonajeMazo(
                    usuarioID = repository.usuario!!.id,
                    name = personajeSeleccionado.name,
                    imagen = personajeSeleccionado.imagen,
                    speed = Random.nextInt(100),
                    defense = Random.nextInt(100),
                    power = Random.nextInt(100),
                    rating = 0,
                    fav = false
                )

                personaje.rating = (personaje.speed!! + personaje.defense!! + personaje.power!!) / 3
                personajesMazo.add(personaje)
                repository.savePersonajeMazo(personaje)
                listPersonajes.removeAt(numeroAleatorio)
            }

            withContext(Dispatchers.Main) {
                callback(personajesMazo)
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

                return SobreViewModel(
                    (application as MarvelApplication).appContainer.repository,

                    ) as T
            }
        }
    }
}