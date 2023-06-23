package com.example.hueverianietoclientes.ui.views.main

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.example.hueverianietoclientes.domain.usecase.HomeUseCase
import com.example.hueverianietoclientes.ui.views.login.LoginActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val homeUseCase: HomeUseCase) : ViewModel() {

    fun navigateToLogin(context: Context, activity: MainActivity) {
        val intent = Intent(context, LoginActivity::class.java)
        context.startActivity(intent)
        activity.finish()
    }

}
