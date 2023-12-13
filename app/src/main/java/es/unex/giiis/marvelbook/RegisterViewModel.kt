package es.unex.giiis.marvelbook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import es.unex.giiis.marvelbook.database.Usuario
import es.unex.giiis.marvelbook.utils.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterViewModel(
    private val repository: Repository
) : ViewModel() {
    fun getUsuarioByEmail(email: String, callback: (Usuario?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val usuarioAux = repository.findByEmail(email)
            withContext(Dispatchers.Main) {
                callback(usuarioAux)
            }
        }
    }

    fun saveUsuario(usuario: Usuario) {
        viewModelScope.launch(Dispatchers.IO){
            repository.createUsuario(usuario)
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

                return RegisterViewModel(
                    (application as MarvelApplication).appContainer.repository,

                    ) as T
            }
        }
    }
}