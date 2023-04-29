package com.example.hueverianietoclientes.ui.views.main.fragment.settings

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.example.hueverianietoclientes.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {

    fun navigateToChangePassword(view: View?, bundle: Bundle) {
        view?.findNavController()?.navigate(R.id.action_settingsFragment_to_changePasswordFragment, bundle)
            ?: Log.e(
                SettingsViewModel::class.java.simpleName,
                "Error en la navegación a Cambiar contraseña"
            )
    }

}