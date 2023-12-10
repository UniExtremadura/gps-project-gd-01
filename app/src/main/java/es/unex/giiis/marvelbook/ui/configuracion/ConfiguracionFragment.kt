package es.unex.giiis.marvelbook.ui.configuracion

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat
import es.unex.giiis.marvelbook.R

class ConfiguracionFragment : PreferenceFragmentCompat() {

    private val configuracionViewModel: ConfiguracionViewModel by viewModels { ConfiguracionViewModel.Factory }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        configuracionViewModel.getUsuario()
        val usuario = configuracionViewModel.user

        val emailPreference = findPreference<EditTextPreference>("email")
        val passwordPreference = findPreference<EditTextPreference>("password")

        emailPreference?.text = usuario!!.email.toString()
        emailPreference?.summary = usuario.email.toString()
        passwordPreference?.text = usuario.password.toString()
        passwordPreference?.summary = usuario.password.toString()
    }
}
