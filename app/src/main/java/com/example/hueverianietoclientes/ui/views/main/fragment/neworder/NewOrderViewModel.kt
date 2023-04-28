package com.example.hueverianietoclientes.ui.views.main.fragment.neworder

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.hueverianietoclientes.data.network.DBOrderFieldData
import com.example.hueverianietoclientes.data.network.OrderData
import com.example.hueverianietoclientes.domain.usecase.NewOrderUseCase
import com.example.hueverianietoclientes.ui.components.hngridview.HNGridTextAdapter
import com.example.hueverianietoclientes.ui.views.login.LoginViewState
import com.example.hueverianietoclientes.utils.Constants
import com.example.hueverianietoclientes.utils.OrderUtils
import com.example.hueverianietoclientes.utils.Utils
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NewOrderViewModel @Inject constructor(
    val newOrderUseCase: NewOrderUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(NewOrderViewState())
    val viewState: StateFlow<NewOrderViewState> get() = _viewState

    private var _alertDialog = MutableLiveData(NewOrderViewState())
    val alertDialog : LiveData<NewOrderViewState> get() = _alertDialog

    private var _orderList = MutableLiveData<List<Int?>>()
    val orderList : LiveData<List<Int?>> get() = _orderList

    fun addNewOrder() {

    }

    fun checkOrder(recyclerView: RecyclerView, clientDataId: String,
                   approxDeliveryDatetimeSelected: Timestamp, paymentMethodSelected: Int?) {
        // TODO
        // TODO: Poner el onclickconfirm a true y al acabar a false
        _viewState.value = NewOrderViewState(
            error = false,
            isLoading = true,
            step = 1,
            onClickConfirm = true,
            null
        )

        try {
            val dbOrderFieldData = getOrderStructure(recyclerView)
            if (paymentMethodSelected == null) {
                _viewState.value = NewOrderViewState(
                    error = true,
                    isLoading = false,
                    step = 1,
                    onClickConfirm = false,
                    popUpCode = 0
                )
                _alertDialog.value = NewOrderViewState(
                    error = true,
                    isLoading = false,
                    step = 1,
                    onClickConfirm = false,
                    popUpCode = 0
                )
            }
            else if (dbOrderFieldData == null) {
                _viewState.value = NewOrderViewState(
                    error = true,
                    isLoading = false,
                    step = 1,
                    onClickConfirm = false,
                    popUpCode = 1
                )
                _alertDialog.value = NewOrderViewState(
                    error = true,
                    isLoading = false,
                    step = 1,
                    onClickConfirm = false,
                    popUpCode = 1
                )
            } else {
                val orderFieldMap = OrderUtils.parseDBOrderFieldDataToMap(dbOrderFieldData)
                OrderData(
                    approxDeliveryDatetime = approxDeliveryDatetimeSelected,
                    createdBy = clientDataId,
                    deliveryDatetime = null,
                    deliveryDni = null,
                    deliveryNote = null,
                    deliveryPerson = null,
                    notes = null,
                    order = orderFieldMap,
                    orderDatetime = Timestamp(Date()),
                    orderId = 0,    // TODO: Se cambia al final
                    paid = false,
                    paymentMethod = Constants.paymentMethod[paymentMethodSelected]!!.toLong(),
                    status = 0, // TODO
                    totalPrice = null
                )
                _viewState.value = NewOrderViewState(
                    error = false,
                    isLoading = false,
                    step = 1,
                    onClickConfirm = false,
                    popUpCode = 2
                )
                _alertDialog.value = NewOrderViewState(
                    error = false,
                    isLoading = false,
                    step = 1,
                    onClickConfirm = false,
                    popUpCode = 2
                )
            }

        } catch (e: Exception) {
            Log.e(NewOrderFragment::class.java.simpleName, e.message, e)
            _viewState.value = NewOrderViewState(
                error = true,
                isLoading = false,
                step = 1,
                onClickConfirm = false,
                popUpCode = 3
            )
            _alertDialog.value = NewOrderViewState(
                error = true,
                isLoading = false,
                step = 1,
                onClickConfirm = false,
                popUpCode = 3
            )
        }
    }

    fun changePage(goToPage: Int) {
        if (goToPage == 1) {
            _viewState.value = NewOrderViewState(
                error = false,
                isLoading = false,
                step = 1,
                onClickConfirm = false,
                popUpCode = null
            )
        } else {
            _viewState.value = NewOrderViewState(
                error = false,
                isLoading = false,
                step = 2,
                onClickConfirm = false,
                popUpCode = null
            )
        }
    }

    fun onClickConfirmButton() {
        // TODO: Comprobar campos
        // TODO: Levantar alertdialog para asegurar

    }

    private fun getOrderStructure(recyclerView: RecyclerView) : DBOrderFieldData? {
        val xlDozenValue : Any?
        val xlBoxValue : Any?
        val lDozenValue : Any?
        val lBoxValue : Any?
        val mDozenValue : Any?
        val mBoxValue : Any?
        val sDozenValue : Any?
        val sBoxValue : Any?
        with(recyclerView.adapter as HNGridTextAdapter) {
            xlDozenValue = this.getItemWithPosition(2).response.toString().toIntOrNull()
            xlBoxValue = this.getItemWithPosition(4).response.toString().toIntOrNull()
            lDozenValue = this.getItemWithPosition(7).response.toString().toIntOrNull()
            lBoxValue = this.getItemWithPosition(9).response.toString().toIntOrNull()
            mDozenValue = this.getItemWithPosition(12).response.toString().toIntOrNull()
            mBoxValue = this.getItemWithPosition(14).response.toString().toIntOrNull()
            sDozenValue = this.getItemWithPosition(17).response.toString().toIntOrNull()
            sBoxValue = this.getItemWithPosition(19).response.toString().toIntOrNull()
            _orderList.value = mutableListOf(
                xlDozenValue, xlBoxValue, lDozenValue, lBoxValue,
                mDozenValue, mBoxValue, sDozenValue, sBoxValue
            )
        }
        if ((xlDozenValue != null) || xlBoxValue != null || lDozenValue != null
            || lBoxValue != null || mDozenValue != null || mBoxValue != null ||
            sDozenValue != null || sBoxValue != null) {
            try {
                return DBOrderFieldData(
                    xlBoxPrice = null,
                    xlBoxQuantity = xlBoxValue as Int?,
                    xlDozenPrice = null,
                    xlDozenQuantity = xlDozenValue as Int?,
                    lBoxPrice = null,
                    lBoxQuantity = lBoxValue as Int?,
                    lDozenPrice = null,
                    lDozenQuantity = lDozenValue as Int?,
                    mBoxPrice = null,
                    mBoxQuantity = mBoxValue as Int?,
                    mDozenPrice = null,
                    mDozenQuantity = mDozenValue as Int?,
                    sBoxPrice = null,
                    sBoxQuantity = sBoxValue as Int?,
                    sDozenPrice = null,
                    sDozenQuantity = sDozenValue as Int?,
                )
            } catch (e: Exception) {
                return null
            }
        } else {
            return null
        }
    }


}