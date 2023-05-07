package com.example.hueverianietoclientes.ui.views.main.fragment.myorders

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hueverianietoclientes.base.BaseFragment
import com.example.hueverianietoclientes.base.BaseState
import com.example.hueverianietoclientes.data.network.ClientData
import com.example.hueverianietoclientes.data.network.OrderData
import com.example.hueverianietoclientes.databinding.FragmentMyOrdersBinding
import com.example.hueverianietoclientes.domain.model.OrderContainerModel
import com.example.hueverianietoclientes.ui.components.HNModalDialog
import com.example.hueverianietoclientes.ui.components.hnordercontainer.HNOrderContainerAdapter
import com.example.hueverianietoclientes.ui.views.login.LoginActivity
import com.example.hueverianietoclientes.ui.views.login.LoginViewState
import com.example.hueverianietoclientes.ui.views.main.MainActivity
import com.example.hueverianietoclientes.utils.Constants
import com.example.hueverianietoclientes.utils.OrderUtils
import com.example.hueverianietoclientes.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlin.properties.Delegates

@AndroidEntryPoint
class MyOrdersFragment : BaseFragment() {

    private lateinit var binding: FragmentMyOrdersBinding
    private lateinit var clientData: ClientData
    private val myOrdersViewModel : MyOrdersViewModel by viewModels()
    private var fromNewOrderFragment by Delegates.notNull<Boolean>()
    private lateinit var alertDialog: HNModalDialog
    private var isFirst = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).configNav(true)
        this.binding = FragmentMyOrdersBinding.inflate(
            inflater, container, false
        )

        val args : MyOrdersFragmentArgs by navArgs()
        this.clientData = args.clientData
        if (isFirst) {
            this.fromNewOrderFragment = args.fromNewOrder
            isFirst = false
        } else {
            this.fromNewOrderFragment = false
        }

        return this.binding.root
    }

    override fun configureUI() {
        this.alertDialog = HNModalDialog(requireContext())
        this.myOrdersViewModel.getOrdersData(clientData.documentId)
        lifecycleScope.launchWhenStarted {
            myOrdersViewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }
        if (this.fromNewOrderFragment) {
            Utils.setPopUp(
                alertDialog,
                requireContext(),
                "Pedido realizado",
                "Su pedido se ha realizado correctamente. En un plazo máximo de 24 horas, nos pondremos en contacto con usted para confirmar los datos. ¡Gracias por la confianza!",
                "De acuerdo",
                null,
                {
                    alertDialog.cancel()
                    this.fromNewOrderFragment = false
                },
                null
            )
        }
    }

    override fun setObservers() {
        myOrdersViewModel.orderList.observe(this) { orderDataList ->
            if (orderDataList == null) {
                // Error
            } else {
                val orderList = mutableListOf<OrderContainerModel>()
                for (orderData in orderDataList) {
                    if (orderData != null) {
                        val orderContainerModel = OrderContainerModel(
                            orderData.orderDatetime,
                            orderData.orderId!!,
                            OrderUtils.getOrderSummary(OrderUtils.orderDataToBDOrderModel(orderData)),
                            orderData.totalPrice ?: -1,
                            orderData.status,
                            orderData.deliveryDni
                        ) {
                            this.myOrdersViewModel.navigateToOrderDetail(
                                view, bundleOf(
                                    "orderData" to orderData,
                                    "clientData" to this.clientData
                                ))
                        }
                        orderList.add(orderContainerModel)
                    }
                }
                if (orderList.isEmpty()) {
                    // TODO: Vacío
                } else {
                    this.binding.orderRecyclerView.layoutManager = LinearLayoutManager(context)
                    this.binding.orderRecyclerView.adapter = HNOrderContainerAdapter(orderList)
                    this.binding.orderRecyclerView.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun setListeners() {
        // It is not necessary
    }

    override fun updateUI(state: BaseState) {
        try {
            with(state as MyOrdersViewState) {
                with(binding) {
                    this.loadingComponent.isVisible = state.isLoading
                    if (state.error) {
                        //setPopUp(errorMap(Constants.loginBadFormattedEmailError))
                    } else if (state.isEmpty) {
                        //setPopUp(errorMap(Constants.loginBadFormattedEmailError))
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
        }

    }

    companion object {
        private val TAG = LoginActivity::class.java.simpleName
    }
}