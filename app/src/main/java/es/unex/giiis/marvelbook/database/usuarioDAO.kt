package es.unex.giiis.marvelbook.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface usuarioDAO {

    @Query("SELECT * FROM Usuario")
    fun getAll(): List<Usuario>

    @Query("SELECT * FROM Usuario WHERE  email = :email")
    fun findByEmail(email: String?): Usuario?

    @Insert
    fun insertarUsuario(vararg usuario: Usuario)

    @Query("DELETE FROM Usuario")
    fun eliminarUsuarios()

}