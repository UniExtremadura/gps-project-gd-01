package es.unex.giiis.marvelbook.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import androidx.room.TypeConverters


@Database(entities = [Personaje::class,Usuario::class,Comic::class,Creador::class,PersonajeMazo::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun personajeDAO(): PersonajeDAO
    abstract fun usuarioDAO(): UsuarioDAO
    abstract fun comicDAO(): ComicDAO
    abstract fun creadorDAO(): CreadorDAO
    abstract fun personajeMazoDAO(): PersonajeMazoDAO


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "AppDatabase"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}