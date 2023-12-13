package es.unex.giiis.marvelbook.database

import org.junit.Assert.*
import org.junit.After
import org.junit.Before
import org.junit.Test

class PersonajeMazoTest {
    private lateinit var personajeMazo: PersonajeMazo

    @Before
    fun setUp() {
        personajeMazo = PersonajeMazo(1, 0L, "Spiderman", "imagen", 43, 87, 45, 44, true)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getId() {
        assertEquals(1, personajeMazo.id)
    }

    @Test
    fun getUsuarioID() {
        assertEquals(0L, personajeMazo.usuarioID)
    }

    @Test
    fun getName() {
        assertEquals("Spiderman", personajeMazo.name)
    }

    @Test
    fun getImagen() {
        assertEquals("imagen", personajeMazo.imagen)
    }

    @Test
    fun getSpeed() {
        assertEquals(43, personajeMazo.speed)
    }

    @Test
    fun getDefense() {
        assertEquals(87, personajeMazo.defense)
    }

    @Test
    fun getPower() {
        assertEquals(45, personajeMazo.power)
    }

    @Test
    fun getRating() {
        assertEquals(44, personajeMazo.rating)
    }

    @Test
    fun setRating() {
        personajeMazo.rating = 45
        assertEquals(45, personajeMazo.rating)
    }

    @Test
    fun getFav() {
        assertEquals(true, personajeMazo.fav)
    }

    @Test
    fun setFav() {
        personajeMazo.fav = false
        assertEquals(false, personajeMazo.fav)
    }
}