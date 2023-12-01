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
import es.unex.giiis.marvelbook.databinding.FragmentComicBinding
import es.unex.giiis.marvelbook.ui.coleccion.ColeccionViewModel
import es.unex.giiis.marvelbook.ui.coleccion.tab.adapterTabs.ComicAdapter
import es.unex.giiis.marvelbook.ui.coleccion.tab.detalles.ComicDetallesFragmentDirections

class ComicFragment : Fragment() {

    private lateinit var adapter: ComicAdapter

    private var _binding: FragmentComicBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController : NavController

    private val viewModel: ComicViewModel by viewModels { ComicViewModel.Factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentComicBinding.inflate(inflater, container, false)

        navController = findNavController()

        val sharedViewModel = ViewModelProvider(requireActivity())[ColeccionViewModel::class.java]

        sharedViewModel.getSearchTerm().observe(viewLifecycleOwner) { term ->
            viewModel.performSearch(term)
        }

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.spinner.observe(viewLifecycleOwner) { comics ->
            binding.spinner.visibility = if (comics) View.VISIBLE else View.GONE
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

    private fun subscribeIU(adapter: ComicAdapter) {
        viewModel.comics.observe(viewLifecycleOwner) { comics ->
            if (comics != null) {
                adapter.updateList(comics)
            }
        }
    }

    private fun subscribeSearch(adapter: ComicAdapter) {
        viewModel.comicActual.observe(viewLifecycleOwner) { comics ->
            if (comics != null) {
                adapter.updateList(comics)
            }
        }
    }

    private fun setUpRecyclerView() {
        adapter = ComicAdapter(
            comics = emptyList(),
            onClick = {
                val action = ComicDetallesFragmentDirections.actionGlobalComicDetallesFragment(
                    it.id.toLong()
                )
                navController.navigate(action)
            })
        with(binding) {
            rvComicList.layoutManager = LinearLayoutManager(context)
            rvComicList.adapter = adapter
        }
    }
}