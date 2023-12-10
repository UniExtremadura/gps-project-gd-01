package es.unex.giiis.marvelbook.ui.configuracion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import es.unex.giiis.marvelbook.MarvelApplication
import es.unex.giiis.marvelbook.database.Usuario
import es.unex.giiis.marvelbook.utils.Repository

class ConfiguracionViewModel(
    private val repository: Repository
) : ViewModel() {

    var user: Usuario? = null

    fun getUsuario() {
        user = repository.usuario
    }

    fun updateUsuario(usuario: Usuario) {
        repository.updateUsuario(usuario)
    }

    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])

                return ConfiguracionViewModel(
                    (application as MarvelApplication).appContainer.repository,

                    ) as T
            }
        }
    }
}
