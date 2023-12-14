package es.unex.giiis.marvelbook.ui.mazo

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import es.unex.giiis.marvelbook.BatallaActivity
import es.unex.giiis.marvelbook.R
import es.unex.giiis.marvelbook.databinding.FragmentMazoDetallesBinding

class MazoDetallesFragment : Fragment() {

    private var _binding: FragmentMazoDetallesBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private val viewModel: MazoDetallesViewModel by viewModels { MazoDetallesViewModel.Factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        navController = findNavController()
        val personajeMazoID = arguments?.getLong("personajeMazoID")!!

        _binding = FragmentMazoDetallesBinding.inflate(inflater, container, false)


        viewModel.obtenerPersonajeID(personajeMazoID) { personajeMazo ->

            requireActivity().findViewById<Toolbar>(R.id.toolbar)?.apply {
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
                startActivity(intent)
            }

            binding.botonVender.setOnClickListener {
                viewModel.venderPersonaje(personajeMazo.id)
                viewModel.toast.observe(viewLifecycleOwner) { text ->
                    text?.let {
                        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
                        viewModel.onToastShown()
                    }
                }
               navController.navigateUp()
            }
        }

        return binding.root
    }

}