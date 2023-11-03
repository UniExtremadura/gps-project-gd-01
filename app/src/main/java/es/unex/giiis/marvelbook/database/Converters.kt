package es.unex.giiis.marvelbook.database

import android.text.TextUtils
import androidx.room.TypeConverter
import java.util.Arrays


class Converters {
    @TypeConverter
    fun fromIntegerList(integerList: List<Int>?): String? {
        if (integerList == null) {
            return null
        }
        return integerList.joinToString(",")
    }

    @TypeConverter
    fun toIntegerList(commaSeparatedString: String?): List<Int>? {
        if (commaSeparatedString.isNullOrEmpty()) {
            return emptyList()
        }
        return commaSeparatedString.split(",").mapNotNull { it.toIntOrNull() }
    }

    @TypeConverter
    fun fromString(value: String): List<String>? {
        // Convierte una cadena (formato de base de datos) en una lista de cadenas
        return Arrays.asList(*value.split(",".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray())
    }

    @TypeConverter
    fun fromList(list: List<String?>?): String? {
        // Convierte una lista de cadenas en una sola cadena (formato de base de datos)
        return TextUtils.join(",", list!!)
    }
}
