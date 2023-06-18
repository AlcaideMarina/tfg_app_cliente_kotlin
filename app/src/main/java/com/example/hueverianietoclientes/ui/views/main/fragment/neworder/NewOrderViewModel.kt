package com.example.hueverianietoclientes.ui.views.main.fragment.neworder

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.example.hueverianietoclientes.R
import com.example.hueverianietoclientes.data.network.EggPricesData
import com.example.hueverianietoclientes.data.network.OrderData
import com.example.hueverianietoclientes.domain.usecase.GetOrderIdUseCase
import com.example.hueverianietoclientes.domain.usecase.GetPricesUseCase
import com.example.hueverianietoclientes.domain.usecase.NewOrderUseCase
import com.example.hueverianietoclientes.ui.views.main.MainActivity
import com.example.hueverianietoclientes.ui.views.main.fragment.home.HomeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewOrderViewModel @Inject constructor(
    val newOrderUseCase: NewOrderUseCase,
    val getOrderIdUseCase: GetOrderIdUseCase,
    val getPricesUseCase: GetPricesUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(NewOrderViewState())
    val viewState: StateFlow<NewOrderViewState> get() = _viewState

    private var _alertDialog = MutableLiveData(NewOrderViewState())
    val alertDialog: LiveData<NewOrderViewState> get() = _alertDialog

    private var _orderList = MutableLiveData<List<Int?>>()
    val orderList: LiveData<List<Int?>> get() = _orderList

    private var _orderData = MutableLiveData<OrderData>()
    val orderData: LiveData<OrderData> get() = _orderData

    private var _eggPrices = MutableLiveData(EggPricesData())
    val eggPrices: LiveData<EggPricesData> get() = _eggPrices

    fun addNewOrder(clientDocumentId: String, orderData: OrderData) {
        viewModelScope.launch {
            _viewState.value = NewOrderViewState(
                error = false,
                isLoading = true,
                step = 2,
                null
            )
            when (val orderId = getOrderIdUseCase(clientDocumentId)) {
                null -> {
                    _viewState.value = NewOrderViewState(
                        error = true,
                        isLoading = false,
                        step = 2,
                        4
                    )
                }
                else -> {
                    orderData.orderId = orderId
                    when (newOrderUseCase(clientDocumentId, orderData)) {
                        false -> {
                            _viewState.value = NewOrderViewState(
                                error = true,
                                isLoading = false,
                                step = 2,
                                4
                            )
                        }
                        true -> {
                            _viewState.value = NewOrderViewState(
                                error = false,
                                isLoading = false,
                                step = 3,
                                null
                            )
                        }
                    }
                }
            }
        }
    }

    fun changePage(goToPage: Int) {
        if (goToPage == 1) {
            _viewState.value = NewOrderViewState(
                error = false,
                isLoading = false,
                step = 1,
                popUpCode = null
            )
        } else {
            _viewState.value = NewOrderViewState(
                error = false,
                isLoading = false,
                step = 2,
                popUpCode = null
            )
        }
    }

    fun getPrices() {
        viewModelScope.launch {
            _viewState.value = NewOrderViewState(isLoading = true)
            when (val result = getPricesUseCase()) {
                null -> {
                    _viewState.value = NewOrderViewState(isLoading = false, error = true)
                    _eggPrices.value = EggPricesData()
                }
                else -> {
                    _viewState.value = NewOrderViewState(isLoading = false)
                    _eggPrices.value = result
                }
            }
        }
    }

    fun navigateToMyOrders(view: View?, bundle: Bundle, activity: FragmentActivity) {
        (activity as MainActivity).goBackFragments()
        view?.findNavController()?.navigate(R.id.action_homeFragment_to_myOrdersFragment, bundle)
            ?: Log.e(
                HomeViewModel::class.java.simpleName,
                "Error en la navegación a 'Mis pedidos'"
            )
    }
}