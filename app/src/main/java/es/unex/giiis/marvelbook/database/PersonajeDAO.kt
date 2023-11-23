package es.unex.giiis.marvelbook.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PersonajeDAO {

    @Query("SELECT COUNT(*) FROM Personaje")
    fun numeroPersonajes(): Int

    @Query("SELECT * FROM Personaje ORDER BY name")
    fun getAll(): List<Personaje>

    @Query("SELECT * FROM Personaje WHERE id = :id")
    fun getByID(vararg id: Long): Personaje

    @Insert
    fun insertarPersonaje(vararg personaje: Personaje)

    @Query("DELETE FROM Personaje")
    fun eliminarPersonajes()

}
