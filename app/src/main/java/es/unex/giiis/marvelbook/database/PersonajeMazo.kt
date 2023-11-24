package es.unex.giiis.marvelbook.database


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class PersonajeMazo (
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "usuarioID") val usuarioID: Long?,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "imagen") val imagen: String?,
    @ColumnInfo(name = "speed") val speed: Int?,
    @ColumnInfo(name = "defense") val defense: Int?,
    @ColumnInfo(name = "power") val power: Int?,
    @ColumnInfo(name = "rating") var rating: Int?,
    @ColumnInfo(name = "fav") var fav: Boolean?,
): Serializable