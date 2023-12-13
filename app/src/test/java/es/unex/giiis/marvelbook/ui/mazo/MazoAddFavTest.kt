package es.unex.giiis.marvelbook.ui.mazo

import android.annotation.SuppressLint
import es.unex.giiis.marvelbook.database.PersonajeMazo
import es.unex.giiis.marvelbook.database.PersonajeMazoDAO
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.times
import org.mockito.Mockito.`when`

class MazoAddFavTest {

    @Mock
    private lateinit var personajeMazoDAO: PersonajeMazoDAO

    @Before
    fun setUp() {
        personajeMazoDAO = Mockito.mock(PersonajeMazoDAO::class.java)
    }

    @SuppressLint("CheckResult")
    @Test
    fun addFavPersonaje() {
        //simular acciones
        val personajeMazo = PersonajeMazo(1L, 1, "Spiderman", null, 1, 1, 1, 1, false)
        doNothing().`when`(personajeMazoDAO).insertarPersonajeMazo(personajeMazo)

        val modificarPersonajeMazo = PersonajeMazo(1L, 1, "Spiderman", null, 1, 1, 1, 1, true)
        doNothing().`when`(personajeMazoDAO).updatePersonajeMazo(modificarPersonajeMazo)

        `when`(personajeMazoDAO.getById(1L)).thenReturn(modificarPersonajeMazo)

        //ejecutar las acciones
        personajeMazoDAO.insertarPersonajeMazo(personajeMazo)
        personajeMazoDAO.updatePersonajeMazo(modificarPersonajeMazo)
        val personajeMazo2 = personajeMazoDAO.getById(1L)

        //comprobar acciones
        assertEquals(personajeMazo2.fav, true)
        Mockito.verify(personajeMazoDAO, times(1)).insertarPersonajeMazo(personajeMazo)
        Mockito.verify(personajeMazoDAO, times(1)).updatePersonajeMazo(modificarPersonajeMazo)
        Mockito.verify(personajeMazoDAO, times(1)).getById(1L)
    }

    @SuppressLint("CheckResult")
    @Test
    fun addFavPersonaje2() {
        //simular acciones
        val personajeMazo = PersonajeMazo(1L, 1, "Spiderman", null, 1, 1, 1, 1, false)
        doNothing().`when`(personajeMazoDAO).insertarPersonajeMazo(personajeMazo)

        val personajeMazo2 = PersonajeMazo(2L, 1, "SpidermanAUX", null, 1, 1, 1, 1, false)
        doNothing().`when`(personajeMazoDAO).insertarPersonajeMazo(personajeMazo2)

        val modificarPersonajeMazo = PersonajeMazo(1L, 1, "Spiderman", null, 1, 1, 1, 1, true)
        doNothing().`when`(personajeMazoDAO).updatePersonajeMazo(modificarPersonajeMazo)

        `when`(personajeMazoDAO.getById(1L)).thenReturn(modificarPersonajeMazo)
        `when`(personajeMazoDAO.getById(2L)).thenReturn(personajeMazo2)

        //ejecutar las acciones
        personajeMazoDAO.insertarPersonajeMazo(personajeMazo)
        personajeMazoDAO.insertarPersonajeMazo(personajeMazo2)
        personajeMazoDAO.updatePersonajeMazo(modificarPersonajeMazo)
        val personajeResult = personajeMazoDAO.getById(1L)
        val personajeResult2 = personajeMazoDAO.getById(2L)

        //comprobar acciones
        assertEquals(personajeResult.fav, true)
        assertEquals(personajeResult2.fav, false)
        Mockito.verify(personajeMazoDAO, times(1)).insertarPersonajeMazo(personajeMazo)
        Mockito.verify(personajeMazoDAO, times(1)).insertarPersonajeMazo(personajeMazo2)
        Mockito.verify(personajeMazoDAO, times(1)).updatePersonajeMazo(modificarPersonajeMazo)
        Mockito.verify(personajeMazoDAO, times(1)).getById(1L)
        Mockito.verify(personajeMazoDAO, times(1)).getById(2L)
    }


}