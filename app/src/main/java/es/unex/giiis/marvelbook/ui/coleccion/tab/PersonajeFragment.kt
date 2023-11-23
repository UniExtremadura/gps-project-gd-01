package es.unex.giiis.marvelbook.ui.coleccion.tab

import es.unex.giiis.marvelbook.ui.coleccion.tab.adapter.PersonajeAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import es.unex.giiis.marvelbook.api.APIError
import es.unex.giiis.marvelbook.api.getNetworkService
import es.unex.giiis.marvelbook.data.api.toPersonaje
import es.unex.giiis.marvelbook.database.AppDatabase
import es.unex.giiis.marvelbook.databinding.FragmentPersonajeBinding
import es.unex.giiis.marvelbook.ui.coleccion.tab.detalles.PersonajeDetallesFragmentDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class PersonajeFragment : Fragment() {

    private lateinit var db: AppDatabase
    private lateinit var adapter: PersonajeAdapter

    private var _binding: FragmentPersonajeBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        db = AppDatabase.getInstance(requireContext())
        _binding = FragmentPersonajeBinding.inflate(inflater, container, false)
        navController = findNavController()
        return binding.root

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
                            Toast.makeText(context,"Personaje: "+ error.message, Toast.LENGTH_SHORT).show()
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
            val personajes = db.personajeDAO().getAll()
            withContext(Dispatchers.Main) {
                adapter = PersonajeAdapter(personajes = personajes, onClick = {
                    val action = PersonajeDetallesFragmentDirections.actionGlobalPersonajeDetallesFragment(
                        it.id.toLong()
                    )
                    navController.navigate(action)
                }
                )
                with(binding) {
                    rvPersonajeList.layoutManager = LinearLayoutManager(context)
                    rvPersonajeList.adapter = adapter
                }
            }
        }
    }



    private suspend fun fetchShows() {

        try {

            for (i in 0..2000 step 20) {

                for (aux in getNetworkService().getPersonajes(i).data?.results ?: listOf()) {
                    db.personajeDAO().insertarPersonaje(aux.toPersonaje())
                }
            }

        } catch (cause: Throwable) {
            throw APIError("Unable to fetch data from API", cause)
        }
    }
}