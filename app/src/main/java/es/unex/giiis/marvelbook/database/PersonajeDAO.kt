package es.unex.giiis.marvelbook.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PersonajeDAO {
    @Query("SELECT count(*) FROM Personaje")
    suspend fun numeroPersonajes(): Long

    @Query("SELECT * FROM Personaje ORDER BY name")
    fun getAll(): List<Personaje>

    @Query("SELECT * FROM Personaje ORDER BY name")
    fun getAll1(): LiveData<List<Personaje>>

    @Query("SELECT * FROM Personaje WHERE id = :id")
    fun getByID(vararg id: Long): Personaje

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarPersonaje(vararg personaje: Personaje)

    @Query("DELETE FROM Personaje")
    fun eliminarPersonajes()

}
