package es.unex.giiis.marvelbook.ui.coleccion.tab.detalles

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
import com.bumptech.glide.Glide
import es.unex.giiis.marvelbook.R
import es.unex.giiis.marvelbook.adapter.CreadorComicsAdapter
import es.unex.giiis.marvelbook.database.AppDatabase
import es.unex.giiis.marvelbook.database.Comic
import es.unex.giiis.marvelbook.database.Creador
import es.unex.giiis.marvelbook.databinding.FragmentCreadorDetallesBinding

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CreadorDetallesFragment : Fragment() {

    private lateinit var db: AppDatabase

    private var _binding: FragmentCreadorDetallesBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController

    private lateinit var creador: Creador
    private lateinit var adapter: CreadorComicsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        db = AppDatabase.getInstance(requireContext())
        navController = findNavController()
        val creadorID = arguments?.getLong("creadorID")!!

        lifecycleScope.launch(Dispatchers.IO) {
            creador = db.creadorDAO().getByID(creadorID)
        }

        _binding = FragmentCreadorDetallesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().findViewById<Toolbar>(R.id.toolbar)?.apply {
            title = creador.name
        }


        with(binding) {
            nombreCreadorDetalles.text = creador.name
            Glide.with(foto.context)
                .load(creador.imagen.toString())
                .into(foto)
        }
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        lifecycleScope.launch(Dispatchers.IO) {

            val listComicsID = creador.comics
            val comicsList = mutableListOf<Comic>()
            var numComic = 0
            if (listComicsID != null) {

                for(aux in listComicsID){
                    var comic = db.comicDAO().getById(aux.toLong())
                    if(comic!= null){
                        comicsList.add(comic)
                        numComic++
                    }
                }
            }

            withContext(Dispatchers.Main) {
                if(numComic == 0){
                    with(binding){
                        apariciones.text = getString(R.string.valorNuloApariciones)
                    }
                }
                adapter = CreadorComicsAdapter(comics = comicsList,
                )
                with(binding) {
                    creadorComicsList.layoutManager = LinearLayoutManager(context)
                    creadorComicsList.adapter = adapter
                }
            }
        }



    }

}
