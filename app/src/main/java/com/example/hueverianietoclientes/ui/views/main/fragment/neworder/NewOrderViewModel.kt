package com.example.hueverianietoclientes.ui.views.main.fragment.neworder

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hueverianietoclientes.data.network.OrderData
import com.example.hueverianietoclientes.domain.usecase.NewOrderUseCase
import com.example.hueverianietoclientes.ui.views.login.LoginViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class NewOrderViewModel @Inject constructor(
    val newOrderUseCase: NewOrderUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(NewOrderViewState())
    val viewState: StateFlow<NewOrderViewState> get() = _viewState

    private var _alertDialog = MutableLiveData(NewOrderViewState())
    val alertDialog : LiveData<NewOrderViewState> get() = _alertDialog

    fun addNewOrder() {
    }

    fun checkOrder() {
        // TODO
    }

    fun changePage() {
        if (_viewState.value.step == 1) {
            // Estamos en la pantalla de creación de pedido -> Pasamos a la segunda -> Resumen
        } else {
            // Estamos en el resumen -> Queremos volver a la pantalla de creación para modificar
        }
    }

    fun onClickConfirmButton() {
        // TODO: Comprobar campos
        // TODO: Levantar alertdialog para asegurar

    }

}