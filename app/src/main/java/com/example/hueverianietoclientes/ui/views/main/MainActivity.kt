package com.example.hueverianietoclientes.ui.views.main

import android.os.Bundle
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
import com.example.hueverianietoclientes.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun setUp() {
        this.binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(this.binding.topBar)
        navController = binding.fragmentContainerView.getFragment<NavHostFragment>().navController
        this.binding.topBar.setupWithNavController(navController)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        configNav("")
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

    fun configNav(title: String) {
        this.binding.topBar.title = title
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

}