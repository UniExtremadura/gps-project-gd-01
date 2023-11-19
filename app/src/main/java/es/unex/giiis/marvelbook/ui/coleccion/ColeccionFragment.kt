package es.unex.giiis.marvelbook.ui.coleccion

import es.unex.giiis.marvelbook.ui.coleccion.tab.ViewPagerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import es.unex.giiis.marvelbook.R
import es.unex.giiis.marvelbook.database.AppDatabase
import es.unex.giiis.marvelbook.databinding.FragmentColeccionBinding
import es.unex.giiis.marvelbook.ui.coleccion.tab.ComicFragment
import es.unex.giiis.marvelbook.ui.coleccion.tab.CreadorFragment
import es.unex.giiis.marvelbook.ui.coleccion.tab.PersonajeFragment

@Suppress("DEPRECATION")
class ColeccionFragment : Fragment() {

    private var _binding: FragmentColeccionBinding? = null
    private lateinit var db: AppDatabase
    private var searchMenuItem: MenuItem? = null

    private lateinit var adapter: ViewPagerAdapter
    private lateinit var tabLayout: TabLayout

    private var personajeFragment : PersonajeFragment? = null
    private var comicFragment : ComicFragment? = null
    private var creadorFragment : CreadorFragment? = null


    private val binding get() = _binding!!

    private val tabArray = arrayOf(
        "PERSONAJES",
        "COMICS",
        "CREADOR"

    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)

        db = AppDatabase.getInstance(requireContext())
        _binding = FragmentColeccionBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val viewPager = binding.viewPager
        tabLayout = binding.tabLayout

        adapter = ViewPagerAdapter(childFragmentManager, lifecycle)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabArray[position]
        }.attach()

        return root
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                searchMenuItem?.let {
                    val searchView = it.actionView as SearchView
                    searchView.setQuery("", false)
                    searchView.isIconified = true
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                searchMenuItem?.let {
                    val searchView = it.actionView as SearchView
                    searchView.setQuery("", false)
                    searchView.isIconified = true
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onSearch(query: String) {

        when (binding.viewPager.currentItem) {
            0 -> {
                if(personajeFragment == null){
                    personajeFragment = adapter.getFragmentPersonaje()
                }
                personajeFragment!!.performSearch(query)
            }
            1 -> {
                if(comicFragment == null) {
                    comicFragment = adapter.getFragmentComic()
                }
                comicFragment!!.performSearch(query)
            }
            2 -> {
                if(creadorFragment == null) {
                    creadorFragment = adapter.getFragmentCreador()
                }
                creadorFragment!!.performSearch(query)
            }
        }
    }

}