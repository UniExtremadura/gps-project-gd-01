package es.unex.giiis.marvelbook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import es.unex.giiis.marvelbook.database.PersonajeMazo
import es.unex.giiis.marvelbook.utils.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PeleaResultadoViewModel(
    private val repository: Repository
) : ViewModel() {

    fun obtenerPersonajeID(id: Long, callback: (PersonajeMazo) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val personaje = repository.getPersonajeMazoByID(id)

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

                return PeleaResultadoViewModel(
                    (application as MarvelApplication).appContainer.repository,

                    ) as T
            }
        }
    }

}