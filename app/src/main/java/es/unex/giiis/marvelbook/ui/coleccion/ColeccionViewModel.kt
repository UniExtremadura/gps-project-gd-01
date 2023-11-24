package es.unex.giiis.marvelbook.ui.coleccion

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ColeccionViewModel : ViewModel() {
    private val searchTerm = MutableLiveData<String>()

    fun setSearchTerm(term: String) {
        searchTerm.value = term
    }

    fun getSearchTerm() = searchTerm
}