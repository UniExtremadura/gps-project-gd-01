package es.unex.giiis.marvelbook.ui.coleccion.tab.detalles

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import es.unex.giiis.marvelbook.R
import es.unex.giiis.marvelbook.adapter.PersonajeComicsAdapter
import es.unex.giiis.marvelbook.database.Comic
import es.unex.giiis.marvelbook.database.Personaje
import es.unex.giiis.marvelbook.databinding.FragmentPersonajeDetallesBinding


class PersonajeDetallesFragment : Fragment() {

    private var _binding: FragmentPersonajeDetallesBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController

    private lateinit var adapter: PersonajeComicsAdapter

    private val viewModel: PersonajeDetallesViewModel by viewModels { PersonajeDetallesViewModel.Factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        navController = findNavController()

        _binding = FragmentPersonajeDetallesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.personajeID = arguments?.getLong("personajeID")!!
        suscribeUI()
    }

    private fun selectVisibility(personaje: Personaje){
        requireActivity().findViewById<Toolbar>(R.id.toolbar)?.apply {

            title = personaje.name
            with(binding) {
                nombrePersonajeColeccion.text = personaje.name
                if (personaje.description == "") {
                    descripcion.visibility = View.GONE
                } else {
                    descripcion.visibility = View.VISIBLE
                    descripcion.text = personaje.description
                }
                Glide.with(foto.context)
                    .load(personaje.imagen.toString())
                    .into(foto)
            }

            viewModel.listComic.observe(viewLifecycleOwner) { listComic ->
                messageApariciones(listComic)
            }
        }
    }
    private fun suscribeUI() {
        viewModel.personajeDetalle.observe(viewLifecycleOwner) { personaje ->
            personaje?.let{ selectVisibility(personaje) }
        }
    }

    private fun messageApariciones(listComic : MutableList<Comic>){
        if (listComic.isEmpty()) {
            with(binding) {
                apariciones.text = getString(R.string.valorNuloApariciones)
            }
        } else {
            with(binding) {
                apariciones.text = ""
            }
        }
        adapter = PersonajeComicsAdapter(
            comics = listComic
        )
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        with(binding) {
            personajeComicsList.layoutManager = LinearLayoutManager(context)
            personajeComicsList.adapter = adapter
        }
    }
}
