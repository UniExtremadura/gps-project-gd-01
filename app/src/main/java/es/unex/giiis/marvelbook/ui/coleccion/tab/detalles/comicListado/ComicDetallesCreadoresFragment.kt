package es.unex.giiis.marvelbook.ui.coleccion.tab.detalles.comicListado

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import es.unex.giiis.marvelbook.R
import es.unex.giiis.marvelbook.adapter.ComicCreadoresAdapter
import es.unex.giiis.marvelbook.database.AppDatabase
import es.unex.giiis.marvelbook.database.Comic
import es.unex.giiis.marvelbook.database.Creador
import es.unex.giiis.marvelbook.databinding.FragmentComicDetallesCreadoresBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ComicDetallesCreadoresFragment : Fragment() {
    private lateinit var db: AppDatabase

    private var _binding: FragmentComicDetallesCreadoresBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController

    private lateinit var comic: Comic
    private lateinit var adapter: ComicCreadoresAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        db = AppDatabase.getInstance(requireContext())
        navController = findNavController()


        _binding = FragmentComicDetallesCreadoresBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().findViewById<Toolbar>(R.id.toolbar)?.apply {
            title = "Listado de Creadores"
        }
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        lifecycleScope.launch(Dispatchers.IO) {
            val comicID = arguments?.getLong("comicID")!!
            comic = db.comicDAO().getById(comicID)
            val listCreadoresID = comic.creators
            val creadoresList = mutableListOf<Creador>()
            var numCreadores = 0
            if (listCreadoresID != null) {
                for(aux in listCreadoresID){
                    val creador = db.creadorDAO().getByID(aux.toLong())

                    if(creador!= null){
                        creadoresList.add(creador)
                    numCreadores++
                    }
                }
            }

            withContext(Dispatchers.Main) {
                if(numCreadores == 0) {
                    with(binding) {
                        comicCreadoresList.visibility = View.GONE
                        aparicionesCreadores.text = getString(R.string.valorNuloApariciones)
                        aparicionesCreadores.visibility = View.VISIBLE
                    }}
                else{
                    with(binding) {
                        comicCreadoresList.visibility = View.VISIBLE
                        aparicionesCreadores.visibility = View.GONE
                    }

                }
                adapter = ComicCreadoresAdapter(creadores = creadoresList,
                )
                with(binding) {
                    comicCreadoresList.layoutManager = LinearLayoutManager(context)
                    comicCreadoresList.adapter = adapter
                }
            }
        }



    }
}