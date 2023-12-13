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
import es.unex.giiis.marvelbook.adapter.CreadorComicsAdapter
import es.unex.giiis.marvelbook.database.AppDatabase
import es.unex.giiis.marvelbook.database.Comic
import es.unex.giiis.marvelbook.database.Creador
import es.unex.giiis.marvelbook.databinding.FragmentCreadorDetallesBinding



class CreadorDetallesFragment : Fragment() {

    private lateinit var db: AppDatabase

    private var _binding: FragmentCreadorDetallesBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController

    private lateinit var adapter: CreadorComicsAdapter

    private val viewModel: CreadorDetallesViewModel by viewModels { CreadorDetallesViewModel.Factory }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        db = AppDatabase.getInstance(requireContext())
        navController = findNavController()

        _binding = FragmentCreadorDetallesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.creadorID = arguments?.getLong("creadorID")!!
        suscribeUI()

    }

    private fun selectVisibility(creador: Creador){
        requireActivity().findViewById<Toolbar>(R.id.toolbar)?.apply {

            title = creador.name
            with(binding) {

                Glide.with(foto.context)
                    .load(creador.imagen.toString())
                    .into(foto)
            }

            viewModel.listComic.observe(viewLifecycleOwner) { listComic ->
                messageApariciones(listComic)
            }
        }
    }
    private fun suscribeUI() {
        viewModel.creadorDetalle.observe(viewLifecycleOwner) { creador ->
            creador?.let{ selectVisibility(creador) }
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
        adapter = CreadorComicsAdapter(
            comics = listComic
        )
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        with(binding) {
            creadorComicsList.layoutManager = LinearLayoutManager(context)
            creadorComicsList.adapter = adapter
        }
    }

}
