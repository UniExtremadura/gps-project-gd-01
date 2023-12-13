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
import es.unex.giiis.marvelbook.database.Creador
import es.unex.giiis.marvelbook.database.Personaje
import es.unex.giiis.marvelbook.utils.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ComicDetallesViewModel (
    private val repository: Repository,
) : ViewModel() {

    var numCreador: Int = 0
    var numPersonaje: Int = 0
    private var _comicDetalle = MutableLiveData<Comic?>()
    val comicDetalle : LiveData<Comic?>
        get() = _comicDetalle

    private var _listCreator = MutableLiveData<MutableList<Creador>>()
    val listCreator : LiveData<MutableList<Creador>>
        get() = _listCreator

    private var _listCharacter = MutableLiveData<MutableList<Personaje>>()
    val listCharacter : LiveData<MutableList<Personaje>>
        get() = _listCharacter

    var comicID: Long = 0L
        set(value) {
            field = value
            getComic()
        }

    private fun getComic() {
        if (comicID != 0L) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val _comic = repository.getComicById(comicID)
                    if (_comic != null) {

                        withContext(Dispatchers.Main) {
                            _comicDetalle.value = _comic
                        }
                        val listCreadoresID = _comic.creators
                        val listPersonajeID = _comic.characters

                        if (listCreadoresID != null) {
                            var listCr: MutableList<Creador> = mutableListOf()
                            for (aux in listCreadoresID) {
                                val creador = repository.getCreatorById(aux.toLong())
                                if (creador != null) {
                                    listCr.add(creador)
                                    numCreador++
                                }
                            }
                            withContext(Dispatchers.Main) {
                                _listCreator.value = listCr
                            }
                        }

                        if (listPersonajeID != null) {
                            var listPers: MutableList<Personaje> = mutableListOf()
                            for (aux in listPersonajeID) {
                                val personaje = repository.getCharacterById(aux.toLong())
                                if (personaje != null) {
                                    listPers.add(personaje)
                                    numPersonaje++
                                }
                            }
                            withContext(Dispatchers.Main) {
                                _listCharacter.value = listPers
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

                return ComicDetallesViewModel(
                    (application as MarvelApplication).appContainer.repository,
                ) as T
            }
        }
    }
}