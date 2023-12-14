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

class MazoDetallesViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _toast = MutableLiveData<String?>()
    val toast: LiveData<String?>
        get() = _toast
    fun getUsuario(): Usuario {
        return repository.usuario!!
    }

    fun venderPersonaje(id: Long) {
        viewModelScope.launch(Dispatchers.IO){
            val user = repository.usuario!!
            user.monedas = user.monedas?.plus(4)
            repository.updateUsuario(user)

            repository.deletePersonajeMazo(id)
            withContext(Dispatchers.Main) {
                _toast.value = "Personaje vendido por 4 monedas"
            }
        }
    }

    fun onToastShown() {
        _toast.value = null
    }


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

                return MazoDetallesViewModel(
                    (application as MarvelApplication).appContainer.repository,

                    ) as T
            }
        }
    }
}