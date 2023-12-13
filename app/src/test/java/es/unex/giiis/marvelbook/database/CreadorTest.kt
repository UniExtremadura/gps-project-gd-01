package es.unex.giiis.marvelbook.database

import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

class CreadorTest {
    private lateinit var creador: Creador

    @Before
    fun setUp() {
        creador = Creador(1, "Stan Lee", "imagen", listOf("comics"))
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getId() {
        assertEquals(1, creador.id)
    }

    @Test
    fun getName() {
        assertEquals("Stan Lee", creador.name)
    }

    @Test
    fun getImagen() {
        assertEquals("imagen", creador.imagen)
    }

    @Test
    fun getComics() {
        assertEquals(listOf("comics"), creador.comics)
    }
}