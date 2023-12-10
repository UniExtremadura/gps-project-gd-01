package es.unex.giiis.marvelbook

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import es.unex.giiis.marvelbook.database.AppDatabase
import es.unex.giiis.marvelbook.database.PersonajeMazo
import es.unex.giiis.marvelbook.database.Usuario
import es.unex.giiis.marvelbook.databinding.ActivityBatallaBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class BatallaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBatallaBinding
    private lateinit var db: AppDatabase
    private lateinit var user: Usuario
    private val batallaViewModel: BatallaViewModel by viewModels { BatallaViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBatallaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(applicationContext)

        val personaje1ID = intent.getLongExtra("personajeMazoID", 0L)
        user = batallaViewModel.getUsuario()

        lifecycleScope.launch(Dispatchers.IO) {
            val personaje1 = db.personajeMazoDAO().getById(personaje1ID)

            val personaje2 = obtenerPersonajeAleatorio()

            withContext(Dispatchers.Main) {
                setUpView(personaje1, personaje2)
            }
            val ganador = gestionarBatalla(personaje1, personaje2)

            withContext(Dispatchers.Main) {
                binding.imagenFight.setOnClickListener {
                    val intent = Intent(this@BatallaActivity, PeleaResultadoActivity::class.java)
                    intent.putExtra("ganador", ganador)
                    intent.putExtra("personajeID", personaje1ID)
                    startActivity(intent)
                }
            }
        }
    }

    private fun obtenerPersonajeAleatorio(): PersonajeMazo {
        val listadoPersonajes = db.personajeDAO().getAll()

        val personajeElegido = listadoPersonajes[Random.nextInt(listadoPersonajes.size)]

        val personaje2 = PersonajeMazo(
            usuarioID = null,
            name = personajeElegido.name,
            imagen = personajeElegido.imagen,
            speed = Random.nextInt(100),
            defense = Random.nextInt(100),
            power = Random.nextInt(100),
            rating = 0,
            fav = false
        )

        personaje2.rating = (personaje2.speed!! + personaje2.defense!! + personaje2.power!!) / 3

        return personaje2
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


    private fun gestionarBatalla(personaje1: PersonajeMazo, personaje2: PersonajeMazo): Boolean {
        val ganador = personaje1.rating!! >= personaje2.rating!!

        if (!ganador) {
            if (user.monedas!! < 5) {
                user.monedas = 0
            } else {
                user.monedas = user.monedas!! - 5
            }
        } else {
            user.monedas = user.monedas!! + 3
        }
        db.usuarioDAO().updateUsuario(user)
        return ganador
    }

    @Deprecated("Deprecated in Java")
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
    }
}