package com.example.hueverianietoclientes.ui.views.main.fragment.home

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.example.hueverianietoclientes.R
import com.example.hueverianietoclientes.domain.usecase.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val homeUseCase: HomeUseCase) : ViewModel() {

    private val _viewState = MutableStateFlow(HomeViewState())
    val viewState: StateFlow<HomeViewState> get() = _viewState

    // TODO: Falta la función que active el caso de uso - Logout

    fun navigateToMyProfile(view: View?, bundle: Bundle) {
        view?.findNavController()?.navigate(R.id.action_homeFragment_to_myProfileFragment, bundle)
            ?: Log.e(
                HomeViewModel::class.simpleName,
                "Error en la navegación a Mi perfil"
            )
    }

    fun navigateToBilling(view: View?, bundle: Bundle) {
        view?.findNavController()?.navigate(R.id.action_homeFragment_to_billingFragment, bundle)
            ?: Log.e(
                HomeViewModel::class.java.simpleName,
                "Error en la navegación a Facturación"
            )
    }

    fun navigateToMyOrders(view: View?, bundle: Bundle) {
        view?.findNavController()?.navigate(R.id.action_homeFragment_to_myOrdersFragment, bundle)
            ?: Log.e(
                HomeViewModel::class.java.simpleName,
                "Error en la navegación a 'Mis pedidos'"
            )
    }

    fun navigateToNewOrder(view: View?, bundle: Bundle) {
        view?.findNavController()?.navigate(R.id.action_homeFragment_to_newOrderFragment, bundle)
            ?: Log.e(
                HomeViewModel::class.java.simpleName,
                "Error en la navegación a 'Nuevo pedido'"
            )
    }

    fun navigateToSettings(view: View?, bundle: Bundle) {
        view?.findNavController()?.navigate(R.id.action_homeFragment_to_settingsFragment, bundle)
            ?: Log.e(
                HomeViewModel::class.java.simpleName,
                "Error en la navegación a 'Ajustes'"
            )
    }

}
