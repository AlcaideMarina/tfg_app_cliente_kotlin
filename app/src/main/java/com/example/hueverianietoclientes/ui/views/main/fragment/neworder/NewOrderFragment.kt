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
import com.example.hueverianietoclientes.data.network.DBOrderFieldData
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
    private lateinit var approxDeliveryDatetimeSelected: Timestamp
    private var orderData: OrderData? = null
    private lateinit var eggPrices: EggPricesData

    private val recyclerViewTitles = listOf(0, 7, 14, 21)
    private val recyclerViewTextInputLayouts = listOf(2, 5, 9, 12, 16, 19, 23, 26)

    private var orderStep = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).configNav(true)
        this.binding = FragmentNewOrderBinding.inflate(
            inflater, container, false
        )
        val args: NewOrderFragmentArgs by navArgs()
        this.clientData = args.clientData

        return this.binding.root
    }

    override fun configureUI() {
        this.newOrderViewModel.getPrices()

        getPaymentMethodDropdownValues()
        setRecyclerView(EggPricesData())
        setClientDataFields()

        this.binding.deliveryDateTextInputLayout.inputType = InputType.TYPE_DATETIME_VARIATION_NORMAL
        approxDeliveryDatetimeSelected = Timestamp(Utils.addToDate(Date(), 3))
        this.binding.deliveryDateTextInputLayout.setText(
            Utils.parseDateToString(approxDeliveryDatetimeSelected.toDate())
        )
        this.binding.deliveryDateTextInputLayout.setOnClickListener { onClickScheduledDate() }
        this.binding.confirmButtonText.text = "CONFIRMAR"
        this.binding.modifyButtonText.text = "Modificar datos"
        this.alertDialog = HNModalDialog(requireContext())

        lifecycleScope.launchWhenStarted {
            newOrderViewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }
    }

    override fun setObservers() {
        this.newOrderViewModel.alertDialog.observe(this) { newOrderViewState ->
            when (newOrderViewState.popUpCode) {
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
                    clientData.documentId, this.newOrderViewModel.orderData.value!!
                )
            } else {
                Utils.setPopUp(
                    alertDialog,
                    requireContext(),
                    "Error",
                    "Se ha producido un error. Por favor, revise los datos y vuelva a intentarlo",
                    "De acuerdo",
                    null,
                    { alertDialog.cancel() },
                    null
                )
            }
        }
        this.newOrderViewModel.eggPrices.observe(this) {
            eggPrices = it
            setRecyclerView(it)
        }
    }

    override fun setListeners() {
        this.binding.confirmButton.setOnClickListener {

                it.hideSoftInput()
                val paymentMethodSelected =
                    when (this.binding.paymentMethodAutoCompleteTextView.text.toString()) {
                        requireContext().getString(R.string.in_cash) -> R.string.in_cash
                        requireContext().getString(R.string.per_receipt) -> R.string.per_receipt
                        requireContext().getString(R.string.transfer) -> R.string.transfer
                        else -> null
                    }

                val dbOrderFieldData = getDBOrderFieldData()

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

                    if (orderStep == 1) {
                        Utils.setPopUp(
                            alertDialog,
                            requireContext(),
                            "Aviso",
                            "Una vez realizado el pedido, no se podrán modificar los datos directamente. Tendrá que llamarnos y solicitar el campo. ¿Desea continuar o prefiere revisar los datos?",
                            "Revisar",
                            "Continuar",
                            { alertDialog.cancel() },
                            {
                                alertDialog.cancel()
                                this.newOrderViewModel.changePage(2)
                                this.binding.scrollView.fullScroll(ScrollView.FOCUS_UP)
                            }
                        )
                    } else if (orderStep == 2) {

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


        }

        this.binding.modifyButton.setOnClickListener {
            it.hideSoftInput()
            this.newOrderViewModel.changePage(1)
            this.binding.scrollView.fullScroll(ScrollView.FOCUS_UP)
        }

        this.binding.paymentMethodAutoCompleteTextView.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                v.hideSoftInput()
            }
        }
    }

    private fun getPaymentMethodDropdownValues() {
        val values = Constants.paymentMethod.entries.iterator()
        dropdownPaymentMethodItems = mutableListOf()
        for (v in values) {
            dropdownPaymentMethodItems.add(requireContext().getString(v.key))
        }
        this.binding.paymentMethodAutoCompleteTextView.setAdapter(
            ArrayAdapter(
                requireContext(), R.layout.component_dropdown_list_item, dropdownPaymentMethodItems
            )
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
            approxDeliveryDatetimeSelected =
                Utils.parseStringToTimestamp("$dayStr/$monthStr/$yearStr")
        }
        val datePickerDialog = DatePickerDialog(requireContext(), listener, year, month, day)
        datePickerDialog.datePicker.minDate = Utils.addToDate(Date(), 3).time
        datePickerDialog.show()
    }

    private fun setRecyclerView(eggPricesData: EggPricesData) {

        with(this.binding) {
            this.xlDozenTextInputLayout.setText("0")
            this.xlDozenPriceTextInputLayout.text = (eggPricesData.xlDozen ?: "-").toString() + " €/ud"
            this.xlDozenTextInputLayout.setTextColor(requireContext().getColor(R.color.black_color))
            this.xlBoxTextInputLayout.setText("0")
            this.xlBoxPriceTextInputLayout.text = (eggPricesData.xlBox ?: "-").toString() + " €/ud"
            this.xlBoxTextInputLayout.setTextColor(requireContext().getColor(R.color.black_color))

            this.lDozenTextInputLayout.setText("0")
            this.lDozenPriceTextInputLayout.text = (eggPricesData.lDozen ?: "-").toString() + " €/ud"
            this.lDozenTextInputLayout.setTextColor(requireContext().getColor(R.color.black_color))
            this.lBoxTextInputLayout.setText("0")
            this.lBoxPriceTextInputLayout.text = (eggPricesData.lBox ?: "-").toString() + " €/ud"
            this.lBoxTextInputLayout.setTextColor(requireContext().getColor(R.color.black_color))

            this.mDozenTextInputLayout.setText("0")
            this.mDozenPriceTextInputLayout.text = (eggPricesData.mDozen ?: "-").toString() + " €/ud"
            this.mDozenTextInputLayout.setTextColor(requireContext().getColor(R.color.black_color))
            this.mBoxTextInputLayout.setText("0")
            this.mBoxPriceTextInputLayout.text = (eggPricesData.mBox ?: "-").toString() + " €/ud"
            this.mBoxTextInputLayout.setTextColor(requireContext().getColor(R.color.black_color))

            this.sDozenTextInputLayout.setText("0")
            this.sDozenPriceTextInputLayout.text = (eggPricesData.sDozen ?: "-").toString() + " €/ud"
            this.sDozenTextInputLayout.setTextColor(requireContext().getColor(R.color.black_color))
            this.sBoxTextInputLayout.setText("0")
            this.sBoxPriceTextInputLayout.text = (eggPricesData.sBox ?: "-").toString() + " €/ud"
            this.sBoxTextInputLayout.setTextColor(requireContext().getColor(R.color.black_color))
        }

    }

    private fun setClientDataFields() {
        this.binding.directionTextInputLayout.setText(clientData.direction)
        this.binding.directionTextInputLayout.isEnabled = false
        this.binding.phoneTextInputLayoutPhone1.setText(
            clientData.phone[0].entries.iterator().next().value.toString()
        )
        this.binding.phoneTextInputLayoutPhone1.isEnabled = false
        this.binding.phoneTextInputLayoutPhone2.setText(
            clientData.phone[1].entries.iterator().next().value.toString()
        )
        this.binding.phoneTextInputLayoutPhone2.isEnabled = false
    }

    override fun updateUI(state: BaseState) {
        try {
            with(state as NewOrderViewState) {
                orderStep = this.step
                with(binding) {
                    this.loadingComponent.isVisible = state.isLoading
                }
                when (this.step) {
                    1 -> {
                        setFieldsEnable(true)
                        binding.confirmButtonText.text = "GUARDAR"
                        binding.confirmButton.visibility = View.VISIBLE
                        binding.modifyButton.visibility = View.GONE
                        binding.textsContainer.visibility = View.GONE
                        binding.divider.visibility = View.GONE
                    }
                    2 -> {
                        setFieldsEnable(false)
                        binding.confirmButtonText.text = "CONFIRMAR"
                        binding.confirmButton.visibility = View.VISIBLE
                        binding.modifyButton.visibility = View.VISIBLE
                        binding.textsContainer.visibility = View.VISIBLE
                        binding.divider.visibility = View.VISIBLE
                    }
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

    private fun setFieldsEnable(isEnable: Boolean) {
        with(this.binding) {
            val color = if (isEnable) R.color.black_color else R.color.black_light_color_80

            this.xlBoxTextInputLayout.isEnabled = isEnable
            this.xlBoxTextInputLayout.setTextColor(requireContext().getColor(color))
            this.xlDozenTextInputLayout.isEnabled = isEnable
            this.xlDozenTextInputLayout.setTextColor(requireContext().getColor(color))
            this.lBoxTextInputLayout.isEnabled = isEnable
            this.lBoxTextInputLayout.setTextColor(requireContext().getColor(color))
            this.lDozenTextInputLayout.isEnabled = isEnable
            this.lDozenTextInputLayout.setTextColor(requireContext().getColor(color))
            this.mBoxTextInputLayout.isEnabled = isEnable
            this.mBoxTextInputLayout.setTextColor(requireContext().getColor(color))
            this.mDozenTextInputLayout.isEnabled = isEnable
            this.mDozenTextInputLayout.setTextColor(requireContext().getColor(color))
            this.sBoxTextInputLayout.isEnabled = isEnable
            this.sBoxTextInputLayout.setTextColor(requireContext().getColor(color))
            this.sDozenTextInputLayout.isEnabled = isEnable
            this.sDozenTextInputLayout.setTextColor(requireContext().getColor(color))

            this.paymentMethodAutoCompleteTextView.isEnabled = isEnable
            this.paymentMethodTextInputLayout.isEnabled = isEnable
            this.paymentMethodAutoCompleteTextView.setTextColor(requireContext().getColor(color))
            this.deliveryDateTextInputLayout.isEnabled = isEnable
            this.deliveryDateTextInputLayout.setTextColor(requireContext().getColor(color))

        }
    }

    private fun getDBOrderFieldData() : DBOrderFieldData? {
        val xlBox =
            if(this.binding.xlBoxTextInputLayout.text.toString().toIntOrNull() == 0) null
            else this.binding.xlBoxTextInputLayout.text.toString().toIntOrNull()
        val xlDozen =
            if(this.binding.xlDozenTextInputLayout.text.toString().toIntOrNull() == 0) null
            else this.binding.xlDozenTextInputLayout.text.toString().toIntOrNull()
        val lBox =
            if(this.binding.lBoxTextInputLayout.text.toString().toIntOrNull() == 0) null
            else this.binding.lBoxTextInputLayout.text.toString().toIntOrNull()
        val lDozen =
            if(this.binding.lDozenTextInputLayout.text.toString().toIntOrNull() == 0) null
            else this.binding.lDozenTextInputLayout.text.toString().toIntOrNull()
        val mBox =
            if(this.binding.mBoxTextInputLayout.text.toString().toIntOrNull() == 0) null
            else this.binding.mBoxTextInputLayout.text.toString().toIntOrNull()
        val mDozen =
            if(this.binding.mDozenTextInputLayout.text.toString().toIntOrNull() == 0) null
            else this.binding.mDozenTextInputLayout.text.toString().toIntOrNull()
        val sBox =
            if(this.binding.sBoxTextInputLayout.text.toString().toIntOrNull() == 0) null
            else this.binding.sBoxTextInputLayout.text.toString().toIntOrNull()
        val sDozen =
            if(this.binding.sDozenTextInputLayout.text.toString().toIntOrNull() == 0) null
            else this.binding.sDozenTextInputLayout.text.toString().toIntOrNull()

        return if (xlBox == null && xlDozen == null && lBox == null && lDozen == null &&
            mBox == null && mDozen == null && sBox == null && sDozen == null) {
            null
        } else {
            DBOrderFieldData(
                eggPrices.xlBox,
                xlBox,
                eggPrices.xlDozen,
                xlDozen,
                eggPrices.lBox,
                lBox,
                eggPrices.lDozen,
                lDozen,
                eggPrices.mBox,
                mBox,
                eggPrices.mDozen,
                mDozen,
                eggPrices.sBox,
                sBox,
                eggPrices.sDozen,
                sDozen,
            )
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
