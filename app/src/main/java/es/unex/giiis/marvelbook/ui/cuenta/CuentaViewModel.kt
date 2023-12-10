package es.unex.giiis.marvelbook.ui.cuenta

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.preference.PreferenceManager
import es.unex.giiis.marvelbook.MarvelApplication
import es.unex.giiis.marvelbook.database.Usuario
import es.unex.giiis.marvelbook.utils.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CuentaViewModel(
    private val repository: Repository
) : ViewModel() {

    var usuarioEmail: Usuario? = null

    fun getUsuarioByEmail(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val usuarioAux = repository.findByEmail(email)
            withContext(Dispatchers.Main) {
                usuarioEmail = usuarioAux
            }
        }
    }

    fun getUsuario(): Usuario {
        return repository.usuario!!
    }

    fun updateUsuario(usuario: Usuario, context: Context) {
        viewModelScope.launch(Dispatchers.IO){
            repository.updateUsuario(usuario)
        }
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putString("email", usuario.email)
        editor.putString("password", usuario.password)
        editor.apply()
    }

    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])

                return CuentaViewModel(
                    (application as MarvelApplication).appContainer.repository,

                    ) as T
            }
        }
    }
}