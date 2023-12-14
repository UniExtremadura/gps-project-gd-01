package es.unex.giiis.marvelbook.ui.tienda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import es.unex.giiis.marvelbook.databinding.ActivitySobreBinding
import es.unex.giiis.marvelbook.adapter.PersonajeMazoAdapterSobre

class SobreActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySobreBinding
    private lateinit var adapter: PersonajeMazoAdapterSobre
    private val viewModel: SobreViewModel by viewModels { SobreViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySobreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bGuardarMazo.setOnClickListener {
            super.onBackPressed()
        }

        when (intent.getIntExtra("sobreTipo", 0)) {
            1 -> {
                setUpRecyclerViewSobreBasico()
            }
            2 -> {
                setUpRecyclerViewSobreEspecial()
            }
            else -> {
                onBackPressed()
            }
        }
    }


    private fun setUpRecyclerViewSobreBasico() {
        viewModel.generarSobreBasico { personajesMazo ->
            adapter = PersonajeMazoAdapterSobre(personajes = personajesMazo)
            with(binding) {
                listadoSobre.layoutManager = LinearLayoutManager(this@SobreActivity)
                listadoSobre.adapter = adapter
            }
        }
    }

    private fun setUpRecyclerViewSobreEspecial() {
        viewModel.generarSobreEspecial { personajesMazo ->
            adapter = PersonajeMazoAdapterSobre(personajes = personajesMazo)
            with(binding) {
                listadoSobre.layoutManager = LinearLayoutManager(this@SobreActivity)
                listadoSobre.adapter = adapter
            }
        }
    }
}