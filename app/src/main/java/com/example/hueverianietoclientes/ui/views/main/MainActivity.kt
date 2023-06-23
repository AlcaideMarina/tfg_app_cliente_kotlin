package com.example.hueverianietoclientes.ui.views.main

import android.os.Build
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.hueverianietoclientes.R
import com.example.hueverianietoclientes.base.BaseActivity
import com.example.hueverianietoclientes.base.BaseState
import com.example.hueverianietoclientes.data.network.ClientData
import com.example.hueverianietoclientes.databinding.ActivityMainBinding
import com.example.hueverianietoclientes.ui.components.HNModalDialog
import com.example.hueverianietoclientes.utils.Utils
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val mainViewModel: MainViewModel by viewModels()
    lateinit var clientData: ClientData
    private lateinit var alertDialog: HNModalDialog

    override fun setUp() {
        clientData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("client_data", ClientData::class.java)!!
        } else {
            intent.getParcelableExtra<ClientData>("client_data")!!
        }

        this.binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(this.binding.topBar)
        navController = binding.fragmentContainerView.getFragment<NavHostFragment>().navController
        this.binding.topBar.setupWithNavController(navController)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        configNav(false)

        this.alertDialog = HNModalDialog(this)
    }

    override fun configureUI() {
        // Not necessary
    }

    override fun setListeners() {
        // Not necessary
    }

    override fun setObservers() {
        // Not necessary
    }

    override fun updateUI(state: BaseState) {
        // Not necessary
    }

    fun configNav(setHome: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(setHome)
    }

    fun changeTopBarName(newName: String) {
        supportActionBar?.title = newName
    }

    fun goBackFragments() {
        onBackPressedDispatcher.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                Utils.setPopUp(
                    alertDialog,
                    this,
                    "Aviso",
                    "¿Estás seguro de que quieres cerrar sesión?",
                    "Cancelar",
                    "Continuar",
                    {
                        alertDialog.cancel()
                    },
                    {
                        alertDialog.cancel()
                        FirebaseAuth.getInstance().signOut()
                        this.mainViewModel.navigateToLogin(applicationContext, this)
                    }
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
