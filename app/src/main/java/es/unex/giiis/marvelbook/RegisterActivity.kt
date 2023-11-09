package es.unex.giiis.marvelbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.util.PatternsCompat
import androidx.lifecycle.lifecycleScope
import es.unex.giiis.marvelbook.databinding.ActivityRegisterBinding
import es.unex.giiis.marvelbook.database.AppDatabase
import es.unex.giiis.marvelbook.database.Usuario

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(applicationContext)

        binding.botonRegistro.setOnClickListener{

            if (validate()) {


                lifecycleScope.launch(Dispatchers.IO) {

                    val usuarioEmail = db.usuarioDAO().findByEmail(binding.emailRegistro.text.toString())

                    if(usuarioEmail != null) {
                        withContext(Dispatchers.Main) {
                            binding.emailRegistro.error = "Este email ya está registrado"
                        }
                    }
                    else{
                        val usuario = Usuario(
                            nombre = binding.nombreRegistro.text.toString(),
                            email = binding.emailRegistro.text.toString(),
                            password = binding.passwordRegistro.text.toString(),
                            monedas = 100,
                            mazoID = 0
                        )
                        db.usuarioDAO().insertarUsuario(usuario)

                        // Cambiar al hilo principal para mostrar el Toast y navegar a otra actividad
                        withContext(Dispatchers.Main) {
                            val context = this@RegisterActivity
                            Toast.makeText(applicationContext, "Se ha registrado correctamente", Toast.LENGTH_LONG).show()
                            val intent =  Intent(context, MainActivity::class.java);
                            startActivity(intent)
                        }
                    }
                }

            }

        }

    }

    private fun validate(): Boolean {
        val result = ArrayList<Boolean>()
        result.add(validateNombreRegistro())
        result.add(validateEmail())
        result.add(validatePassword())



        for (i in 0 until result.size) {
            if (!result[i]) {
                return false
            }
        }
        return true
    }


    private fun validateNombreRegistro(): Boolean {
        val nombreRegistro = binding.nombreRegistro.text.toString()
        if (nombreRegistro.isEmpty()) {
            binding.nombreRegistro.error = "El nombre de usuario no puede estar vacío"
            return false
        }
        return true
    }

    private fun validateEmail(): Boolean {
        val emailRegistro = binding.emailRegistro.text.toString()

        if (emailRegistro.isEmpty()) {
            binding.emailRegistro.error = "El email no puede estar vacío"
            return false
        } else if (!PatternsCompat.EMAIL_ADDRESS.matcher(emailRegistro).matches()) {
            binding.emailRegistro.error = "Introduzca un email de usuario válido"
            return false
        }
        return true;
    }



    private fun validatePassword(): Boolean {
        val password = binding.passwordRegistro.text.toString()
        val password2 = binding.passwordRegistro2.text.toString()
        val passwordRegex = Regex(
            "^" +
                    "(?=.*[0-9])" +  // Al menos 1 dígito
                    "(?=.*[a-zA-Z])" +  // Al menos una letra
                    "(?=\\S+$)" +  // Sin espacios en blanco
                    ".{6,}" +  // Al menos 6 dígitos
                    "$"
        )
        if (password.isNotEmpty()) {
            if (password2.isNotEmpty()) {
                if (passwordRegex.matches(password)) {
                    if (password == password2) {
                        return true
                    }
                    binding.passwordRegistro2.error = "La contraseña no coincide con la anterior"
                    return false
                }
                binding.passwordRegistro.error = "La contraseña es demasiado débil"
                return false
            }
            binding.passwordRegistro2.error = "La contraseña no puede estar vacía"
            return false
        }
        binding.passwordRegistro.error = "La contraseña no puede estar vacía"
        return false
    }


}