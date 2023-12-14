package es.unex.giiis.marvelbook.utils

import android.content.Context
import es.unex.giiis.marvelbook.api.getNetworkService
import es.unex.giiis.marvelbook.database.AppDatabase

class AppContainer(context: Context?) {

    private val networkService = getNetworkService()
    private val db = AppDatabase.getInstance(context!!)
    val repository = Repository(db.personajeDAO(), db.comicDAO() , db.creadorDAO(), db.usuarioDAO(), db.personajeMazoDAO(), networkService)

}