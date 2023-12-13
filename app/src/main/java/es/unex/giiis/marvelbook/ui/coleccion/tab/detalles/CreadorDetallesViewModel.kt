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
import es.unex.giiis.marvelbook.utils.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreadorDetallesViewModel (
    private val repository: Repository,
) : ViewModel() {

    var numComic: Int = 0
    private var _creadorDetalle = MutableLiveData<Creador?>()
    val creadorDetalle : LiveData<Creador?>
        get() = _creadorDetalle

    private var _listComic = MutableLiveData<MutableList<Comic>>()
    val listComic : LiveData<MutableList<Comic>>
        get() = _listComic

    var creadorID: Long = 0L
        set(value) {
            field = value
            getCreator()
        }

    private fun getCreator() {
        if (creadorID != 0L) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val _creador = repository.getCreatorById(creadorID)
                    if (_creador != null) {

                        withContext(Dispatchers.Main) {
                            _creadorDetalle.value = _creador
                        }
                        val listComicsID = _creador.comics

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

                return CreadorDetallesViewModel(
                    (application as MarvelApplication).appContainer.repository,
                ) as T
            }
        }
    }
}