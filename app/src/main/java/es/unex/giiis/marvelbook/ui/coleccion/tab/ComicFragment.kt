package es.unex.giiis.marvelbook.ui.coleccion.tab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import es.unex.giiis.marvelbook.api.APIError
import es.unex.giiis.marvelbook.api.getNetworkService
import es.unex.giiis.marvelbook.data.api.toComic
import es.unex.giiis.marvelbook.database.AppDatabase
import es.unex.giiis.marvelbook.databinding.FragmentComicBinding
import es.unex.giiis.marvelbook.ui.coleccion.ColeccionViewModel
import es.unex.giiis.marvelbook.ui.coleccion.tab.adapterTabs.ComicAdapter
import es.unex.giiis.marvelbook.ui.coleccion.tab.detalles.ComicDetallesFragmentDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ComicFragment : Fragment() {

    private lateinit var db: AppDatabase
    private lateinit var adapter: ComicAdapter

    private var _binding: FragmentComicBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController : NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        db = AppDatabase.getInstance(requireContext())

        _binding = FragmentComicBinding.inflate(inflater, container, false)

        navController = findNavController()

        val sharedViewModel = ViewModelProvider(requireActivity())[ColeccionViewModel::class.java]

        sharedViewModel.getSearchTerm().observe(viewLifecycleOwner) { term ->
            performSearch(term)
        }

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                            Toast.makeText(context,"Comic"+error.message, Toast.LENGTH_SHORT).show()
                        }
                    } finally {
                        withContext(Dispatchers.Main) {
                            binding.spinner.visibility = View.GONE
                        }
                    }
                }
            }
            setUpRecyclerView()
        }
    }

    private fun setUpRecyclerView() {
        lifecycleScope.launch(Dispatchers.IO) {
            val comics = db.comicDAO().getAll()
            withContext(Dispatchers.Main) {
                adapter = ComicAdapter(comics = comics, onClick = {
                    val action = ComicDetallesFragmentDirections.actionGlobalComicDetallesFragment(
                        it.id.toLong()
                    )
                    navController.navigate(action)
                }
                )
                with(binding) {
                    rvComicList.layoutManager = LinearLayoutManager(context)
                    rvComicList.adapter = adapter
                }
            }
        }
    }

    private suspend fun fetchShowsComics() {
        try {

            for (i in 0..1000 step 20) {

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

    private fun performSearch(query: String) {
        lifecycleScope.launch(Dispatchers.IO) {

            val originalList = db.comicDAO().getAll()

            withContext(Dispatchers.Main) {
                val filteredList = originalList.filter { comic ->
                    comic.title?.contains(query, ignoreCase = true) ?: true
                }
                (binding.rvComicList.adapter as? ComicAdapter)?.updateList(filteredList)
            }
        }
    }
}