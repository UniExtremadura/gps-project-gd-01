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
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import es.unex.giiis.marvelbook.R
import es.unex.giiis.marvelbook.adapter.PersonajeMazoAdapterMazo
import es.unex.giiis.marvelbook.database.PersonajeMazo
import es.unex.giiis.marvelbook.databinding.FragmentMazoBinding

class MazoFragment : Fragment() {

    private var _binding: FragmentMazoBinding? = null
    private lateinit var adapter: PersonajeMazoAdapterMazo
    private lateinit var navController: NavController

    private val viewModel: MazoViewModel by viewModels { MazoViewModel.Factory }

    private var searchMenuItem: MenuItem? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMazoBinding.inflate(inflater, container, false)
        navController = findNavController()
        val root: View = binding.root

        viewModel.getSearchTerm().observe(viewLifecycleOwner) { term ->
            viewModel.performSearch(term)
        }

        setHasOptionsMenu(true)
        viewModel.getAllPersonajeMazo()
        viewModel.personajesMazo.observe(viewLifecycleOwner) { personajesMazo ->
            personajesMazo?.let { setUpRecyclerView(personajesMazo) }
        }
        return root
    }


    private fun setUpRecyclerView(personajesMazo: List<PersonajeMazo>) {

        adapter = PersonajeMazoAdapterMazo(
            personajes = personajesMazo,
            onFavClickListener = { position ->
                val personajeMazo = personajesMazo[position]
                personajeMazo.fav = !personajeMazo.fav!!
                viewModel.updatePersonajeMazo(personajeMazo)
                viewModel.personajesMazo.observe(viewLifecycleOwner) { personajesMazo ->
                    personajesMazo?.let {
                        adapter.updateList(personajesMazo)
                        adapter.notifyItemChanged(position)
                    }
                }

            }, onClick = {
                val action =
                    MazoDetallesFragmentDirections.actionGlobalMazoDetallesFragment(
                        it.id, viewModel.getUsuario().id
                    )
                navController.navigate(action)
            }
        )
        with(binding) {
            listaMazo.layoutManager = LinearLayoutManager(requireContext())
            listaMazo.adapter = adapter
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
                    viewModel.setSearchTerm(newText)
                }
                return false
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
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