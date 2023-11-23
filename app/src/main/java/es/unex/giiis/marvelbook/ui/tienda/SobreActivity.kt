package es.unex.giiis.marvelbook.ui.tienda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import es.unex.giiis.marvelbook.database.AppDatabase
import es.unex.giiis.marvelbook.database.PersonajeMazo
import es.unex.giiis.marvelbook.databinding.ActivitySobreBinding
import es.unex.giiis.marvelbook.adapter.PersonajeMazoAdapterSobre
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class SobreActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySobreBinding
    private var usuarioSesionID: Long = 0
    private lateinit var adapter: PersonajeMazoAdapterSobre
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySobreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = AppDatabase.getInstance(this)
        usuarioSesionID = intent.getLongExtra("usuarioID", 0L)

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
        lifecycleScope.launch(Dispatchers.IO) {

            val personajes = db.personajeDAO().getAll().toMutableList()

            val numMax = personajes.size
            val numeroAleatorio = Random.nextInt(numMax)

            val personajeSeleccionado = personajes[numeroAleatorio]

            val personaje = PersonajeMazo(
                usuarioID = usuarioSesionID,
                name = personajeSeleccionado.name,
                imagen = personajeSeleccionado.imagen,
                speed = Random.nextInt(100),
                defense = Random.nextInt(100),
                power = Random.nextInt(100),
                rating = 0,
                fav = false
            )

            personaje.rating = (personaje.speed!! + personaje.defense!! + personaje.power!!) / 3

            val personajesMazo = mutableListOf<PersonajeMazo>()
            personajesMazo.add(personaje)
            db.personajeMazoDAO().insertarPersonajeMazo(personaje)

            withContext(Dispatchers.Main) {
                adapter = PersonajeMazoAdapterSobre(personajes = personajesMazo)
                with(binding) {
                    listadoSobre.layoutManager = LinearLayoutManager(this@SobreActivity)
                    listadoSobre.adapter = adapter
                }
            }
        }
    }

    private fun setUpRecyclerViewSobreEspecial() {
        lifecycleScope.launch(Dispatchers.IO) {

            val personajes = db.personajeDAO().getAll().toMutableList()

            val personajesMazo = mutableListOf<PersonajeMazo>()
            for (i in 0..2 ){
                val numMax = personajes.size
                val numeroAleatorio = Random.nextInt(numMax)

                val personajeSeleccionado = personajes[numeroAleatorio]

                val personaje = PersonajeMazo(
                    usuarioID = usuarioSesionID,
                    name = personajeSeleccionado.name,
                    imagen = personajeSeleccionado.imagen,
                    speed = Random.nextInt(100),
                    defense = Random.nextInt(100),
                    power = Random.nextInt(100),
                    rating = 0,
                    fav = false
                )

                personaje.rating = (personaje.speed!! + personaje.defense!! + personaje.power!!) / 3
                personajesMazo.add(personaje)
                db.personajeMazoDAO().insertarPersonajeMazo(personaje)
                personajes.removeAt(numeroAleatorio)
            }

            withContext(Dispatchers.Main) {
                adapter = PersonajeMazoAdapterSobre(personajes = personajesMazo)
                with(binding) {
                    listadoSobre.layoutManager = LinearLayoutManager(this@SobreActivity)
                    listadoSobre.adapter = adapter
                }
            }
        }
    }
}