package com.example.hueverianietoclientes.ui.views.main.fragment.orderdetail

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.hueverianietoclientes.base.BaseFragment
import com.example.hueverianietoclientes.base.BaseState
import com.example.hueverianietoclientes.data.network.ClientData
import com.example.hueverianietoclientes.data.network.OrderData
import com.example.hueverianietoclientes.databinding.FragmentOrderDetailBinding
import com.example.hueverianietoclientes.ui.views.main.MainActivity
import java.util.StringTokenizer

class OrderDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentOrderDetailBinding
    private lateinit var orderData: OrderData
    private lateinit var clientData: ClientData

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).configNav(true)
        this.binding = FragmentOrderDetailBinding.inflate(
            inflater, container, false
        )

        val args : OrderDetailFragmentArgs by navArgs()
        this.orderData = args.orderData
        this.clientData = args.clientData

        return this.binding.root
    }

    override fun configureUI() {

        this.binding.companyTextInputLayout.isEnabled = false
        this.binding.directionTextInputLayout.isEnabled = false
        this.binding.cifTextInputLayout.isEnabled = false
        this.binding.phoneTextInputLayoutPhone1.isEnabled = false
        this.binding.phoneTextInputLayoutPhone2.isEnabled = false
        this.binding.totalPriceTextInputLayout.isEnabled = false
        this.binding.orderDateTextInputLayout.isEnabled = false
        this.binding.deliveryDateTextInputLayout.isEnabled = false
        this.binding.deliveryPersonTextInputLayout.isEnabled = false
        this.binding.deliveryNoteTextInputLayout.isEnabled = false
        this.binding.deliveryDniTextInputLayout.isEnabled = false

        this.binding.orderIdTextView.text = "ID pedido: " + this.orderData.orderId.toString()

        val phone1 = clientData.phone[0].entries.iterator().next()
        val phone2 = clientData.phone[1].entries.iterator().next()

        val priceStr: String
        if (orderData.totalPrice == (-1).toLong()) {
            priceStr = "Pendiente de confirmación"
            this.binding.euroText.visibility = View.GONE
        } else {
            priceStr = orderData.totalPrice.toString()
        }
        val deliveryNote = if(orderData.deliveryNote == null) {
            ""
        } else {
            this.orderData.deliveryNote.toString()
        }

        val sdf = SimpleDateFormat("MM/dd/yyyy")
        val deliveryDateStr = if(orderData.deliveryDatetime == null) {
            ""
        } else {
            sdf.format(orderData.orderDatetime.toDate())
        }

        this.binding.companyTextInputLayout.setInputText(clientData.company)
        this.binding.directionTextInputLayout.setInputText(clientData.direction)
        this.binding.cifTextInputLayout.setInputText(clientData.cif)
        this.binding.phoneTextInputLayoutPhone1.setInputText(phone1.value.toString())
        this.binding.phoneTextInputLayoutPhone2.setInputText(phone2.value.toString())
        this.binding.totalPriceTextInputLayout.setInputText(priceStr)
        this.binding.orderDateTextInputLayout.setInputText(sdf.format(orderData.orderDatetime.toDate()))
        this.binding.deliveryDateTextInputLayout.setInputText(deliveryDateStr)
        this.binding.deliveryPersonTextInputLayout.setInputText(this.orderData.deliveryPerson ?: "")
        this.binding.deliveryNoteTextInputLayout.setInputText(deliveryNote)
        this.binding.deliveryDniTextInputLayout.setInputText(orderData.deliveryDni ?: "")

    }

    override fun setObservers() {
        // It is not necessary, this is a static fragment
    }

    override fun setListeners() {
        // It is not necessary, this is a static fragment
    }

    override fun updateUI(state: BaseState) {
        // It is not necessary, this is a static fragment
    }
}