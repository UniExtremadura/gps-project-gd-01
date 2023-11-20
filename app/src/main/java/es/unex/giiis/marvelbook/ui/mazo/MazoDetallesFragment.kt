package es.unex.giiis.marvelbook.ui.mazo

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import es.unex.giiis.marvelbook.BatallaActivity
import es.unex.giiis.marvelbook.R
import es.unex.giiis.marvelbook.database.AppDatabase
import es.unex.giiis.marvelbook.database.PersonajeMazo
import es.unex.giiis.marvelbook.databinding.FragmentMazoDetallesBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MazoDetallesFragment : Fragment() {
    private lateinit var db: AppDatabase

    private var _binding: FragmentMazoDetallesBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController

    private lateinit var personajeMazo: PersonajeMazo

    private var usuarioSesionID: Long = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        db = AppDatabase.getInstance(requireContext())
        navController = findNavController()
        val personajeMazoID = arguments?.getLong("personajeMazoID")!!
        usuarioSesionID = arguments?.getLong("usuarioID")!!

        _binding = FragmentMazoDetallesBinding.inflate(inflater, container, false)

        lifecycleScope.launch(Dispatchers.IO) {
            personajeMazo = db.personajeMazoDAO().getById(personajeMazoID)
            withContext(Dispatchers.Main) {
                val appBarConfiguration = AppBarConfiguration(navController.graph)
                requireActivity().findViewById<Toolbar>(R.id.toolbar)?.apply {
                    setupWithNavController(navController, appBarConfiguration)
                    title = personajeMazo.name
                }


                with(binding) {
                    nombrePersonaje.text = personajeMazo.name
                    ratingValue.text = personajeMazo.rating.toString()
                    defendPersonajeMazo.text = personajeMazo.defense.toString()
                    powerPersonajeMazo.text = personajeMazo.power.toString()
                    speedPersonajeMazo.text = personajeMazo.speed.toString()

                    Glide.with(fotoPersonajeMazo.context)
                        .load(personajeMazo.imagen.toString())
                        .into(fotoPersonajeMazo)
                }

                binding.botonBatalla.setOnClickListener {
                    val intent = Intent(requireContext(), BatallaActivity::class.java)
                    intent.putExtra("personajeMazoID", personajeMazo.id)
                    intent.putExtra("usuarioSesionID", usuarioSesionID)
                    startActivity(intent)
                }
            }
        }



        return binding.root
    }

}