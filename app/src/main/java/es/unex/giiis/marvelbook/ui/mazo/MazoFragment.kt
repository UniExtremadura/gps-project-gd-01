package es.unex.giiis.marvelbook.ui.mazo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import es.unex.giiis.marvelbook.R
import es.unex.giiis.marvelbook.adapter.PersonajeMazoAdapterMazo
import es.unex.giiis.marvelbook.database.AppDatabase
import es.unex.giiis.marvelbook.database.Usuario
import es.unex.giiis.marvelbook.databinding.FragmentMazoBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MazoFragment : Fragment() {

    private var _binding: FragmentMazoBinding? = null
    private lateinit var user: Usuario
    private lateinit var adapter: PersonajeMazoAdapterMazo
    private lateinit var db: AppDatabase
    private lateinit var navController: NavController

    private val mazoViewModel: MazoViewModel by viewModels { MazoViewModel.Factory }

    private var searchMenuItem: MenuItem? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        db = AppDatabase.getInstance(requireContext())

        user = mazoViewModel.getUsuario()

        _binding = FragmentMazoBinding.inflate(inflater, container, false)
        navController = findNavController()
        val root: View = binding.root

        mazoViewModel.getSearchTerm().observe(viewLifecycleOwner) { term ->
            onSearch(term)
        }

        setHasOptionsMenu(true)
        setUpRecyclerView()
        return root
    }

    private fun setUpRecyclerView() {
        lifecycleScope.launch(Dispatchers.IO) {
            var personajesMazo = db.personajeMazoDAO().getAll(user.id).toMutableList()

            withContext(Dispatchers.Main) {
                adapter = PersonajeMazoAdapterMazo(
                    personajes = personajesMazo,
                    onFavClickListener = { position ->
                        val personajeMazo = personajesMazo[position]
                        personajeMazo.fav = !personajeMazo.fav!!
                        lifecycleScope.launch(Dispatchers.IO) {
                            db.personajeMazoDAO().updatePersonajeMazo(personajeMazo)
                            personajesMazo =
                                db.personajeMazoDAO().getAll(user.id).toMutableList()

                            withContext(Dispatchers.Main) {
                                adapter.updateList(personajesMazo)
                                adapter.notifyItemChanged(position)
                            }
                        }
                    }, onClick = {
                        val action =
                            MazoDetallesFragmentDirections.actionGlobalMazoDetallesFragment(
                                it.id, user.id
                            )
                        navController.navigate(action)
                    }
                )
                with(binding) {
                    listaMazo.layoutManager = LinearLayoutManager(requireContext())
                    listaMazo.adapter = adapter
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_search, menu)
        searchMenuItem = menu.findItem(R.id.action_search)
        val searchView = searchMenuItem?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    mazoViewModel.setSearchTerm(newText)
                }
                return false
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun onSearch(query: String) {
        lifecycleScope.launch(Dispatchers.IO) {

            val originalList = db.personajeMazoDAO().getAll(user.id)

            withContext(Dispatchers.Main) {
                val filteredList = originalList.filter { personajeMazo ->
                    personajeMazo.name?.contains(query, ignoreCase = true) ?: true
                }
                (binding.listaMazo.adapter as? PersonajeMazoAdapterMazo)?.updateList(filteredList)
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onDestroyOptionsMenu() {
        searchMenuItem?.let {
            val searchView = it.actionView as SearchView
            searchView.setQuery("", false)
            searchView.isIconified = true
        }
        super.onDestroyOptionsMenu()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}