package es.unex.giiis.marvelbook

import android.app.Application
import es.unex.giiis.marvelbook.utils.AppContainer

class MarvelApplication: Application() {
    lateinit var appContainer: AppContainer
    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(this)
    }
}