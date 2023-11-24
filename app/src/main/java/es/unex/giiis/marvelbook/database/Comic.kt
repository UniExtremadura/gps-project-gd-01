package es.unex.giiis.marvelbook.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Comic(
    @PrimaryKey val id: Int = 0,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "isbn") val isbn: String?,
    @ColumnInfo(name = "format") val format: String?,
    @ColumnInfo(name = "pageCount") val pageCount: Int?,
    @ColumnInfo(name = "imagen") val imagen: String?,
    @ColumnInfo(name = "creators") val creators: List<String>?,
    @ColumnInfo(name = "characters") val characters: List<String>?,
) {
}