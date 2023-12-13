package es.unex.giiis.marvelbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.preference.PreferenceManager
import es.unex.giiis.marvelbook.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val mainViewModel: MainViewModel by viewModels { MainViewModel.Factory }
    private val viewModel: LoginViewModel by viewModels { LoginViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.crearCuenta.setOnClickListener{
            val intent =  Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.botonInicio.setOnClickListener{

            viewModel.getUsuarioByEmail(binding.emailLogin.text.toString()) { usuario ->

                if (usuario != null) {

                    if (usuario.password == binding.passwordLogin.text.toString()) {

                        Toast.makeText(applicationContext,"Bienvenido, "+ usuario.nombre, Toast.LENGTH_SHORT).show()

                        val intent = Intent(this, MainActivity::class.java)
                        viewModel.sumarMonedas(usuario, this)

                        viewModel.toast.observe(this) {it ->
                            it?.let{
                                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                                viewModel.onToastShown()
                            }
                        }

                        mainViewModel.refrescarUsuario(usuario.id)
                        startActivity(intent)
                    } else {
                            binding.passwordLogin.error = "Las credenciales no son correctas"
                            binding.emailLogin.error = "Las credenciales no son correctas"
                    }
                } else {
                        binding.passwordLogin.error = "Las credenciales no son correctas"
                        binding.emailLogin.error = "Las credenciales no son correctas"
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