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
import es.unex.giiis.marvelbook.data.api.toCreador
import es.unex.giiis.marvelbook.database.AppDatabase
import es.unex.giiis.marvelbook.databinding.FragmentCreadorBinding
import es.unex.giiis.marvelbook.ui.coleccion.ColeccionViewModel
import es.unex.giiis.marvelbook.ui.coleccion.tab.adapterTabs.CreadorAdapter
import es.unex.giiis.marvelbook.ui.coleccion.tab.detalles.CreadorDetallesFragmentDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CreadorFragment : Fragment() {



    private lateinit var db: AppDatabase
    private lateinit var adapter: CreadorAdapter

    private var _binding: FragmentCreadorBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        db = AppDatabase.getInstance(requireContext())
        _binding = FragmentCreadorBinding.inflate(inflater, container, false)

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
                if (db.creadorDAO().numeroCreadores() < 1) {
                    withContext(Dispatchers.Main) {
                        binding.spinner.visibility = View.VISIBLE
                    }
                    try {
                        fetchShowsCreators()

                    } catch (error: APIError) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context,"Creador: "+error.message, Toast.LENGTH_SHORT).show()
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
            val creadores = db.creadorDAO().getAll()
            withContext(Dispatchers.Main) {
                adapter = CreadorAdapter(creadores = creadores, onClick = {
                    val action = CreadorDetallesFragmentDirections.actionGlobalCreadorDetallesFragment(
                        it.id.toLong()
                    )
                    navController.navigate(action)
                }
                )
                with(binding) {
                    rvCreadorList.layoutManager = LinearLayoutManager(context)
                    rvCreadorList.adapter = adapter
                }
            }
        }
    }

    private suspend fun fetchShowsCreators() {
        try {

            for (i in 0..100 step 20) {

                for (aux in getNetworkService().getCreadores(i).data?.results ?: listOf()) {
                    db.creadorDAO().insertarCreador(aux.toCreador())
                }
            }
        } catch (cause: Throwable) {
            throw APIError("Unable to fetch data from API", cause)
        }
    }

    private fun performSearch(query: String) {
        lifecycleScope.launch(Dispatchers.IO) {

            val originalList = db.creadorDAO().getAll()

            withContext(Dispatchers.Main) {
                val filteredList = originalList.filter { creador ->
                    creador.name?.contains(query, ignoreCase = true) ?: true
                }
                (binding.rvCreadorList.adapter as? CreadorAdapter)?.updateList(filteredList)
            }
        }
    }
}