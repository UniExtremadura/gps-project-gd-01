package es.unex.giiis.marvelbook.database

import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

class PersonajeTest {
    private lateinit var personaje: Personaje

    @Before
    fun setUp() {
        personaje = Personaje(1, "Spiderman", "Hombre araña", "imagen", listOf("comic1", "comic2"))
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getId() {
        assertEquals(1, personaje.id)
    }

    @Test
    fun getName() {
        assertEquals("Spiderman", personaje.name)
    }

    @Test
    fun getDescription() {
        assertEquals("Hombre araña", personaje.description)
    }

    @Test
    fun getImagen() {
        assertEquals("imagen", personaje.imagen)
    }

    @Test
    fun getComics() {
        assertEquals(listOf("comic1", "comic2"), personaje.comics)
    }
}