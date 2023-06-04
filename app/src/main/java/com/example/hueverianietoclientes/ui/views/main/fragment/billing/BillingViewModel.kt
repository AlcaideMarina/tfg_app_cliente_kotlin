package com.example.hueverianietoclientes.ui.views.main.fragment.billing

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
import com.example.hueverianietoclientes.domain.model.BillingContainerModel
import com.example.hueverianietoclientes.domain.model.BillingModel
import com.example.hueverianietoclientes.domain.model.OrderBillingModel
import com.example.hueverianietoclientes.domain.usecase.BillingUseCase
import com.example.hueverianietoclientes.ui.views.main.fragment.home.HomeViewModel
import com.example.hueverianietoclientes.ui.views.main.fragment.myorders.MyOrdersViewState
import com.example.hueverianietoclientes.utils.Utils
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class BillingViewModel @Inject constructor(
    val billingUseCase: BillingUseCase
) : ViewModel() {

    private var _viewState = MutableStateFlow(BillingViewState())
    val viewState: StateFlow<BillingViewState> get() = _viewState

    private val _orderList = MutableLiveData<List<OrderData?>?>()
    val orderList: LiveData<List<OrderData?>?> get() = _orderList

    private val _billingContainerList = MutableLiveData<List<BillingContainerModel?>?>()
    val billingContainerList: LiveData<List<BillingContainerModel?>?> get() = _billingContainerList

    fun getBillingData(documentId: String) {
        _viewState.value = BillingViewState(isLoading = false)
        viewModelScope.launch {
            _viewState.value = BillingViewState(isLoading = true)
            when(val result = billingUseCase(documentId)) {
                null -> {
                    _viewState.value = BillingViewState(isLoading = false, error = true)
                    _billingContainerList.value = listOf()
                }
                listOf<OrderData>() -> {
                    _viewState.value = BillingViewState(isLoading = false, isEmpty = true)
                    _billingContainerList.value = listOf()
                }
                else -> {
                    _viewState.value = BillingViewState(isLoading = false)
                    //_orderList.value = result
                    val orderBillingModelList = getOrderBillingModel(result)
                    val list = getBillingContainerFromOrderData(orderBillingModelList)
                    _billingContainerList.value = list
                }
            }
        }
    }

    private fun getOrderBillingModel(orderDataList: List<OrderData?>?) : List<OrderBillingModel> {
        val list = mutableListOf<OrderBillingModel>()
        if (orderDataList != null) {
            for (item in orderDataList) {
                if (item != null) {
                    list.add(
                        OrderBillingModel(
                            item.orderId,
                            item.orderDatetime,
                            item.paymentMethod,
                            item.totalPrice,
                            item.paid
                        )
                    )
                }
            }
        }
        return list
    }

    private fun getBillingContainerFromOrderData(orderBillingModelList: List<OrderBillingModel>) : List<BillingContainerModel> {

        val list = mutableListOf<BillingContainerModel>()
        var orderBillingModelListAux = orderBillingModelList

        var paymentByCash: Double = 0.0
        var paymentByReceipt: Double = 0.0
        var paymentByTransfer: Double = 0.0
        var paid: Double = 0.0
        var toBePaid: Double = 0.0
        var totalPrice: Double = 0.0
        var orderBillingModelMonthlyList = mutableListOf<OrderBillingModel>()

        // Ordenamos la lista por fecha de pedido en desc.
        orderBillingModelListAux = orderBillingModelListAux.sortedBy { it.orderDatetime } .reversed()

        // Cogemos la primera posición -> Es la más reciente -> Último mes
        val firstOrder = orderBillingModelListAux[0]
        val firstDate = firstOrder.orderDatetime.toDate()

        val calendar = Calendar.getInstance()
        calendar.time = firstDate
        var m = (calendar.get(Calendar.MONTH) + 1).toString()
        while (m.length < 2) m = "0" + m
        var y = calendar.get(Calendar.YEAR).toString()
        while (y.length < 4) y = "0" + y

        // Creamos fecha inicial y final
        var initDateTimestamp = Utils.parseStringToTimestamp(
            "01/$m/$y"
        )
        var endDateTimestamp = Timestamp(Utils.addToDate(initDateTimestamp.toDate(), monthsToAdd = 1))

        for (item in orderBillingModelListAux) {
            if (initDateTimestamp > item.orderDatetime) {
                // Añadimos el elemento a la lista de retorno
                val billingModel = BillingModel(
                    paymentByCash = paymentByCash,
                    paymentByReceipt = paymentByReceipt,
                    paymentByTransfer = paymentByTransfer,
                    paid = paid,
                    toBePaid = toBePaid,
                    totalPrice = totalPrice,
                    //orderBillingModelList = orderBillingModelMonthlyList
                )
                val billingContainerModel = BillingContainerModel(
                    initDate = initDateTimestamp,
                    endDate = endDateTimestamp,
                    billingModel = billingModel
                )
                list.add(billingContainerModel)
                // Reseteamos todas las variables y guardamos
                endDateTimestamp = initDateTimestamp
                initDateTimestamp = Timestamp(
                    Utils.addToDate(initDateTimestamp.toDate(), monthsToAdd = -1))
                paymentByCash = 0.0
                paymentByReceipt = 0.0
                paymentByTransfer = 0.0
                paid = 0.0
                toBePaid = 0.0
                totalPrice = 0.0
                orderBillingModelMonthlyList = mutableListOf()
            }

            // Actualizamos métodos de pago
            when(item.paymentMethod.toInt()) {
                 0 -> paymentByCash += (item.totalPrice ?: 0).toDouble()
                 1 -> paymentByReceipt += (item.totalPrice ?: 0).toDouble()
                 2 -> paymentByTransfer += (item.totalPrice ?: 0).toDouble()
            }
            // Actualizamos si es un pedido pagado o por pagar
            when(item.paid) {
                true -> paid += (item.totalPrice ?: 0).toDouble()
                false -> toBePaid += (item.totalPrice ?: 0).toDouble()
            }
            totalPrice += (item.totalPrice ?: 0).toDouble()
            orderBillingModelMonthlyList.add(item)

            if (orderBillingModelListAux.last() == item) {
                val billingModel = BillingModel(
                    paymentByCash = (paymentByCash * 100.0).roundToInt() / 100.0,
                    paymentByReceipt = (paymentByReceipt * 100.0).roundToInt() / 100.0,
                    paymentByTransfer = (paymentByTransfer * 100.0).roundToInt() / 100.0,
                    paid = (paid * 100.0).roundToInt() / 100.0,
                    toBePaid = (toBePaid * 100.0).roundToInt() / 100.0,
                    totalPrice = (totalPrice * 100.0).roundToInt() / 100.0,
                    //orderBillingModelList = orderBillingModelMonthlyList
                )
                val billingContainerModel = BillingContainerModel(
                    initDate = initDateTimestamp,
                    endDate = endDateTimestamp,
                    billingModel = billingModel
                )
                list.add(billingContainerModel)
            }
        }
        return list
    }

    fun navigateToBillingDetail(view: View?, bundle: Bundle) {
        view?.findNavController()?.navigate(R.id.action_billingFragment_to_billingDetailFragment, bundle)
            ?: Log.e(
                BillingViewModel::class.java.simpleName,
                "Error en la navegación a Detalle de factura"
            )
    }

}