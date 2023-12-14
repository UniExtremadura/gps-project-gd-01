package es.unex.giiis.marvelbook

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import es.unex.giiis.marvelbook.database.PersonajeMazo
import es.unex.giiis.marvelbook.databinding.ActivityBatallaBinding

class BatallaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBatallaBinding
    private val viewModel: BatallaViewModel by viewModels { BatallaViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBatallaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val personaje1ID = intent.getLongExtra("personajeMazoID", 0L)

        viewModel.obtenerPersonajeID(personaje1ID) { personaje1 ->

            viewModel.obtenerPersonajeAleatorio { personaje2 ->

                setUpView(personaje1, personaje2)
                val ganador = viewModel.gestionarBatalla(personaje1, personaje2)

                binding.imagenFight.setOnClickListener {
                    val intent = Intent(this@BatallaActivity, PeleaResultadoActivity::class.java)
                    intent.putExtra("ganador", ganador)
                    intent.putExtra("personajeID", personaje1ID)
                    startActivity(intent)
                }
            }
        }
    }

    private fun setUpView(personaje1: PersonajeMazo, personaje2: PersonajeMazo) {
        with(binding) {
            valorVelocidad.text = personaje1.speed.toString()
            valorAtaque.text = personaje1.power.toString()
            valorDefensa.text = personaje1.defense.toString()
            Glide.with(imagenPersonaje1.context)
                .load(personaje1.imagen.toString())
                .into(imagenPersonaje1)

            valorVelocidad2.text = personaje2.speed.toString()
            valorDefensa2.text = personaje2.defense.toString()
            valorAtaque2.text = personaje2.power.toString()
            Glide.with(imagenPersonaje2.context)
                .load(personaje2.imagen.toString())
                .into(imagenPersonaje2)
        }
    }

    @Deprecated("Deprecated in Java")
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
    }
}