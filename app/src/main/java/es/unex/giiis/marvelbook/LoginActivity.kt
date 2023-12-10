package es.unex.giiis.marvelbook

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import es.unex.giiis.marvelbook.database.AppDatabase
import es.unex.giiis.marvelbook.database.Usuario
import es.unex.giiis.marvelbook.databinding.ActivityLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var db: AppDatabase
    private lateinit var sharedPreferences: SharedPreferences
    private val mainViewModel: MainViewModel by viewModels { MainViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(applicationContext)

        binding.crearCuenta.setOnClickListener{
            val intent =  Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

        binding.botonInicio.setOnClickListener{

            lifecycleScope.launch(Dispatchers.IO) {
                val email = binding.emailLogin.text.toString()
                val password = binding.passwordLogin.text.toString()

                val usuarioAUX = db.usuarioDAO().findByEmail(email)

                if(usuarioAUX != null){

                    if(usuarioAUX.password == password){
                        val usuarioLog = db.usuarioDAO().getUserById(usuarioAUX.id)
                        withContext(Dispatchers.Main) {
                            val context = this@LoginActivity

                            val nombreUsuario = usuarioAUX.nombre

                            val mensaje = "Bienvenido, $nombreUsuario"

                            Toast.makeText(applicationContext, mensaje, Toast.LENGTH_SHORT).show()

                            val intent = Intent(context, MainActivity::class.java)
                            val usuarioID = usuarioAUX.id
                            if (usuarioLog != null) {
                                sumarMonedas(usuarioLog)
                            }
                            mainViewModel.refrescarUsuario(usuarioID)
                            startActivity(intent)
                        }
                    }
                    else{
                        withContext(Dispatchers.Main) {
                            binding.passwordLogin.error = "Las credenciales no son correctas"
                            binding.emailLogin.error = "Las credenciales no son correctas"
                        }
                    }
                }
                else{
                    withContext(Dispatchers.Main) {
                        binding.passwordLogin.error = "Las credenciales no son correctas"
                        binding.emailLogin.error = "Las credenciales no son correctas"
                    }
                }
            }
        }
        readSettings()
    }

    private fun sumarMonedas(usuario: Usuario) {
        val editor = sharedPreferences.edit()

        val lastLoginDate = sharedPreferences.getLong("LastLoginDate", 0)
        val currentDate = System.currentTimeMillis()

        if (!isSameHour(lastLoginDate, currentDate)) {
            val currentCoins = usuario.monedas
            val newCoins = currentCoins?.plus(100)
            if (newCoins != null) {
                usuario.monedas = newCoins
                Toast.makeText(this, "Has ganado 100 monedas", Toast.LENGTH_SHORT).show()
                lifecycleScope.launch(Dispatchers.IO) {
                    db.usuarioDAO().updateUsuario(usuario)
                }
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

    private fun readSettings() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val rememberme = preferences.getBoolean("rememberme", false)
        val email = preferences.getString("email", "")
        val password = preferences.getString("password", "")

        if (rememberme) {
            binding.emailLogin.setText(email)
            binding.passwordLogin.setText(password)
        }
    }

}