package es.unex.giiis.marvelbook.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ComicDAO {

    @Query("SELECT COUNT(*) FROM Comic")
    fun numeroComics(): Int

    @Query("SELECT * FROM Comic WHERE id = :id")
    fun getById(vararg id: Long): Comic

    @Query("SELECT * FROM Comic ORDER BY title")
    fun getAll(): List<Comic>

    @Query("SELECT * FROM Comic WHERE id = :id")
    fun obtenerComic(vararg id: Int): List<Comic>

    @Insert
    fun insertarComic(vararg comic: Comic)

    @Query("DELETE FROM Comic")
    fun eliminarComics()
}