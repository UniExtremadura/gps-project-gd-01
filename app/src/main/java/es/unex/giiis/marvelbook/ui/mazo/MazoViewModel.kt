package es.unex.giiis.marvelbook.ui.mazo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import es.unex.giiis.marvelbook.MarvelApplication
import es.unex.giiis.marvelbook.database.PersonajeMazo
import es.unex.giiis.marvelbook.database.Usuario
import es.unex.giiis.marvelbook.utils.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MazoViewModel(
    private val repository: Repository
) : ViewModel() {

    private var _personajesMazo = MutableLiveData<List<PersonajeMazo>?>()
    val personajesMazo: LiveData<List<PersonajeMazo>?>
        get() = _personajesMazo

    private val searchTerm = MutableLiveData<String>()

    init {
        getAllPersonajeMazo()
    }

    fun setSearchTerm(term: String) {
        searchTerm.value = term
    }

    fun getSearchTerm() = searchTerm

    fun getUsuario(): Usuario {
        return repository.usuario!!
    }

    fun updatePersonajeMazo(personajeMazo: PersonajeMazo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updatePersonajeMazo(personajeMazo)
            getAllPersonajeMazo()
        }
    }

    fun getAllPersonajeMazo() {
        viewModelScope.launch(Dispatchers.IO) {
            val aux = repository.getAllPersonajeMazo(repository.usuario!!.id).toMutableList()
            withContext(Dispatchers.Main) {
                _personajesMazo.value = aux
            }
        }
    }

    fun performSearch(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val originalList = repository.getAllPersonajeMazo(repository.usuario!!.id)
            val aux = originalList.filter { personajeMazo ->
                personajeMazo.name?.contains(query, ignoreCase = true) ?: true
            }

            withContext(Dispatchers.Main) {
                _personajesMazo.value = aux
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
                val application =
                    checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])

                return MazoViewModel(
                    (application as MarvelApplication).appContainer.repository,

                    ) as T
            }
        }
    }
}