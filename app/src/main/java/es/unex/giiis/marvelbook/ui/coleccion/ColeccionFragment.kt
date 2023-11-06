package es.unex.giiis.marvelbook.ui.coleccion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import es.unex.giiis.marvelbook.R
import es.unex.giiis.marvelbook.api.APIError
import es.unex.giiis.marvelbook.api.getNetworkService
import es.unex.giiis.marvelbook.data.api.toComic
import es.unex.giiis.marvelbook.data.api.toCreador
import es.unex.giiis.marvelbook.data.api.toPersonaje
import es.unex.giiis.marvelbook.database.AppDatabase
import es.unex.giiis.marvelbook.databinding.FragmentColeccionBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Suppress("DEPRECATION")
class ColeccionFragment : Fragment() {

    private var _binding: FragmentColeccionBinding? = null
    private lateinit var db: AppDatabase
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        db = AppDatabase.getInstance(requireContext())
        val coleccionViewModel =
            ViewModelProvider(this).get(ColeccionViewModel::class.java)

        _binding = FragmentColeccionBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val textView: TextView = binding.textHome
        coleccionViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_search, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {

            withContext(Dispatchers.IO) {
                if (db.personajeDAO().numeroPersonajes() < 1) {
                    withContext(Dispatchers.Main) {
                        binding.spinner.visibility = View.VISIBLE
                    }
                    try {
                        fetchShows()

                    } catch (error: APIError) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
                        }
                    } finally {
                        withContext(Dispatchers.Main) {
                            binding.spinner.visibility = View.GONE
                        }

                    }
                } else {

                    //TODO procesar los datos de los personajes desde la base de datos
                    //db.personajeDAO().getAll()
                }
            }
        }
        lifecycleScope.launch {

            withContext(Dispatchers.IO) {
                if (db.comicDAO().numeroComics() < 1) {
                    withContext(Dispatchers.Main) {
                        binding.spinner.visibility = View.VISIBLE
                    }
                    try {
                        fetchShowsComics()

                    } catch (error: APIError) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
                        }
                    } finally {
                        withContext(Dispatchers.Main) {
                            binding.spinner.visibility = View.GONE
                        }

                    }
                } else {

                    //TODO procesar los datos de los comics desde la base de datos
                    //db.comicDAO().getAll()
                }
            }
        }


        lifecycleScope.launch {

            withContext(Dispatchers.IO) {
                if (db.creadorDAO().numeroCreadores() < 1) {
                    withContext(Dispatchers.Main) {
                        binding.spinner.visibility = View.VISIBLE
                    }
                    try {

                        fetchShowsCreators()

                    } catch (error: APIError) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
                        }
                    } finally {
                        withContext(Dispatchers.Main) {
                            binding.spinner.visibility = View.GONE
                        }

                    }
                } else {

                    //TODO procesar los datos de los creadores desde la base de datos
                    //db.creadorDAO().getAll()

                }
            }
        }
    }

    private suspend fun fetchShowsComics() {

        try {

            for (i in 0..500 step 20) {

                for (aux in getNetworkService().getComics(i).data?.results ?: listOf()) {
                    if(db.comicDAO().obtenerComic(aux.toComic().id).isEmpty()){
                        db.comicDAO().insertarComic(aux.toComic())
                    }

                }
            }

        } catch (cause: Throwable) {

            throw APIError("Unable to fetch data from API", cause)
        }
    }


    private suspend fun fetchShows() {

        try {

            for (i in 0..200 step 20) {

                for (aux in getNetworkService().getPersonajes(i).data?.results ?: listOf()) {
                    db.personajeDAO().insertarPersonaje(aux.toPersonaje())
                }
            }

        } catch (cause: Throwable) {
            throw APIError("Unable to fetch data from API", cause)
        }
    }


    private suspend fun fetchShowsCreators() {

        try {

            for (i in 0..200 step 20) {

                for (aux in getNetworkService().getCreadores(i).data?.results ?: listOf()) {
                    db.creadorDAO().insertarCreador(aux.toCreador())
                }
            }

        } catch (cause: Throwable) {
            throw APIError("Unable to fetch data from API", cause)
        }
    }
}