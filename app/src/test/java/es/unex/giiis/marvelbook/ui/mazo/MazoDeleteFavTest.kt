package es.unex.giiis.marvelbook.ui.mazo

import android.annotation.SuppressLint
import es.unex.giiis.marvelbook.database.PersonajeMazo
import es.unex.giiis.marvelbook.database.PersonajeMazoDAO
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times

class MazoDeleteFavTest {

    @Mock
    private lateinit var personajeMazoDAO: PersonajeMazoDAO

    @Before
    fun setUp() {
        personajeMazoDAO = Mockito.mock(PersonajeMazoDAO::class.java)
    }

    @SuppressLint("CheckResult")
    @Test
    fun deleteFavPersonaje() {
        //simular acciones
        val personajeMazo = PersonajeMazo(1L, 1, "Spiderman", null, 1, 1, 1, 1, true)
        Mockito.doNothing().`when`(personajeMazoDAO).insertarPersonajeMazo(personajeMazo)

        val modificarPersonajeMazo = PersonajeMazo(1L, 1, "Spiderman", null, 1, 1, 1, 1, false)
        Mockito.doNothing().`when`(personajeMazoDAO).updatePersonajeMazo(modificarPersonajeMazo)

        Mockito.`when`(personajeMazoDAO.getById(1L)).thenReturn(modificarPersonajeMazo)

        //ejecutar las acciones
        personajeMazoDAO.insertarPersonajeMazo(personajeMazo)
        personajeMazoDAO.updatePersonajeMazo(modificarPersonajeMazo)
        val personajeMazo2 = personajeMazoDAO.getById(1L)

        //comprobar acciones
        assertEquals(personajeMazo2.fav, false)
        Mockito.verify(personajeMazoDAO, times(1)).insertarPersonajeMazo(personajeMazo)
        Mockito.verify(personajeMazoDAO, times(1)).updatePersonajeMazo(modificarPersonajeMazo)
        Mockito.verify(personajeMazoDAO, times(1)).getById(1L)
    }
}