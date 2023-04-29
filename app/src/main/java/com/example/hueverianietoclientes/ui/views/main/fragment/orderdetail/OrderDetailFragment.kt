package com.example.hueverianietoclientes.ui.views.main.fragment.orderdetail

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.example.hueverianietoclientes.R
import com.example.hueverianietoclientes.base.BaseFragment
import com.example.hueverianietoclientes.base.BaseState
import com.example.hueverianietoclientes.data.network.ClientData
import com.example.hueverianietoclientes.data.network.OrderData
import com.example.hueverianietoclientes.databinding.FragmentOrderDetailBinding
import com.example.hueverianietoclientes.domain.model.GridTextItemModel
import com.example.hueverianietoclientes.ui.components.hngridview.CustomGridLayoutManager
import com.example.hueverianietoclientes.ui.components.hngridview.HNGridTextAdapter
import com.example.hueverianietoclientes.ui.views.main.MainActivity
import com.example.hueverianietoclientes.utils.Constants
import com.example.hueverianietoclientes.utils.OrderUtils
import com.example.hueverianietoclientes.utils.Utils


class OrderDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentOrderDetailBinding
    private lateinit var orderData: OrderData
    private lateinit var clientData: ClientData
    private val recyclerViewTitles = listOf(0, 7, 14, 21)
    private val recyclerViewSubtitles = listOf(1, 3, 4, 6, 8, 10, 11, 13, 15, 17, 18, 20, 22, 24, 25, 27)
    private val recyclerViewTextInputLayouts = listOf(2, 5, 9, 12, 16, 19, 23, 26)

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

        disableTextInputLayouts()
        setTexts()
        setRecyclerView()

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

    private fun disableTextInputLayouts() {

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
    }

    private fun setTexts() {

        val phone1 = clientData.phone[0].entries.iterator().next()
        val phone2 = clientData.phone[1].entries.iterator().next()

        val priceStr: String
        if (orderData.totalPrice == null) {
            priceStr = requireContext().getString(R.string.price_pending)
            this.binding.euroText.visibility = View.GONE
        } else {
            priceStr = orderData.totalPrice.toString()
        }

        val statusApproxDeliveryDatetimeList = listOf<Long>(0, 1, 2)
        val deliveryDatetimeField : String = if (statusApproxDeliveryDatetimeList.contains(orderData.status)) {
            requireContext().getString(Utils.getKey(
                Constants.orderStatus, orderData.status.toInt())!!) + " - " +
                    Utils.parseTimestampToString(orderData.approxDeliveryDatetime)
        } else if (orderData.status == (4).toLong()) {
            Utils.parseTimestampToString(orderData.deliveryDatetime) ?: ""
        } else if (orderData.status == (5).toLong()) {
            requireContext().getString(Utils.getKey(
                Constants.orderStatus, orderData)!!) + " - " +
                    Utils.parseTimestampToString(orderData.deliveryDatetime)
        } else {
            Utils.parseTimestampToString(orderData.deliveryDatetime)
                ?: Utils.parseTimestampToString(orderData.approxDeliveryDatetime)!!
        }

        with(this.binding) {
            orderIdTextView.text = "ID pedido: " + orderData.orderId.toString()
            companyTextInputLayout.setInputText(clientData.company)
            directionTextInputLayout.setInputText(clientData.direction)
            cifTextInputLayout.setInputText(clientData.cif)
            phoneTextInputLayoutPhone1.setInputText(phone1.value.toString())
            phoneTextInputLayoutPhone2.setInputText(phone2.value.toString())
            totalPriceTextInputLayout.setInputText(priceStr)
            orderDateTextInputLayout.setInputText(
                Utils.parseTimestampToString(orderData.orderDatetime) ?: "")
            deliveryDateTextInputLayout.setInputText(deliveryDatetimeField)
            deliveryPersonTextInputLayout.setInputText(orderData.deliveryPerson ?: "")
            deliveryNoteTextInputLayout.setInputText(orderData.deliveryNote?.toString() ?: "")
            deliveryDniTextInputLayout.setInputText(orderData.deliveryDni ?: "")
        }

    }

    private fun setRecyclerView() {

        val list = OrderUtils.getOrderDataGridModel(orderData)

        val manager = CustomGridLayoutManager(this.context, 4)
        manager.setScrollEnabled(false)
        manager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (recyclerViewTitles.contains(position)) 4
                else if(recyclerViewTextInputLayouts.contains(position)) 2
                else 1
            }
        }

        this.binding.orderRecyclerView.layoutManager = manager
        this.binding.orderRecyclerView.adapter = HNGridTextAdapter(list)

    }
}