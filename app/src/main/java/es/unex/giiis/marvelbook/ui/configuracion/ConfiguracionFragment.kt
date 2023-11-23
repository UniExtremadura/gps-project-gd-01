package es.unex.giiis.marvelbook.ui.configuracion

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat
import es.unex.giiis.marvelbook.R
import es.unex.giiis.marvelbook.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ConfiguracionFragment : PreferenceFragmentCompat() {

    private lateinit var db: AppDatabase
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        db = AppDatabase.getInstance(requireContext())

        lifecycleScope.launch(Dispatchers.IO) {
            val user = db.usuarioDAO().getUserById(arguments?.getLong("usuarioID")!!)!!
            val emailPreference = findPreference<EditTextPreference>("email")
            val passwordPreference = findPreference<EditTextPreference>("password")

            emailPreference?.text = user.email.toString()
            emailPreference?.summary = user.email.toString()
            passwordPreference?.text = user.password.toString()
            passwordPreference?.summary = user.password.toString()
        }
    }
}
