package es.unex.giiis.marvelbook.ui.cuenta

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.util.PatternsCompat
import androidx.lifecycle.lifecycleScope
import es.unex.giiis.marvelbook.MainActivity
import es.unex.giiis.marvelbook.database.AppDatabase
import es.unex.giiis.marvelbook.database.Usuario
import es.unex.giiis.marvelbook.databinding.FragmentCuentaBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CuentaFragment : Fragment() {

    private var _binding: FragmentCuentaBinding? = null
    private lateinit var db: AppDatabase
    private val binding get() = _binding!!
    private var usuarioSesionID: Long = 0L
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCuentaBinding.inflate(inflater, container, false)
        db = AppDatabase.getInstance(requireContext())
        usuarioSesionID = arguments?.getLong("usuarioID")!!

        cargarInformacionUsuario(usuarioSesionID)

        binding.botonModificarUsuario.setOnClickListener {
            modificarUsuario()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun modificarUsuario() {
        lifecycleScope.launch(Dispatchers.IO) {

            val emailNuevo = binding.emailSettings.text.toString()
            val user = db.usuarioDAO().getUserById(usuarioSesionID)
            val usuarioEmail = db.usuarioDAO().findByEmail(emailNuevo)

            withContext(Dispatchers.Main) {
                if (user != null) {
                    if (user.email.toString() == emailNuevo || usuarioEmail == null) {

                        if (validate()) {
                            val usuario = Usuario(
                                id = usuarioSesionID,
                                nombre = binding.nameSetting.text.toString(),
                                email = binding.emailSettings.text.toString(),
                                password = binding.passwordSettings.text.toString(),
                                monedas = user.monedas,
                            )

                            lifecycleScope.launch(Dispatchers.IO) {
                                db.usuarioDAO().updateUsuario(usuario)
                            }

                            Toast.makeText(
                                requireContext(),
                                "Se ha modificado el usuario correctamente",
                                Toast.LENGTH_LONG
                            ).show()
                            val intent = Intent(requireContext(), MainActivity::class.java)
                            intent.putExtra("usuarioID", usuarioSesionID)
                            startActivity(intent)

                        }
                    } else {
                        binding.emailSettings.error = "Este email ya está registrado"
                    }
                }
            }
        }
    }

    private fun validate(): Boolean {
        val result = ArrayList<Boolean>()
        result.add(validateNombre())
        result.add(validateEmail())
        result.add(validatePassword())



        for (i in 0 until result.size) {
            if (!result[i]) {
                return false
            }
        }
        return true
    }


    private fun validateNombre(): Boolean {
        val nombreModificado = binding.nameSetting.text.toString()
        if (nombreModificado.isEmpty()) {
            binding.nameSetting.error = "El nombre de usuario no puede estar vacío"
            return false
        }
        return true
    }

    private fun validateEmail(): Boolean {
        val emailSettings = binding.emailSettings.text.toString()

        if (emailSettings.isEmpty()) {
            binding.emailSettings.error = "El email no puede estar vacío"
            return false
        } else if (!PatternsCompat.EMAIL_ADDRESS.matcher(emailSettings).matches()) {
            binding.emailSettings.error = "Introduzca un email de usuario válido"
            return false
        }
        return true
    }


    private fun validatePassword(): Boolean {
        val password = binding.passwordSettings.text.toString()
        val password2 = binding.password2Settings.text.toString()
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
                    binding.password2Settings.error = "La contraseña no coincide con la anterior"
                    return false
                }
                binding.passwordSettings.error = "La contraseña es demasiado débil"
                return false
            }
            binding.password2Settings.error = "La contraseña no puede estar vacía"
            return false
        }
        binding.passwordSettings.error = "La contraseña no puede estar vacía"
        return false
    }

    private fun cargarInformacionUsuario(usuarioID: Long) {
        lifecycleScope.launch(Dispatchers.IO) {
            val usuario = db.usuarioDAO().getUserById(usuarioID)

            withContext(Dispatchers.Main) {
                binding.nameSetting.setText(usuario?.nombre)
                binding.emailSettings.setText(usuario?.email)
                binding.passwordSettings.setText(usuario?.password)
                binding.password2Settings.setText(usuario?.password)

            }
        }
    }

}