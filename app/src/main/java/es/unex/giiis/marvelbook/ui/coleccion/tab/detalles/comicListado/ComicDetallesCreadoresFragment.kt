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
import es.unex.giiis.marvelbook.adapter.ComicCreadoresAdapter
import es.unex.giiis.marvelbook.database.Creador
import es.unex.giiis.marvelbook.databinding.FragmentComicDetallesCreadoresBinding
import es.unex.giiis.marvelbook.ui.coleccion.tab.detalles.ComicDetallesViewModel

class ComicDetallesCreadoresFragment : Fragment() {

    private var _binding: FragmentComicDetallesCreadoresBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController
    private lateinit var adapter: ComicCreadoresAdapter

    private val viewModel: ComicDetallesViewModel by viewModels { ComicDetallesViewModel.Factory }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        navController = findNavController()

        _binding = FragmentComicDetallesCreadoresBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().findViewById<Toolbar>(R.id.toolbar)?.apply {
            title = "Listado de creadores"
        }
        viewModel.comicID = arguments?.getLong("comicID")!!
        suscribeUI()
    }

    private fun suscribeUI() {
        viewModel.listCreator.observe(viewLifecycleOwner) { listCreador ->
            selectVisibility(listCreador)
        }
    }

    private fun selectVisibility(listCreador : MutableList<Creador>){
        if(listCreador.isEmpty()) {
            Log.d("PersonajeDetalles", listCreador.size.toString())
            with(binding) {
                comicCreadoresList.visibility = View.GONE
                aparicionesCreadores.text = getString(R.string.valorNuloApariciones)
                aparicionesCreadores.visibility = View.VISIBLE

            }}
        else{

            Log.d("PersonajeDetalles", listCreador.size.toString())
            with(binding) {
                comicCreadoresList.visibility = View.VISIBLE
                aparicionesCreadores.visibility = View.GONE
            }

        }
        adapter = ComicCreadoresAdapter(
            creadores = listCreador,
        )
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        with(binding) {
            comicCreadoresList.layoutManager = LinearLayoutManager(context)
            comicCreadoresList.adapter = adapter
        }
    }
}