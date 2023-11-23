package es.unex.giiis.marvelbook.ui.mazo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MazoViewModel : ViewModel() {

    private val searchTerm = MutableLiveData<String>()
    fun setSearchTerm(term: String) {
        searchTerm.value = term
    }

    fun getSearchTerm() = searchTerm
}