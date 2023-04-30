package com.example.hueverianietoclientes.ui.views.main.fragment.billing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hueverianietoclientes.data.network.OrderData
import com.example.hueverianietoclientes.domain.model.BillingContainerModel
import com.example.hueverianietoclientes.domain.model.BillingModel
import com.example.hueverianietoclientes.domain.model.OrderBillingModel
import com.example.hueverianietoclientes.domain.usecase.BillingUseCase
import com.example.hueverianietoclientes.ui.views.main.fragment.myorders.MyOrdersViewState
import com.example.hueverianietoclientes.utils.Utils
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

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

        var paymentByCash: Long = 0
        var paymentByReceipt: Long = 0
        var paymentByTransfer: Long = 0
        var paid: Long = 0
        var toBePaid: Long = 0
        var totalPrice: Long = 0
        var orderBillingModelMonthlyList = mutableListOf<OrderBillingModel>()

        // Ordenamos la lista por fecha de pedido en desc.
        orderBillingModelList.sortedBy { it.orderDatetime } .reversed()

        // Cogemos la primera posición -> Es la más reciente -> Último mes
        val firstOrder = orderBillingModelList[0]
        val firstDate = firstOrder.orderDatetime.toDate()

        val calendar = Calendar.getInstance()
        calendar.time = firstDate
        var m = calendar.get(Calendar.MONTH).toString()
        if (m.length < 2) m = "0" + m
        var y = calendar.get(Calendar.YEAR).toString()
        if (y.length < 4) y = "0" + y

        // Creamos fecha inicial y final
        var initDateTimestamp = Utils.parseStringToTimestamp(
            "01/$m/$y"
        )
        var endDateTimestamp = Timestamp(Utils.addToDate(initDateTimestamp.toDate(), monthsToAdd = 1))

        var cont = 0

        for (item in orderBillingModelList) {
            if (initDateTimestamp > orderBillingModelList[cont].orderDatetime) {
                //&& orderBillingModelList[cont].orderDatetime < endDateTimestamp) {
                // Actualizamos métodos de pago
               /* when(item.paymentMethod.toInt()) {
                    0 -> paymentByCash += item.totalPrice ?: 0
                    1 -> paymentByReceipt += item.totalPrice ?: 0
                    2 -> paymentByTransfer += item.totalPrice ?: 0
                }
                // Actualizamos si es un pedido pagado o por pagar
                when(item.paid) {
                    true -> paid += item.totalPrice ?: 0
                    false -> toBePaid += item.totalPrice ?: 0
                }
                totalPrice += totalPrice
                orderBillingModelMonthlyList.add(item)
            } else {*/
                // Añadimos el elemento a la lista de retorno
                val billingModel = BillingModel(
                    paymentByCash = paymentByCash,
                    paymentByReceipt = paymentByReceipt,
                    paymentByTransfer = paymentByTransfer,
                    paid = paid,
                    toBePaid = toBePaid,
                    totalPrice = totalPrice,
                    orderBillingModelList = orderBillingModelMonthlyList
                )
                val billingContainerModel = BillingContainerModel(
                    initDate = initDateTimestamp,
                    endDate = endDateTimestamp,
                    billingModel = billingModel
                )
                list.add(billingContainerModel)
                // Reseteamos todas las variables y guardamos
                endDateTimestamp = initDateTimestamp
                initDateTimestamp = Timestamp(Utils.addToDate(initDateTimestamp.toDate(), monthsToAdd = -1))
                paymentByCash = 0
                paymentByReceipt = 0
                paymentByTransfer = 0
                paid = 0
                toBePaid = 0
                totalPrice = 0
                orderBillingModelMonthlyList = mutableListOf<OrderBillingModel>()
            }

            // Actualizamos métodos de pago
            when(item.paymentMethod.toInt()) {
                 0 -> paymentByCash += item.totalPrice ?: 0
                 1 -> paymentByReceipt += item.totalPrice ?: 0
                 2 -> paymentByTransfer += item.totalPrice ?: 0
            }
            // Actualizamos si es un pedido pagado o por pagar
            when(item.paid) {
                true -> paid += item.totalPrice ?: 0
                false -> toBePaid += item.totalPrice ?: 0
            }
            totalPrice += totalPrice
            orderBillingModelMonthlyList.add(item)

            if (orderBillingModelList.last() == item) {
                val billingModel = BillingModel(
                    paymentByCash = paymentByCash,
                    paymentByReceipt = paymentByReceipt,
                    paymentByTransfer = paymentByTransfer,
                    paid = paid,
                    toBePaid = toBePaid,
                    totalPrice = totalPrice,
                    orderBillingModelList = orderBillingModelMonthlyList
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

}