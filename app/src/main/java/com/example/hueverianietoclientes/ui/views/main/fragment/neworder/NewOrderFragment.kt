package com.example.hueverianietoclientes.ui.views.main.fragment.neworder

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.core.view.iterator
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.hueverianietoclientes.base.BaseFragment
import com.example.hueverianietoclientes.base.BaseState
import com.example.hueverianietoclientes.data.network.ClientData
import com.example.hueverianietoclientes.data.network.DBOrderFieldData
import com.example.hueverianietoclientes.data.network.OrderData
import com.example.hueverianietoclientes.databinding.FragmentNewOrderBinding
import com.example.hueverianietoclientes.domain.model.ModalDialogModel
import com.example.hueverianietoclientes.ui.components.HNModalDialog
import com.example.hueverianietoclientes.ui.components.hngridview.CustomGridLayoutManager
import com.example.hueverianietoclientes.ui.components.hngridview.HNGridTextAdapter
import com.example.hueverianietoclientes.ui.views.login.LoginActivity
import com.example.hueverianietoclientes.ui.views.login.LoginViewState
import com.example.hueverianietoclientes.ui.views.main.MainActivity
import com.example.hueverianietoclientes.utils.Constants
import com.example.hueverianietoclientes.utils.OrderUtils
import com.example.hueverianietoclientes.utils.Utils
import com.google.firebase.Timestamp
import com.google.firestore.v1.StructuredQuery.Order
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.observeOn
import java.util.*

@AndroidEntryPoint
class NewOrderFragment : BaseFragment() {

    private lateinit var binding: FragmentNewOrderBinding
    private lateinit var clientData: ClientData
    private lateinit var alertDialog: HNModalDialog
    private var dropdownPaymentMethodItems: MutableList<String> = mutableListOf()
    private val newOrderViewModel: NewOrderViewModel by viewModels()
    private lateinit var approxDeliveryDatetimeSelected : Timestamp

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
        //getOrderStructure()
        setClientDataFields()

        this.binding.deliveryDatePicker.setInputType(InputType.TYPE_DATETIME_VARIATION_NORMAL)
        approxDeliveryDatetimeSelected = Timestamp(Utils.addDaysToDate(Date(), 3))
        this.binding.deliveryDatePicker.setInputText(
            Utils.parseDateToString(approxDeliveryDatetimeSelected.toDate())
        )
        this.binding.deliveryDatePicker.getDatePicker().setOnClickListener { onClickScheduledDate() }
        this.binding.confirmButton.setText("CONFIRMAR")
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
                0 -> setPopUp(
                        "Formulario incorrecto",
                        "Por favor, compruebe los datos. Hay errores o faltan campos por rellenar.",
                        "De acuerdo",
                        null,
                        { alertDialog.cancel() },
                        null
                    )
                1 -> setPopUp(
                        "Formulario incorrecto",
                        "Por favor, compruebe los datos de los productos. Se han introducido caracteres no válidos (sólo son válidos numeros enteros) o no se ha pedido ningún producto.",
                        "De acuerdo",
                        null,
                        { alertDialog.cancel() },
                        null
                    )
                2 -> setPopUp(
                        "Aviso",
                        "Una vez realizado el pedido, no se podrán modificar los datos directamente. Tendrá que llamarnos y solicitar el cambio ¿Desea continuar o prefiere revisar los datos?",
                        "De acuerdo",
                        null,
                        { alertDialog.cancel() },
                        { prepareOrderDataFromForm() }
                    )
                3 -> setPopUp(
                        "Se ha producido un error",
                        "Sentimos comunicarle que se ha producido un error inesperado durante el pedido. Por favor, inténtelo más tarde o póngase en contacto con nosotros.",
                        "Revisar",
                        "Continuar",
                        { alertDialog.cancel() },
                        null
                    )
            }

        }

    }

    override fun setListeners() {
        this.binding.confirmButton.setOnClickListener {
            this.newOrderViewModel.checkOrder(
                recyclerView = this.binding.orderRecyclerView,
                clientDataId = clientData.id,
                approxDeliveryDatetimeSelected = approxDeliveryDatetimeSelected
            )

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
        // TODO: Bloquear hasta 3 días después del pedido
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
            this.binding.deliveryDatePicker.setInputText("$dayStr-$monthStr-$yearStr")
            approxDeliveryDatetimeSelected = Utils.parseStringToTimestamp("$dayStr-$monthStr-$yearStr")
        }
        DatePickerDialog(requireContext(), listener, year, month, day).show()
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
        this.binding.phoneTextInputLayoutPhone1.setInputText(clientData.phone[0].entries.iterator().next().value.toString())
        this.binding.phoneTextInputLayoutPhone2.setInputText(clientData.phone[1].entries.iterator().next().value.toString())
    }

    private fun setPopUp(title: String, message: String, leftButton: String, rightButton: String?,
        leftButtonListener: View.OnClickListener, rightButtonListener: View.OnClickListener?) {
        alertDialog.show(
            requireContext(),
            ModalDialogModel(
                title,
                message,
                leftButton,
                rightButton,
                leftButtonListener,
                rightButtonListener,
                true
            )
        )
    }

    fun prepareOrderDataFromForm() {

    }

    override fun updateUI(state: BaseState) {
        try {
            with(state as NewOrderViewState) {
                with(binding) {
                    this.loadingComponent.isVisible = state.isLoading
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