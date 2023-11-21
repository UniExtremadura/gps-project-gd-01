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
import androidx.lifecycle.lifecycleScope
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
    private var usuarioSesionID: Long = 0
    private lateinit var user: Usuario
    private lateinit var adapter: PersonajeMazoAdapterMazo
    private lateinit var db: AppDatabase
    private var searchMenuItem: MenuItem? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        db = AppDatabase.getInstance(requireContext())
        usuarioSesionID = activity?.intent?.getLongExtra("usuarioID", 0L)!!

        lifecycleScope.launch(Dispatchers.IO) {
            user = db.usuarioDAO().getUserById(usuarioSesionID)!!
        }

        _binding = FragmentMazoBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setHasOptionsMenu(true)
        setUpRecyclerView()
        return root
    }

    private fun setUpRecyclerView() {
        lifecycleScope.launch(Dispatchers.IO) {
            var personajesMazo = db.personajeMazoDAO().getAll(usuarioSesionID).toMutableList()

            withContext(Dispatchers.Main) {
                adapter = PersonajeMazoAdapterMazo(
                    personajes = personajesMazo,
                    onFavClickListener = { position ->
                        val personaje = personajesMazo[position]
                        personaje.fav = !personaje.fav!!
                        lifecycleScope.launch(Dispatchers.IO) {
                            db.personajeMazoDAO().updatePersonajeMazo(personaje)
                            personajesMazo = db.personajeMazoDAO().getAll(usuarioSesionID).toMutableList()

                            withContext(Dispatchers.Main) {
                                adapter.updateList(personajesMazo)
                                adapter.notifyItemChanged(position)
                            }
                        }
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
                onSearch(newText.orEmpty())
                return false
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    fun onSearch(query: String) {
        lifecycleScope.launch(Dispatchers.IO) {

            val originalList = db.personajeMazoDAO().getAll(usuarioSesionID)

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