package es.unex.giiis.marvelbook.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ComicDAO {
    @Query("SELECT count(*) FROM Comic")
    suspend fun numeroComics(): Long

    @Query("SELECT * FROM Comic WHERE id = :id")
    fun getById(vararg id: Long): Comic

    @Query("SELECT * FROM Comic ORDER BY title")
    fun getAll(): List<Comic>

    @Query("SELECT * FROM Comic ORDER BY title")
    fun getAll1(): LiveData<List<Comic>>

    @Query("SELECT * FROM Comic WHERE id = :id")
    fun obtenerComic(vararg id: Int): List<Comic>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarComic(vararg comic: Comic)

    @Query("DELETE FROM Comic")
    fun eliminarComics()
}