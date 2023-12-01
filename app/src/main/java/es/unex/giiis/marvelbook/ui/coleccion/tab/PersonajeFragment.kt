package es.unex.giiis.marvelbook.ui.coleccion.tab

import es.unex.giiis.marvelbook.ui.coleccion.tab.adapterTabs.PersonajeAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import es.unex.giiis.marvelbook.databinding.FragmentPersonajeBinding
import es.unex.giiis.marvelbook.ui.coleccion.tab.detalles.PersonajeDetallesFragmentDirections
import es.unex.giiis.marvelbook.ui.coleccion.ColeccionViewModel


class PersonajeFragment : Fragment() {

    private lateinit var adapter: PersonajeAdapter

    private var _binding: FragmentPersonajeBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController

    private val viewModel: PersonajeViewModel by viewModels { PersonajeViewModel.Factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPersonajeBinding.inflate(inflater, container, false)

        navController = findNavController()

        val sharedViewModel = ViewModelProvider(requireActivity())[ColeccionViewModel::class.java]

        sharedViewModel.getSearchTerm().observe(viewLifecycleOwner) { term ->
            viewModel.performSearch(term)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.spinner.observe(viewLifecycleOwner) { personajes ->
            binding.spinner.visibility = if (personajes) View.VISIBLE else View.GONE
        }

        viewModel.toast.observe(viewLifecycleOwner) { text ->
            text?.let {
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
                viewModel.onToastShown()
            }
        }
        setUpRecyclerView()
        subscribeIU(adapter)
        subscribeSearch(adapter)
    }

    private fun subscribeIU(adapter: PersonajeAdapter) {
        viewModel.personajes.observe(viewLifecycleOwner) { personajes ->
            if (personajes != null) {
                adapter.updateList(personajes)
            }
        }
    }

    private fun subscribeSearch(adapter: PersonajeAdapter) {
        viewModel.personajesActual.observe(viewLifecycleOwner) { personajes ->
            if (personajes != null) {
                adapter.updateList(personajes)
            }
        }
    }

    private fun setUpRecyclerView() {
        adapter = PersonajeAdapter(
            personajes = emptyList(),
            onClick = {
                val action =
                    PersonajeDetallesFragmentDirections.actionGlobalPersonajeDetallesFragment(
                        it.id.toLong()
                    )
                navController.navigate(action)
            })
        with(binding) {
            rvPersonajeList.layoutManager = LinearLayoutManager(context)
            rvPersonajeList.adapter = adapter
        }
    }
}