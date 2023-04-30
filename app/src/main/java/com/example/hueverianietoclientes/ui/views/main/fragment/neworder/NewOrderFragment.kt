package com.example.hueverianietoclientes.ui.views.main.fragment.neworder

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        getPaymentMethodDropdownValues()
        setRecyclerView()
        setClientDataFields()

        this.binding.deliveryDatePicker.setInputType(InputType.TYPE_DATETIME_VARIATION_NORMAL)
        approxDeliveryDatetimeSelected = Timestamp(Utils.addToDate(Date(), 3))
        this.binding.deliveryDatePicker.setInputText(
            Utils.parseDateToString(approxDeliveryDatetimeSelected.toDate())
        )
        this.binding.deliveryDatePicker.getDatePicker().setOnClickListener { onClickScheduledDate() }
        this.binding.confirmButton.setText("GUARDAR")
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
                        "Aviso",
                        "Una vez realizado el pedido, no se podrán modificar los datos directamente. Tendrá que llamarnos y solicitar el cambio ¿Desea continuar o prefiere revisar los datos?",
                        "Revisar",
                        "Continuar",
                        { alertDialog.cancel() },
                        {
                            alertDialog.cancel()
                            (activity as MainActivity).changeTopBarName("Resumen del pedido")
                            this.newOrderViewModel.changePage(2)
                            this.binding.scrollView.scrollTo(0, 0)
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
        }
    }

    override fun setListeners() {
        this.binding.confirmButton.setOnClickListener {
            it.hideSoftInput()
            if (this.newOrderViewModel.viewState.value.step == 1) {
                val paymentMethod =
                    if (this.binding.paymentMethodDropdown.getSelectedItem()
                        == requireContext().getString(R.string.in_cash)) {
                        R.string.in_cash
                    } else if (this.binding.paymentMethodDropdown.getSelectedItem()
                        == requireContext().getString(R.string.per_receipt)) {
                        R.string.per_receipt
                    } else if (this.binding.paymentMethodDropdown.getSelectedItem()
                        == requireContext().getString(R.string.transfer)){
                        R.string.transfer
                    } else {
                        null
                    }
                this.newOrderViewModel.checkOrder(
                    recyclerView = this.binding.orderRecyclerView,
                    clientDataId = clientData.id,
                    approxDeliveryDatetimeSelected = approxDeliveryDatetimeSelected,
                    paymentMethodSelected = paymentMethod
                )
            } else {
                if (orderData != null) {
                    this.newOrderViewModel.addNewOrder(
                        clientData, this.newOrderViewModel.orderData.value!!)
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
        }

        this.binding.modifyButton.setOnClickListener {
            it.hideSoftInput()
            this.newOrderViewModel.changePage(1)
            this.binding.scrollView.fullScroll(ScrollView.FOCUS_UP) // TODO: Estás probando esto y viendo cómo hacer la navegación para ir a allorders
            (activity as MainActivity).changeTopBarName("Nuevo pedido")
        }

        this.binding.paymentMethodDropdown.getAutoCompleteTextView().setOnFocusChangeListener { v, hasFocus ->
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
        this.binding.paymentMethodDropdown.setAdapter(dropdownPaymentMethodItems)
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
            this.binding.deliveryDatePicker.setInputText("$dayStr/$monthStr/$yearStr")
            approxDeliveryDatetimeSelected = Utils.parseStringToTimestamp("$dayStr/$monthStr/$yearStr")
        }
        val datePickerDialog = DatePickerDialog(requireContext(), listener, year, month, day)
        datePickerDialog.datePicker.minDate = Utils.addToDate(Date(), 3).time
        datePickerDialog.show()
    }

    private fun setRecyclerView() {

        val list = OrderUtils.getNewOrderGridModel()

        val manager = CustomGridLayoutManager(this.context, 3)
        manager.setScrollEnabled(false)
        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (listOf(0, 5, 10, 15).contains(position)) 3
                else if (listOf(2, 4, 7, 9, 12, 14, 17, 19).contains(position)) 2
                else 1
            }
        }

        this.binding.orderRecyclerView.layoutManager = manager
        this.binding.orderRecyclerView.adapter = HNGridTextAdapter(list)

    }

    private fun setClientDataFields() {
        this.binding.directionTextInputLayout.setInputText(clientData.direction)
        this.binding.directionTextInputLayout.isEnabled = false
        this.binding.phoneTextInputLayoutPhone1.setInputText(clientData.phone[0].entries.iterator().next().value.toString())
        this.binding.phoneTextInputLayoutPhone1.isEnabled = false
        this.binding.phoneTextInputLayoutPhone2.setInputText(clientData.phone[1].entries.iterator().next().value.toString())
        this.binding.phoneTextInputLayoutPhone2.isEnabled = false
    }

    override fun updateUI(state: BaseState) {
        try {
            with(state as NewOrderViewState) {
                with(binding) {
                    this.loadingComponent.isVisible = state.isLoading
                }
                if (this.step == 1) {
                    setRecyclerViewEnable(true)
                    binding.paymentMethodDropdown.isEnabled = true
                    binding.paymentMethodDropdown.getAutoCompleteTextView().isEnabled = true
                    binding.paymentMethodDropdown.getTextInputLayout().isEnabled = true
                    binding.deliveryDatePicker.isEnabled = true
                    binding.deliveryDatePicker.getDatePicker().isEnabled = true
                    binding.modifyButton.visibility = View.GONE
                    binding.confirmButton.setText("GUARDAR")
                } else if (this.step == 2) {
                    setRecyclerViewEnable(false)
                    binding.paymentMethodDropdown.isEnabled = false
                    binding.paymentMethodDropdown.getAutoCompleteTextView().isEnabled = false
                    binding.paymentMethodDropdown.getTextInputLayout().isEnabled = false
                    binding.deliveryDatePicker.isEnabled = false
                    binding.deliveryDatePicker.getDatePicker().isEnabled = false
                    binding.modifyButton.visibility = View.VISIBLE
                    binding.confirmButton.setText("CONFIRMAR")
                } else {
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
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
        }

    }

    private fun setRecyclerViewEnable(isEnable: Boolean) {
        with(this.binding.orderRecyclerView.adapter as HNGridTextAdapter) {
            this.getItemWithPosition(2).isEnabled = isEnable
            this.getItemWithPosition(4).isEnabled = isEnable
            this.getItemWithPosition(7).isEnabled = isEnable
            this.getItemWithPosition(9).isEnabled = isEnable
            this.getItemWithPosition(12).isEnabled = isEnable
            this.getItemWithPosition(14).isEnabled = isEnable
            this.getItemWithPosition(17).isEnabled = isEnable
            this.getItemWithPosition(19).isEnabled = isEnable
            if (orderList.size == 8) {
                this.getItemWithPosition(2).response = (orderList[0] ?: "").toString()
                this.getItemWithPosition(4).response = (orderList[1] ?: "").toString()
                this.getItemWithPosition(7).response = (orderList[2] ?: "").toString()
                this.getItemWithPosition(9).response = (orderList[3] ?: "").toString()
                this.getItemWithPosition(12).response = (orderList[4] ?: "").toString()
                this.getItemWithPosition(14).response = (orderList[5] ?: "").toString()
                this.getItemWithPosition(17).response = (orderList[6] ?: "").toString()
                this.getItemWithPosition(19).response = (orderList[7] ?: "").toString()
            }
            notifyItemChanged(2)
            notifyItemChanged(4)
            notifyItemChanged(7)
            notifyItemChanged(9)
            notifyItemChanged(12)
            notifyItemChanged(14)
            notifyItemChanged(17)
            notifyItemChanged(19)
        }
    }
    companion object {
        private val TAG = LoginActivity::class.java.simpleName
    }

}