package es.unex.giiis.marvelbook

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import es.unex.giiis.marvelbook.databinding.ActivityPeleaResultadoBinding

class PeleaResultadoActivity : AppCompatActivity() {

    private var personajeID: Long = 0L
    private var ganador: Boolean = false
    private lateinit var binding: ActivityPeleaResultadoBinding
    private val viewModel: PeleaResultadoViewModel by viewModels { PeleaResultadoViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPeleaResultadoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        personajeID = intent.getLongExtra("personajeID", 0L)
        ganador = intent.getBooleanExtra("ganador", false)

        if(ganador) {
            binding.perdedorPelea.visibility = View.GONE
            binding.monedasPerdidas.visibility = View.GONE

        } else {
            binding.ganadorPelea.visibility = View.GONE
            binding.monedasGanadas.visibility = View.GONE
        }

        viewModel.obtenerPersonajeID(personajeID) { personaje ->

            with(binding) {
                nombrePersonajeGanador.text = personaje.name
                Glide.with(imagenPersonajeGanador.context)
                    .load(personaje.imagen.toString())
                    .into(imagenPersonajeGanador)

                botonMazo.setOnClickListener {
                    val intent = Intent(this@PeleaResultadoActivity, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
    }
}