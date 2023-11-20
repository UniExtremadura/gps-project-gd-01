package es.unex.giiis.marvelbook.ui.coleccion.tab

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

private const val NUM_TABS = 3

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private lateinit var personaje : PersonajeFragment
    private lateinit var comic : ComicFragment
    private lateinit var creador : CreadorFragment


    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        if(position == 0){
            personaje = PersonajeFragment()
        }
        if(position == 1){
            comic = ComicFragment()
        }
        if(position == 2){
            creador = CreadorFragment()
        }

        val fragment = when (position) {
            0 -> personaje
            1 -> comic
            2 -> creador
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
        return fragment
    }

    fun getFragmentPersonaje(): PersonajeFragment {
        return personaje
    }

    fun getFragmentComic(): ComicFragment {
        return comic
    }

    fun getFragmentCreador(): CreadorFragment {
        return creador
    }
}