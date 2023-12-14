package es.unex.giiis.marvelbook

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.preference.PreferenceManager
import es.unex.giiis.marvelbook.database.Usuario
import es.unex.giiis.marvelbook.utils.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

class LoginViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _toast = MutableLiveData<String?>()

    val toast: LiveData<String?>
        get() = _toast

    fun getUsuarioByEmail(email: String, callback: (Usuario?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val usuarioAux = repository.findByEmail(email)
            withContext(Dispatchers.Main) {
                callback(usuarioAux)
            }
        }
    }

    fun onToastShown(){
        _toast.value = null
    }
    fun sumarMonedas(usuario: Usuario, context: Context) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPreferences.edit()

        val lastLoginDate = sharedPreferences.getLong("LastLoginDate", 0)
        val currentDate = System.currentTimeMillis()

        if (!isSameHour(lastLoginDate, currentDate)) {
            val currentCoins = usuario.monedas
            val newCoins = currentCoins?.plus(100)
            if (newCoins != null) {
                usuario.monedas = newCoins
                viewModelScope.launch(Dispatchers.IO){
                    repository.updateUsuario(usuario)
                }
                _toast.value = "Has ganado 100 monedas"
            }

            editor.putLong("LastLoginDate", currentDate)
            editor.apply()
        }
    }

    private fun isSameHour(timestamp1: Long, timestamp2: Long): Boolean {
        val cal1 = Calendar.getInstance().apply { timeInMillis = timestamp1 }
        val cal2 = Calendar.getInstance().apply { timeInMillis = timestamp2 }
        return cal1.get(Calendar.HOUR) == cal2.get(Calendar.HOUR)
    }

    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])

                return LoginViewModel(
                    (application as MarvelApplication).appContainer.repository,

                    ) as T
            }
        }
    }
}