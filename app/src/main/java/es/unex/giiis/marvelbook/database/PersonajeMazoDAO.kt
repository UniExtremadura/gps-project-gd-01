package es.unex.giiis.marvelbook.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
@Dao
interface PersonajeMazoDAO {

    @Query("SELECT COUNT(*) FROM PersonajeMazo WHERE usuarioID = :usuarioID")
    fun numeroPersonajeMazo(vararg usuarioID: Long): Int

    @Query("SELECT * FROM PersonajeMazo WHERE usuarioID = :usuarioID ORDER BY fav DESC, name ASC")
    fun getAll(vararg usuarioID: Long): List<PersonajeMazo>

    @Insert
    fun insertarPersonajeMazo(vararg personajeMazo: PersonajeMazo)

    @Query("DELETE FROM PersonajeMazo WHERE Id = :id")
    fun eliminarPersonajesMazo(vararg id: Long)

}
