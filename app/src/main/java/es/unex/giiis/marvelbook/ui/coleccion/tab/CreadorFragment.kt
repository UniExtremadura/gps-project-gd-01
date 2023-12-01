package es.unex.giiis.marvelbook.ui.coleccion.tab

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
import es.unex.giiis.marvelbook.databinding.FragmentCreadorBinding
import es.unex.giiis.marvelbook.ui.coleccion.ColeccionViewModel
import es.unex.giiis.marvelbook.ui.coleccion.tab.adapterTabs.CreadorAdapter
import es.unex.giiis.marvelbook.ui.coleccion.tab.detalles.CreadorDetallesFragmentDirections

class CreadorFragment : Fragment() {
    private lateinit var adapter: CreadorAdapter

    private var _binding: FragmentCreadorBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController

    private val viewModel: CreadorViewModel by viewModels { CreadorViewModel.Factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreadorBinding.inflate(inflater, container, false)

        navController = findNavController()

        val sharedViewModel = ViewModelProvider(requireActivity())[ColeccionViewModel::class.java]

        sharedViewModel.getSearchTerm().observe(viewLifecycleOwner) { term ->
            viewModel.performSearch(term)
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.spinner.observe(viewLifecycleOwner) { creadores ->
            binding.spinner.visibility = if (creadores) View.VISIBLE else View.GONE
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

    private fun subscribeIU(adapter: CreadorAdapter) {
        viewModel.creador.observe(viewLifecycleOwner) { creadores ->
            if (creadores != null) {
                adapter.updateList(creadores)
            }
        }
    }

    private fun subscribeSearch(adapter: CreadorAdapter) {
        viewModel.creadorActual.observe(viewLifecycleOwner) { creadores ->
            if (creadores != null) {
                adapter.updateList(creadores)
            }
        }
    }

    private fun setUpRecyclerView() {
        adapter = CreadorAdapter(
            creadores = emptyList(),
            onClick = {
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