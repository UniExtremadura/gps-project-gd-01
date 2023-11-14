package es.unex.giiis.marvelbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import es.unex.giiis.marvelbook.database.AppDatabase
import es.unex.giiis.marvelbook.databinding.ActivityLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(applicationContext)

        binding.crearCuenta.setOnClickListener{
            val intent =  Intent(this, RegisterActivity::class.java);
            startActivity(intent)
        }

        binding.botonInicio.setOnClickListener{

            lifecycleScope.launch(Dispatchers.IO) {
                val email = binding.emailLogin.text.toString()
                val password = binding.passwordLogin.text.toString()

                val usuarioAUX = db.usuarioDAO().findByEmail(email);

                if(usuarioAUX != null){

                    if(usuarioAUX.password == password){

                        withContext(Dispatchers.Main) {
                            val context = this@LoginActivity

                            val nombreUsuario = usuarioAUX.nombre

                            val mensaje = "Bienvenido, $nombreUsuario"

                            Toast.makeText(applicationContext, mensaje, Toast.LENGTH_SHORT).show()

                            val intent = Intent(context, MainActivity::class.java);
                            val usuarioID = usuarioAUX.id
                            intent.putExtra("usuarioID", usuarioID)
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