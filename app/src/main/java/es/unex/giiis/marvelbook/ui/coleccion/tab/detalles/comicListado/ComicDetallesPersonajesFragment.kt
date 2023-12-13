package es.unex.giiis.marvelbook.ui.coleccion.tab.detalles.comicListado

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import es.unex.giiis.marvelbook.R
import es.unex.giiis.marvelbook.adapter.ComicPersonajesAdapter
import es.unex.giiis.marvelbook.database.AppDatabase
import es.unex.giiis.marvelbook.database.Personaje
import es.unex.giiis.marvelbook.databinding.FragmentComicDetallesPersonajesBinding
import es.unex.giiis.marvelbook.ui.coleccion.tab.detalles.ComicDetallesViewModel
class ComicDetallesPersonajesFragment : Fragment() {
    private lateinit var db: AppDatabase

    private var _binding: FragmentComicDetallesPersonajesBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController
    private lateinit var adapter: ComicPersonajesAdapter

    private val viewModel: ComicDetallesViewModel by viewModels { ComicDetallesViewModel.Factory }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        db = AppDatabase.getInstance(requireContext())
        navController = findNavController()

        _binding = FragmentComicDetallesPersonajesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().findViewById<Toolbar>(R.id.toolbar)?.apply {
            title = "Listado de personajes"
        }
        viewModel.comicID = arguments?.getLong("comicID")!!
        suscribeUI()
    }

    private fun suscribeUI() {
        viewModel.listCharacter.observe(viewLifecycleOwner) { listPersonaje ->
            selectVisibility(listPersonaje)
        }
    }

    private fun selectVisibility(listPersonaje : MutableList<Personaje>){
        if(listPersonaje.isEmpty()) {
            Log.d("PersonajeDetalles", listPersonaje.size.toString())
            with(binding) {
                comicPersonajesList.visibility = View.GONE
                aparicionesPersonaje.text = getString(R.string.valorNuloApariciones)
                aparicionesPersonaje.visibility = View.VISIBLE

            }}
        else{

            Log.d("PersonajeDetalles", listPersonaje.size.toString())
            with(binding) {
                comicPersonajesList.visibility = View.VISIBLE
                aparicionesPersonaje.visibility = View.GONE
            }

        }
        adapter = ComicPersonajesAdapter(
            personajes = listPersonaje,
        )
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        with(binding) {
            comicPersonajesList.layoutManager = LinearLayoutManager(context)
            comicPersonajesList.adapter = adapter
        }
    }
}