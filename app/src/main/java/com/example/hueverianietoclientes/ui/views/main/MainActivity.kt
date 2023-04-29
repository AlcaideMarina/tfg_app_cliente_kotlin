package com.example.hueverianietoclientes.ui.views.main

import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val mainViewModel : MainViewModel by viewModels()
    lateinit var clientData: ClientData
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
    }

    override fun configureUI() {
        //TODO("Not yet implemented")
    }

    override fun setListeners() {
        //TODO("Not yet implemented")
    }

    override fun setObservers() {
        //TODO("Not yet implemented")
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


}