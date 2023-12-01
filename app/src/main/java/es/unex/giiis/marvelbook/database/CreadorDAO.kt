package es.unex.giiis.marvelbook.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CreadorDAO {

    @Query("SELECT count(*) FROM Creador")
    suspend fun numeroCreadores(): Long

    @Query("SELECT * FROM Creador ORDER BY name")
    fun getAll(): List<Creador>

    @Query("SELECT * FROM Creador ORDER BY name")
    fun getAll1(): LiveData<List<Creador>>

    @Query("SELECT * FROM Creador WHERE id = :id")
    fun obtenerCreador(vararg id: Int): List<Creador>

    @Query("SELECT * FROM Creador WHERE id = :id")
    fun getByID(vararg id: Long): Creador

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarCreador(vararg creador: Creador)

    @Query("DELETE FROM Creador")
    fun eliminarCreadores()

}
