package es.unex.giiis.marvelbook.ui.tienda

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import es.unex.giiis.marvelbook.MarvelApplication
import es.unex.giiis.marvelbook.database.Usuario
import es.unex.giiis.marvelbook.utils.Repository

class TiendaViewModel(
    private val repository: Repository
) : ViewModel() {

    fun getUsuario(): Usuario {
        return repository.usuario!!
    }

    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])

                return TiendaViewModel(
                    (application as MarvelApplication).appContainer.repository,

                    ) as T
            }
        }
    }
}