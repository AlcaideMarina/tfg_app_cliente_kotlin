package com.example.hueverianietoclientes.ui.views.main.fragment.home

import android.content.Context
import android.os.Parcelable
import androidx.lifecycle.ViewModel
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

    fun navigateToMyProfile(context: Context, clientData: Parcelable?) {
        // TODO
    }

    fun navigateToFacturation(context: Context, clientData: Parcelable?) {
        // TODO
    }

    fun navigateToMyOrders(context: Context, clientData: Parcelable?) {
        // TODO
    }

    fun navigateToNewOrder(context: Context, clientData: Parcelable?) {
        // TODO
    }

    fun navigateToSettings(context: Context, clientData: Parcelable?) {
        // TODO
    }

    fun navigateToLogin(context: Context, clientData: Parcelable?) {
        // TODO
    }

}
