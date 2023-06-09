package com.example.hueverianietoclientes.ui.views.main.fragment.myorders

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.example.hueverianietoclientes.R
import com.example.hueverianietoclientes.data.network.OrderData
import com.example.hueverianietoclientes.domain.usecase.MyOrdersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyOrdersViewModel @Inject constructor(
    val myOrdersUseCase: MyOrdersUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(MyOrdersViewState())
    val viewState: StateFlow<MyOrdersViewState> get() = _viewState

    private val _orderList = MutableLiveData<List<OrderData?>?>()
    val orderList: LiveData<List<OrderData?>?> get() = _orderList

    fun getOrdersData(documentId: String) {
        _viewState.value = MyOrdersViewState(isLoading = false)
        viewModelScope.launch {
            _viewState.value = MyOrdersViewState(isLoading = true)
            when (val result = myOrdersUseCase(documentId)) {
                null -> {
                    _viewState.value = MyOrdersViewState(isLoading = false, error = true)
                    _orderList.value = listOf()
                }
                listOf<OrderData>() -> {
                    _viewState.value = MyOrdersViewState(isLoading = false, isEmpty = true)
                    _orderList.value = listOf()
                }
                else -> {
                    _viewState.value = MyOrdersViewState(isLoading = false)
                    _orderList.value = result
                }
            }
        }
    }

    fun navigateToOrderDetail(view: View?, bundle: Bundle) {
        view?.findNavController()
            ?.navigate(R.id.action_myOrdersFragment_to_orderDetailFragment, bundle)
            ?: Log.e(
                MyOrdersViewModel::class.java.simpleName,
                "Error en la navegación a Detalle de pedido"
            )
    }

}
