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
import es.unex.giiis.marvelbook.adapter.PersonajeComicsAdapter
import es.unex.giiis.marvelbook.database.AppDatabase
import es.unex.giiis.marvelbook.database.Comic
import es.unex.giiis.marvelbook.database.Personaje
import es.unex.giiis.marvelbook.databinding.FragmentPersonajeDetallesBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class PersonajeDetallesFragment : Fragment() {

    private lateinit var db: AppDatabase

    private var _binding: FragmentPersonajeDetallesBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController

    private lateinit var personaje: Personaje
    private lateinit var adapter: PersonajeComicsAdapter
    private var personajeID: Long = 0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        db = AppDatabase.getInstance(requireContext())
        navController = findNavController()


        _binding = FragmentPersonajeDetallesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        personajeID = arguments?.getLong("personajeID")!!
        lifecycleScope.launch(Dispatchers.IO) {
            personaje = db.personajeDAO().getByID(personajeID)

            withContext(Dispatchers.Main) {
                requireActivity().findViewById<Toolbar>(R.id.toolbar)?.apply {
                    title = personaje.name
                }

                with(binding) {
                    nombrePersonajeColeccion.text = personaje.name
                    if(personaje.description == ""){
                        descripcion.visibility = View.GONE
                    }
                    else{
                        descripcion.visibility = View.VISIBLE
                        descripcion.text = personaje.description
                    }
                    Glide.with(foto.context)
                        .load(personaje.imagen.toString())
                        .into(foto)
                }
                setUpRecyclerView()
            }
        }

    }

    private fun setUpRecyclerView() {
        lifecycleScope.launch(Dispatchers.IO) {

            val listComicsID = personaje.comics
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
                if (numComic == 0){
                    with(binding){
                        apariciones.text =  getString(R.string.valorNuloApariciones)
                    }
                }
                adapter = PersonajeComicsAdapter(comics = comicsList,
                )
                with(binding) {
                    personajeComicsList.layoutManager = LinearLayoutManager(context)
                    personajeComicsList.adapter = adapter
                }
            }
        }



    }

}
