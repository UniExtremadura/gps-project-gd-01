package es.unex.giiis.marvelbook.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Creador(
    @PrimaryKey val id: Int = 0,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "imagen") val imagen: String?,
    @ColumnInfo(name = "comics") val comics: List<String>?,
): Serializable