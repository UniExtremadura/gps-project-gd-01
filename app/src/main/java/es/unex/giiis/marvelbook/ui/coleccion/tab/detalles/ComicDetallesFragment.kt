package es.unex.giiis.marvelbook.ui.coleccion.tab.detalles

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import es.unex.giiis.marvelbook.R
import es.unex.giiis.marvelbook.database.AppDatabase
import es.unex.giiis.marvelbook.database.Comic
import es.unex.giiis.marvelbook.databinding.FragmentComicDetallesBinding
import es.unex.giiis.marvelbook.ui.coleccion.tab.detalles.comicListado.ComicDetallesCreadoresFragmentDirections
import es.unex.giiis.marvelbook.ui.coleccion.tab.detalles.comicListado.ComicDetallesPersonajesFragmentDirections

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ComicDetallesFragment : Fragment() {

    private lateinit var db: AppDatabase

    private var _binding: FragmentComicDetallesBinding? = null
    private val binding get() = _binding!!


    private lateinit var comic: Comic

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        db = AppDatabase.getInstance(requireContext())
        var comicID = arguments?.getLong("comicID")!!

        lifecycleScope.launch(Dispatchers.IO) {
            comic = db.comicDAO().getById(comicID)
        }

        _binding = FragmentComicDetallesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()

        requireActivity().findViewById<Toolbar>(R.id.toolbar)?.apply {
            title = comic.title
        }


        with(binding) {
            if(comic.description == null || comic.description == "") {
                descripcionComic.text = getString(R.string.valorNuloDescripcion)
            }else{
                descripcionComic.text = comic.description
            }
            if(comic.pageCount == null || comic.pageCount == 0) {
                valorNpaginas.text = getString(R.string.valorNulopaginas)
            }else{
                valorNpaginas.text = comic.pageCount.toString()
            }

            if(comic.isbn == null || comic.isbn == "") {
                valorISBN.text = getString(R.string.valorNuloISBN)
            }else{
                valorISBN.text = comic.isbn
            }



            Glide.with(foto.context)
                .load(comic.imagen.toString())
                .into(foto)

            bVerPersonajes.setOnClickListener{
                val action = ComicDetallesPersonajesFragmentDirections.actionGlobalComicDetallesPersonajesFragment(
                    comic.id.toLong()
                )
                navController.navigate(action)
            }

            bVerCreadores.setOnClickListener{
                val action = ComicDetallesCreadoresFragmentDirections.actionGlobalComicDetallesCreadoresFragment(
                    comic.id.toLong()
                )
                navController.navigate(action)
            }
        }
    }


}
