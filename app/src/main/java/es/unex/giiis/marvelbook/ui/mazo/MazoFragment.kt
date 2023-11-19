package es.unex.giiis.marvelbook.ui.mazo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
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

@Suppress("DEPRECATION")
class MazoFragment : Fragment() {

    private var _binding: FragmentMazoBinding? = null
    private var usuarioSesionID: Long = 0
    private lateinit var user: Usuario
    private lateinit var adapter: PersonajeMazoAdapterMazo
    private lateinit var db: AppDatabase

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
                        if (personaje.fav == false) {
                            personaje.fav = true
                            lifecycleScope.launch(Dispatchers.IO) {
                                db.personajeMazoDAO().updatePersonajeMazo(personaje)
                                personajesMazo = db.personajeMazoDAO().getAll(usuarioSesionID).toMutableList()

                                withContext(Dispatchers.Main) {
                                    adapter.updateList(personajesMazo)
                                    adapter.notifyItemChanged(position)
                                }
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
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}