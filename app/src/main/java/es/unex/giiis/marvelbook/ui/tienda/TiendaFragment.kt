package es.unex.giiis.marvelbook.ui.tienda

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import es.unex.giiis.marvelbook.databinding.FragmentTiendaBinding



class TiendaFragment : Fragment() {

    private var _binding: FragmentTiendaBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val tiendaViewModel =
            ViewModelProvider(this).get(TiendaViewModel::class.java)

        _binding = FragmentTiendaBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val textView: TextView = binding.textNotifications
        tiendaViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




}

