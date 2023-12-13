package es.unex.giiis.marvelbook.database

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UsuarioTest {
    private lateinit var usuario: Usuario

    @Before
    fun setUp() {
        usuario = Usuario(1, "grupo1", "grupo1@email.com", "gD01", 33)
    }

    @Test
    fun getId() {
        assertEquals(1, usuario.id)
    }

    @Test
    fun getNombre() {
        assertEquals("grupo1", usuario.nombre)
    }

    @Test
    fun getEmail() {
        assertEquals("grupo1@email.com", usuario.email)
    }

    @Test
    fun getPassword() {
        assertEquals("gD01", usuario.password)
    }

    @Test
    fun getMonedas() {
        assertEquals(33, usuario.monedas)
    }

    @Test
    fun setMonedas() {
        usuario.monedas = 1
        assertEquals(1, usuario.monedas)
    }
}