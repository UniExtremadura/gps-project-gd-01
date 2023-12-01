package es.unex.giiis.marvelbook.ui.coleccion.tab

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import es.unex.giiis.marvelbook.MarvelApplication
import es.unex.giiis.marvelbook.api.APIError
import es.unex.giiis.marvelbook.database.Comic
import es.unex.giiis.marvelbook.utils.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ComicViewModel(
    private val repository: Repository
) : ViewModel() {

    var comics = repository.comics

    private var _comicActual = MutableLiveData<List<Comic>?>()
    val comicActual: LiveData<List<Comic>?>
        get() = _comicActual

    private val _spinner = MutableLiveData<Boolean>()
    val spinner: LiveData<Boolean>
        get() = _spinner

    private val _toast = MutableLiveData<String?>()
    val toast: LiveData<String?>
        get() = _toast

    init{
        refresh()
    }

    private fun refresh() {
        launchDataLoad { repository.shouldFetchComics() }
        viewModelScope.launch(Dispatchers.IO) {
            val aux = repository.getAllComics()

            withContext(Dispatchers.Main){
                _comicActual.value = aux
            }
        }
    }


    fun performSearch(query: String) {
        viewModelScope.launch(Dispatchers.IO){
            val originalList = repository.getAllComics()
            val aux = originalList.filter { comic ->
                comic.title?.contains(query, ignoreCase = true) ?: true
            }

            withContext(Dispatchers.Main){
                _comicActual.value = aux
            }
        }
    }

    fun onToastShown() {
        _toast.value = null
    }

    private fun launchDataLoad(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                _spinner.value = true
                block()
            } catch (error: APIError) {
                _toast.value = error.message
            } finally {
                _spinner.value = false
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

                return ComicViewModel(
                    (application as MarvelApplication).appContainer.repository,

                    ) as T
            }
        }
    }

}