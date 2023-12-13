package es.unex.giiis.marvelbook.ui.coleccion.tab.detalles

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import es.unex.giiis.marvelbook.MarvelApplication
import es.unex.giiis.marvelbook.api.APIError
import es.unex.giiis.marvelbook.database.Comic
import es.unex.giiis.marvelbook.database.Personaje
import es.unex.giiis.marvelbook.utils.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PersonajeDetallesViewModel (
    private val repository: Repository,
) : ViewModel() {

    var numComic: Int = 0
    private var _personajeDetalle = MutableLiveData<Personaje?>()
    val personajeDetalle : LiveData<Personaje?>
        get() = _personajeDetalle

    private var _listComic = MutableLiveData<MutableList<Comic>>()
    val listComic : LiveData<MutableList<Comic>>
        get() = _listComic

    var personajeID: Long = 0L
        set(value) {
            field = value
            getCharacter()
        }

    private fun getCharacter() {
        if (personajeID != 0L) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val _personaje = repository.getCharacterById(personajeID)
                    if (_personaje != null) {

                        withContext(Dispatchers.Main) {
                            _personajeDetalle.value = _personaje
                        }
                        val listComicsID = _personaje.comics

                        if (listComicsID != null) {
                            var listAux: MutableList<Comic> = mutableListOf()
                            for (aux in listComicsID) {
                                val comic = repository.getComicById(aux.toLong())
                                if (comic != null) {
                                    listAux.add(comic)
                                    numComic++
                                }
                            }
                            withContext(Dispatchers.Main) {
                                _listComic.value = listAux
                            }
                        }
                    }
                } catch (error: APIError) {

                }
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

                return PersonajeDetallesViewModel(
                    (application as MarvelApplication).appContainer.repository,
                ) as T
            }
        }
    }
}