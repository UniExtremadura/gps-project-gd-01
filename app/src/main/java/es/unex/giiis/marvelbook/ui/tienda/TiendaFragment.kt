package es.unex.giiis.marvelbook.ui.tienda

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import es.unex.giiis.marvelbook.database.AppDatabase
import es.unex.giiis.marvelbook.database.Usuario
import es.unex.giiis.marvelbook.databinding.FragmentTiendaBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TiendaFragment : Fragment() {

    private var _binding: FragmentTiendaBinding? = null
    private lateinit var user: Usuario
    private val binding get() = _binding!!
    private lateinit var db: AppDatabase
    private lateinit var navController : androidx.navigation.NavController
    private val tiendaViewModel: TiendaViewModel by viewModels { TiendaViewModel.Factory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        navController = findNavController()
        user = tiendaViewModel.getUsuario()
        db = AppDatabase.getInstance(requireContext())

        _binding = FragmentTiendaBinding.inflate(inflater, container, false)
        binding.coinAmmountUsuario.text = user.monedas.toString()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.SobreBasico.setOnClickListener {
            abrirSobreBasico()
        }

        binding.SobreEspecial.setOnClickListener {
            abrirSobreEspecial()
        }
    }

    private fun abrirSobreBasico() {

        if (user.monedas!! >= 10) {

            user.monedas = user.monedas?.minus(10)
            lifecycleScope.launch(Dispatchers.IO) {
                db.usuarioDAO().updateUsuario(user)
            }
            val intent = Intent(requireContext(), SobreActivity::class.java)
            intent.putExtra("sobreTipo", 1)
            startActivity(intent)
        } else {
            Toast.makeText(context, "No tienes monedas suficientes", Toast.LENGTH_SHORT).show()
        }
    }

    private fun abrirSobreEspecial() {

        if (user.monedas!! >= 20) {

            user.monedas = user.monedas?.minus(20)
            lifecycleScope.launch(Dispatchers.IO) {
                db.usuarioDAO().updateUsuario(user)
            }
            val intent = Intent(requireContext(), SobreActivity::class.java)
            intent.putExtra("sobreTipo", 2)
            startActivity(intent)
        } else {
            Toast.makeText(context, "No tienes monedas suficientes", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()

        user = tiendaViewModel.getUsuario()

        binding.coinAmmountUsuario.text = user.monedas.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

