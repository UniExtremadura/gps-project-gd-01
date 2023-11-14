package es.unex.giiis.marvelbook.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CreadorDAO {

    @Query("SELECT COUNT(*) FROM Creador")
    fun numeroCreadores(): Int

    @Query("SELECT * FROM Creador ORDER BY name")
    fun getAll(): List<Creador>

    @Query("SELECT * FROM Creador WHERE id = :id")
    fun obtenerCreador(vararg id: Int): List<Creador>

    @Insert
    fun insertarCreador(vararg creador: Creador)

    @Query("DELETE FROM Creador")
    fun eliminarCreadores()

}
