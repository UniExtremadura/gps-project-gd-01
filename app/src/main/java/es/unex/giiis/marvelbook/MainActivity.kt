package es.unex.giiis.marvelbook

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuPopupHelper
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import es.unex.giiis.marvelbook.databinding.ActivityMainBinding

import es.unex.giiis.marvelbook.ui.configuracion.ConfiguracionFragmentDirections
import es.unex.giiis.marvelbook.ui.cuenta.CuentaFragmentDirections


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    internal lateinit var binding: ActivityMainBinding
    private var usuarioSesionID: Long = 0L

    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as
                NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        usuarioSesionID = intent.getLongExtra("usuarioID", 0L)

        setUpUI()
    }


    private fun setUpUI() {
        binding.navView.setupWithNavController(navController)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_coleccion,
                R.id.navigation_mazo,
                R.id.navigation_tienda
            )
        )

        setSupportActionBar(binding.toolbar)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.ajustesFragment || destination.id == R.id.cuentaFragment || destination.id == R.id.mazoDetallesFragment) {
                binding.navView.visibility = View.GONE
            }
            else{
                binding.navView.visibility = View.VISIBLE
            }
        }
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_settings -> {
                showPopupMenu(binding.toolbar)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    @SuppressLint("RestrictedApi")
    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.inflate(R.menu.popup_menu)

        // Manejar clics en el menú emergente
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_configuracion -> {
                    val action = ConfiguracionFragmentDirections.actionGlobalAjustesFragment(usuarioSesionID)
                    navController.navigate(action)
                    true
                }
                R.id.menu_cuenta -> {
                    val action = CuentaFragmentDirections.actionGlobalCuentaFragment(usuarioSesionID)
                    navController.navigate(action)
                    true
                }
                R.id.menu_cerrarsesion -> {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
        val menuHelper = MenuPopupHelper(this, popupMenu.menu as MenuBuilder, view)
        menuHelper.gravity = Gravity.END
        menuHelper.show()
    }
}