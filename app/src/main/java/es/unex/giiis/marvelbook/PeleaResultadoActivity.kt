package es.unex.giiis.marvelbook

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import es.unex.giiis.marvelbook.database.AppDatabase
import es.unex.giiis.marvelbook.databinding.ActivityPeleaResultadoBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PeleaResultadoActivity : AppCompatActivity() {

    private var personajeID: Long = 0L
    private var ganador: Boolean = false
    private lateinit var binding: ActivityPeleaResultadoBinding
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPeleaResultadoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(applicationContext)

        personajeID = intent.getLongExtra("personajeID", 0L)
        ganador = intent.getBooleanExtra("ganador", false)

        if(ganador) {
            binding.perdedorPelea.visibility = View.GONE
            binding.monedasPerdidas.visibility = View.GONE

        } else {
            binding.ganadorPelea.visibility = View.GONE
            binding.monedasGanadas.visibility = View.GONE
        }

        lifecycleScope.launch(Dispatchers.IO) {
            val personaje = db.personajeMazoDAO().getById(personajeID)

            withContext(Dispatchers.Main) {
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
    }

    @Deprecated("Deprecated in Java")
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
    }
}