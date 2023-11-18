package es.unex.giiis.marvelbook.ui.coleccion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import es.unex.giiis.marvelbook.R
import es.unex.giiis.marvelbook.database.AppDatabase
import es.unex.giiis.marvelbook.databinding.FragmentColeccionBinding
import es.unex.giiis.marvelbook.ui.coleccion.tab.ViewPagerAdapter

@Suppress("DEPRECATION")
class ColeccionFragment : Fragment() {

    private var _binding: FragmentColeccionBinding? = null
    private lateinit var db: AppDatabase

    private val binding get() = _binding!!

    val tabArray = arrayOf(
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
        val tabLayout = binding.tabLayout

        val adapter = ViewPagerAdapter(childFragmentManager, lifecycle)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabArray[position]
        }.attach()
        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_search, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}