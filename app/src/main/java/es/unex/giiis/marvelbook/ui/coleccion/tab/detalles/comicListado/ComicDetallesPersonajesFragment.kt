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
import es.unex.giiis.marvelbook.adapter.ComicPersonajesAdapter
import es.unex.giiis.marvelbook.database.AppDatabase
import es.unex.giiis.marvelbook.database.Comic
import es.unex.giiis.marvelbook.database.Personaje
import es.unex.giiis.marvelbook.databinding.FragmentComicDetallesPersonajesBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ComicDetallesPersonajesFragment : Fragment() {
    private lateinit var db: AppDatabase

    private var _binding: FragmentComicDetallesPersonajesBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController
    private var comicID: Long = 0L
    private lateinit var comic: Comic
    private lateinit var adapter: ComicPersonajesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        lifecycleScope.launch(Dispatchers.IO) {
            comicID = arguments?.getLong("comicID")!!
            comic = db.comicDAO().getById(comicID)
            val listPersonajesID = comic.characters
            val personajesList = mutableListOf<Personaje>()
            var numPersonajes = 0
            if (listPersonajesID != null) {
                for(aux in listPersonajesID){
                    val personaje = db.personajeDAO().getByID(aux.toLong())
                    if(personaje!= null){
                        personajesList.add(personaje)
                        numPersonajes++
                    }
                }
            }

            withContext(Dispatchers.Main) {
                if(numPersonajes == 0) {
                    with(binding) {
                        comicPersonajesList.visibility = View.GONE
                        aparicionesPersonaje.text = getString(R.string.valorNuloApariciones)
                        aparicionesPersonaje.visibility = View.VISIBLE

                    }}
                else{
                    with(binding) {
                        comicPersonajesList.visibility = View.VISIBLE
                        aparicionesPersonaje.visibility = View.GONE
                    }

                }
                adapter = ComicPersonajesAdapter(personajes = personajesList,
                )
                with(binding) {
                    comicPersonajesList.layoutManager = LinearLayoutManager(context)
                    comicPersonajesList.adapter = adapter
                }
            }
        }



    }
}