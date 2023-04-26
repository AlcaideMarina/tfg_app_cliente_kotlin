package com.example.hueverianietoclientes.ui.views.main.fragment.neworder

import android.app.DatePickerDialog
import android.icu.text.CaseMap.Title
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.hueverianietoclientes.base.BaseFragment
import com.example.hueverianietoclientes.base.BaseState
import com.example.hueverianietoclientes.data.network.ClientData
import com.example.hueverianietoclientes.databinding.FragmentNewOrderBinding
import com.example.hueverianietoclientes.domain.model.ModalDialogModel
import com.example.hueverianietoclientes.ui.components.HNModalDialog
import com.example.hueverianietoclientes.ui.components.hngridview.CustomGridLayoutManager
import com.example.hueverianietoclientes.ui.components.hngridview.HNGridTextAdapter
import com.example.hueverianietoclientes.ui.views.main.MainActivity
import com.example.hueverianietoclientes.utils.Constants
import com.example.hueverianietoclientes.utils.OrderUtils
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class NewOrderFragment : BaseFragment() {

    private lateinit var binding: FragmentNewOrderBinding
    private lateinit var clientData: ClientData
    private lateinit var alertDialog: HNModalDialog
    private var dropdownPaymentMethodItems: MutableList<String> = mutableListOf()
    private val newOrderViewModel: NewOrderViewModel by viewModels()

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
        this.binding.deliveryDatePicker.setInputType(InputType.TYPE_DATETIME_VARIATION_NORMAL)
        this.binding.deliveryDatePicker.getDatePicker().setOnClickListener { onClickScheduledDate() }
        this.binding.confirmButton.setText("CONFIRMAR")
    }

    override fun setObservers() {
        this.newOrderViewModel.alertDialog.observe(this) { newOrderViewState ->
            if (newOrderViewState.error) {
                setPopUp(
                    "Formulario incorrecto",
                    "Por favor, compruebe los datos. Hay errores o faltan campos por rellenar.",
                    "De acuerdo",
                    null,
                    { alertDialog.cancel() },
                    null
                )
            } else {
                setPopUp(
                    "Aviso",
                    "Una vez realizado el pedido, no se podrán modificar los datos directamente. Tendrá que llamarnos y solicitar el cambio ¿Desea continuar o prefiere revisar los datos?",
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
            this.newOrderViewModel.checkOrder()
        }
    }

    override fun updateUI(state: BaseState) {
        //TODO("Not yet implemented")
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
            this.binding.deliveryDatePicker.setInputText("$y-$m-$d")
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

}