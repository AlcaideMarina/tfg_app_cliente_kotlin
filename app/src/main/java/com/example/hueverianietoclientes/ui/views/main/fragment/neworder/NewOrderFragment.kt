package com.example.hueverianietoclientes.ui.views.main.fragment.neworder

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ScrollView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.hueverianietoclientes.R
import com.example.hueverianietoclientes.base.BaseFragment
import com.example.hueverianietoclientes.base.BaseState
import com.example.hueverianietoclientes.data.network.ClientData
import com.example.hueverianietoclientes.data.network.EggPricesData
import com.example.hueverianietoclientes.data.network.OrderData
import com.example.hueverianietoclientes.databinding.FragmentNewOrderBinding
import com.example.hueverianietoclientes.ui.components.HNModalDialog
import com.example.hueverianietoclientes.ui.components.hngridview.CustomGridLayoutManager
import com.example.hueverianietoclientes.ui.components.hngridview.HNGridTextAdapter
import com.example.hueverianietoclientes.ui.views.login.LoginActivity
import com.example.hueverianietoclientes.ui.views.main.MainActivity
import com.example.hueverianietoclientes.utils.Constants
import com.example.hueverianietoclientes.utils.OrderUtils
import com.example.hueverianietoclientes.utils.Utils
import com.google.firebase.Timestamp
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class NewOrderFragment : BaseFragment() {

    private lateinit var binding: FragmentNewOrderBinding
    private lateinit var clientData: ClientData
    private lateinit var alertDialog: HNModalDialog
    private var orderList: List<Int?> = mutableListOf()
    private var dropdownPaymentMethodItems: MutableList<String> = mutableListOf()
    private val newOrderViewModel: NewOrderViewModel by viewModels()
    private lateinit var approxDeliveryDatetimeSelected : Timestamp
    private var orderData : OrderData? = null

    private val recyclerViewTitles = listOf(0, 7, 14, 21)
    private val recyclerViewSubtitles = listOf(1, 3, 4, 6, 8, 10, 11, 13, 15, 17, 18, 20, 22, 24, 25, 27)
    private val recyclerViewTextInputLayouts = listOf(2, 5, 9, 12, 16, 19, 23, 26)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).configNav(true)
        this.binding = FragmentNewOrderBinding.inflate(
            inflater, container, false
        )
        val args : NewOrderFragmentArgs by navArgs()
        this.clientData = args.clientData

        return this.binding.root
    }

    override fun configureUI() {
        this.newOrderViewModel.getPrices()

        getPaymentMethodDropdownValues()
        setRecyclerView(EggPricesData())
        setClientDataFields()

        this.binding.deliveryDateTextInputLayout.setInputType(InputType.TYPE_DATETIME_VARIATION_NORMAL)
        approxDeliveryDatetimeSelected = Timestamp(Utils.addToDate(Date(), 3))
        this.binding.deliveryDateTextInputLayout.setText(
            Utils.parseDateToString(approxDeliveryDatetimeSelected.toDate())
        )
        this.binding.deliveryDateTextInputLayout.setOnClickListener { onClickScheduledDate() }
        this.binding.confirmButton.setText("CONFIRMAR")
        this.binding.modifyButton.setText("Modificar datos")
        this.alertDialog = HNModalDialog(requireContext())

        lifecycleScope.launchWhenStarted {
            newOrderViewModel.viewState.collect {viewState ->
                updateUI(viewState)
            }
        }
    }

    override fun setObservers() {
        this.newOrderViewModel.alertDialog.observe(this) { newOrderViewState ->
            when(newOrderViewState.popUpCode) {
                0 -> Utils.setPopUp(
                    alertDialog,
                    requireContext(),
                        "Formulario incorrecto",
                        "Por favor, compruebe los datos. Hay errores o faltan campos por rellenar.",
                        "De acuerdo",
                        null,
                        { alertDialog.cancel() },
                        null
                    )
                1 -> Utils.setPopUp(
                    alertDialog,
                    requireContext(),
                        "Formulario incorrecto",
                        "Por favor, compruebe los datos de los productos. Se han introducido caracteres no válidos (sólo son válidos numeros enteros) o no se ha pedido ningún producto.",
                        "De acuerdo",
                        null,
                        { alertDialog.cancel() },
                        null
                    )
                2 -> Utils.setPopUp(
                    alertDialog,
                    requireContext(),
                    "Precio final",
                    "El precio total del pedido será de TODO €. ¿Desea continuar?",     // TODO
                    "Atrás",
                    "Continuar",
                        { alertDialog.cancel() },
                        {
                            alertDialog.cancel()
                            this.newOrderViewModel.changePage(3)
                        }
                    )
                3 -> Utils.setPopUp(
                    alertDialog,
                    requireContext(),
                        "Se ha producido un error",
                        "Sentimos comunicarle que se ha producido un error inesperado durante el pedido. Por favor, inténtelo más tarde o póngase en contacto con nosotros.",
                        "De acuerdo",
                        null,
                        { alertDialog.cancel() },
                        null
                    )
                4 -> Utils.setPopUp(
                    alertDialog,
                    requireContext(),
                    "Error de servicio",
                    "Sentimos comunicarle que se ha producido un error al intentar guardar el pediod en base de datos. Por favor, inténtelo más tarde o póngase en contacto con nosotros.",
                    "De acuerdo",
                    null,
                    { alertDialog.cancel() },
                    null
                )
            }

        }
        this.newOrderViewModel.orderList.observe(this) {
            orderList = it
        }
        this.newOrderViewModel.orderData.observe(this) {
            orderData = it
            if (orderData != null) {
                this.newOrderViewModel.addNewOrder(
                    clientData.documentId, this.newOrderViewModel.orderData.value!!)
            } else {
                Utils.setPopUp(
                    alertDialog,
                    requireContext(),
                    "Se ha producido un error",
                    "Sentimos comunicarle que se ha producido un error inesperado durante el pedido. Por favor, inténtelo más tarde o póngase en contacto con nosotros.",
                    "De acuerdo",
                    null,
                    { alertDialog.cancel() },
                    null
                )
            }
        }
        this.newOrderViewModel.eggPrices.observe(this) {
            setRecyclerView(it)
        }
    }

    override fun setListeners() {
        this.binding.confirmButton.setOnClickListener {
            it.hideSoftInput()
                val paymentMethodSelected = when (this.binding.paymentMethodAutoCompleteTextView.text.toString()) {
                    requireContext().getString(R.string.in_cash) -> R.string.in_cash
                    requireContext().getString(R.string.per_receipt) -> R.string.per_receipt
                    requireContext().getString(R.string.transfer) -> R.string.transfer
                    else -> null
                }

                val dbOrderFieldData = OrderUtils.getOrderStructure(this.binding.orderRecyclerView)

            if (paymentMethodSelected == null) {
                Utils.setPopUp(
                    alertDialog,
                    requireContext(),
                    "Faltan datos",
                    "El método de pago seleccionado no es válido. Por favor, revise los datos e inténtelo de nuevo",
                    "De acuerdo",
                    null,
                    { alertDialog.cancel() },
                    null
                )
            } else if (dbOrderFieldData == null) {
                Utils.setPopUp(
                    alertDialog,
                    requireContext(),
                    "Faltan datos",
                    "Se debe seleccionar, al menos, un producto para realizar el pedido. Por favor, revise los datos e inténtelo de nuevo",
                    "De acuerdo",
                    null,
                    { alertDialog.cancel() },
                    null
                )
            } else {
                val orderFieldMap = OrderUtils.parseDBOrderFieldDataToMap(dbOrderFieldData)
                val totalPrice = OrderUtils.getTotalPrice(dbOrderFieldData)

                Utils.setPopUp(
                    alertDialog,
                    requireContext(),
                    "Precio final",
                    "El precio total del pedido será de $totalPrice €. ¿Desea continuar?",
                    "Atrás",
                    "Continuar",
                    { alertDialog.cancel() },
                    {
                        alertDialog.cancel()
                        continueOrder(
                            orderFieldMap,
                            paymentMethodSelected,
                            totalPrice
                        )
                    }
                )
            }

        }

        this.binding.modifyButton.setOnClickListener {
            it.hideSoftInput()
            this.newOrderViewModel.changePage(1)
            this.binding.scrollView.fullScroll(ScrollView.FOCUS_UP)
            (activity as MainActivity).changeTopBarName("Nuevo pedido")
        }

        this.binding.paymentMethodAutoCompleteTextView.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                v.hideSoftInput()
            }
        }
    }

    private fun getPaymentMethodDropdownValues() {
        val values = Constants.paymentMethod.entries.iterator()
        for (v in values) {
            dropdownPaymentMethodItems.add(requireContext().getString(v.key))
        }
        this.binding.paymentMethodAutoCompleteTextView.setAdapter(
            ArrayAdapter(
                requireContext(), R.layout.component_dropdown_list_item, dropdownPaymentMethodItems)
        )
    }

    private fun onClickScheduledDate() {
        val selectedCalendar = Calendar.getInstance()
        val year = selectedCalendar.get(Calendar.YEAR)
        val month = selectedCalendar.get(Calendar.MONTH)
        val day = selectedCalendar.get(Calendar.DATE)
        val listener = DatePickerDialog.OnDateSetListener { datepicker, y, m, d ->
            var dayStr = d.toString()
            var monthStr = m.toString()
            var yearStr = y.toString()
            if (dayStr.length < 2) dayStr = "0$dayStr"
            if (monthStr.length < 2) monthStr = "0$monthStr"
            if (yearStr.length < 4) yearStr = "0$yearStr"
            this.binding.deliveryDateTextInputLayout.setText("$dayStr/$monthStr/$yearStr")
            approxDeliveryDatetimeSelected = Utils.parseStringToTimestamp("$dayStr/$monthStr/$yearStr")
        }
        val datePickerDialog = DatePickerDialog(requireContext(), listener, year, month, day)
        datePickerDialog.datePicker.minDate = Utils.addToDate(Date(), 3).time
        datePickerDialog.show()
    }

    private fun setRecyclerView(eggPricesData: EggPricesData) {

        val list = OrderUtils.getNewOrderGridModel(eggPricesData)

        val manager = CustomGridLayoutManager(this.context, 4)
        manager.setScrollEnabled(false)
        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (recyclerViewTitles.contains(position)) 4
                else if(recyclerViewTextInputLayouts.contains(position)) 2
                else 1
            }
        }

        this.binding.orderRecyclerView.layoutManager = manager
        this.binding.orderRecyclerView.adapter = HNGridTextAdapter(list)

    }

    private fun setClientDataFields() {
        this.binding.directionTextInputLayout.setText(clientData.direction)
        this.binding.directionTextInputLayout.isEnabled = false
        this.binding.phoneTextInputLayoutPhone1.setText(clientData.phone[0].entries.iterator().next().value.toString())
        this.binding.phoneTextInputLayoutPhone1.isEnabled = false
        this.binding.phoneTextInputLayoutPhone2.setText(clientData.phone[1].entries.iterator().next().value.toString())
        this.binding.phoneTextInputLayoutPhone2.isEnabled = false
    }

    override fun updateUI(state: BaseState) {
        try {
            with(state as NewOrderViewState) {
                with(binding) {
                    this.loadingComponent.isVisible = state.isLoading
                }
                when(this.step) {
                    3 -> {
                        this@NewOrderFragment.newOrderViewModel.navigateToMyOrders(
                            this@NewOrderFragment.view,
                            bundleOf(
                                "clientData" to clientData,
                                "fromNewOrder" to true
                            ),
                            this@NewOrderFragment.requireActivity()
                        )
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
        }

    }

    private fun setRecyclerViewEnable(isEnable: Boolean) {
        with(this.binding.orderRecyclerView.adapter as HNGridTextAdapter) {
            this.setEnabledDisabled(2, isEnable)
            this.setEnabledDisabled(5, isEnable)
            this.setEnabledDisabled(9, isEnable)
            this.setEnabledDisabled(12, isEnable)
            this.setEnabledDisabled(16, isEnable)
            this.setEnabledDisabled(19, isEnable)
            this.setEnabledDisabled(23, isEnable)
            this.setEnabledDisabled(26, isEnable)
            /*this.getItemWithPosition(2).isEnabled = isEnable
            this.getItemWithPosition(5).isEnabled = isEnable
            this.getItemWithPosition(9).isEnabled = isEnable
            this.getItemWithPosition(12).isEnabled = isEnable
            this.getItemWithPosition(16).isEnabled = isEnable
            this.getItemWithPosition(19).isEnabled = isEnable
            this.getItemWithPosition(23).isEnabled = isEnable
            this.getItemWithPosition(26).isEnabled = isEnable*/
            /*if (orderList.size == 8) {
                this.getItemWithPosition(2).response = (orderList[0] ?: "").toString()
                this.getItemWithPosition(5).response = (orderList[1] ?: "").toString()
                this.getItemWithPosition(9).response = (orderList[2] ?: "").toString()
                this.getItemWithPosition(12).response = (orderList[3] ?: "").toString()
                this.getItemWithPosition(16).response = (orderList[4] ?: "").toString()
                this.getItemWithPosition(19).response = (orderList[5] ?: "").toString()
                this.getItemWithPosition(23).response = (orderList[6] ?: "").toString()
                this.getItemWithPosition(26).response = (orderList[7] ?: "").toString()
            }
            notifyItemChanged(2)
            notifyItemChanged(5)
            notifyItemChanged(9)
            notifyItemChanged(12)
            notifyItemChanged(16)
            notifyItemChanged(19)
            notifyItemChanged(23)
            notifyItemChanged(26)*/
        }
    }

    private fun continueOrder(
        orderFieldMap: Map<String, Map<String, Number?>>,
        paymentMethodSelected: Int,
        totalPrice: Double
    ) {
        val orderData = OrderData(
            approxDeliveryDatetime = approxDeliveryDatetimeSelected,
            clientId = clientData.id,
            company = clientData.company,
            createdBy = "client_${clientData.id}",
            deliveryDatetime = null,
            deliveryDni = null,
            deliveryNote = null,
            deliveryPerson = null,
            lot = null,
            notes = null,
            order = orderFieldMap,
            orderDatetime = Timestamp(Date()),
            orderId = null,
            paid = false,
            paymentMethod = Constants.paymentMethod[paymentMethodSelected]!!.toLong(),
            status = 1,
            totalPrice = totalPrice,
            documentId = null
        )
        this.newOrderViewModel.addNewOrder(clientData.documentId, orderData)
    }

    companion object {
        private val TAG = LoginActivity::class.java.simpleName
    }

}