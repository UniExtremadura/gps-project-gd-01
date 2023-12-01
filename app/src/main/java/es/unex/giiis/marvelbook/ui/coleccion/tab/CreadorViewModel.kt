package es.unex.giiis.marvelbook.ui.coleccion.tab

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import es.unex.giiis.marvelbook.MarvelApplication
import es.unex.giiis.marvelbook.api.APIError
import es.unex.giiis.marvelbook.database.Creador
import es.unex.giiis.marvelbook.utils.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreadorViewModel(
    private val repository: Repository
) : ViewModel() {

    var creador = repository.creador

    private var _creadorActual = MutableLiveData<List<Creador>?>()
    val creadorActual: LiveData<List<Creador>?>
        get() = _creadorActual

    private val _spinner = MutableLiveData<Boolean>()
    val spinner: LiveData<Boolean>
        get() = _spinner

    private val _toast = MutableLiveData<String?>()
    val toast: LiveData<String?>
        get() = _toast

    init {
        refresh()
    }

    private fun refresh() {
        launchDataLoad { repository.shouldFetchCreador() }
        viewModelScope.launch(Dispatchers.IO) {
            val aux = repository.getAllCreadores()

            withContext(Dispatchers.Main) {
                _creadorActual.value = aux
            }
        }
    }


    fun performSearch(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val originalList = repository.getAllCreadores()
            val aux = originalList.filter { creador ->
                creador.name?.contains(query, ignoreCase = true) ?: true
            }

            withContext(Dispatchers.Main) {
                _creadorActual.value = aux
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
                val application =
                    checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])

                return CreadorViewModel(
                    (application as MarvelApplication).appContainer.repository,

                    ) as T
            }
        }
    }

}