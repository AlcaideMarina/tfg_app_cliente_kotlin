package com.example.hueverianietoclientes.ui.views.main.fragment.orderdetail

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).configNav(true)
        this.binding = FragmentOrderDetailBinding.inflate(
            inflater, container, false
        )

        val args: OrderDetailFragmentArgs by navArgs()
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
        this.binding.deliveryPersonTextInputLayout.isEnabled = false
        this.binding.deliveryNoteTextInputLayout.isEnabled = false
        this.binding.lotTextInputLayout.isEnabled = false
        this.binding.deliveryDniTextInputLayout.isEnabled = false
        this.binding.paidCheckedTextView.isEnabled = false
        this.binding.paidCheckedTextView.isFocusableInTouchMode = false
        this.binding.paymentMethodTextInputLayout.isEnabled = false
        this.binding.paymentMethodAutoCompleteTextView.isEnabled = false
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
        val deliveryDatetimeField: String =
            if (statusApproxDeliveryDatetimeList.contains(orderData.status)) {
                requireContext().getString(
                    Utils.getKey(
                        Constants.orderStatus, orderData.status.toInt()
                    )!!
                ) + " - " +
                        Utils.parseTimestampToString(orderData.approxDeliveryDatetime)
            } else if (orderData.status == (4).toLong()) {
                Utils.parseTimestampToString(orderData.deliveryDatetime) ?: ""
            } else if (orderData.status == (5).toLong()) {
                requireContext().getString(
                    Utils.getKey(
                        Constants.orderStatus, orderData.status.toInt()
                    )!!
                ) + " - " +
                        Utils.parseTimestampToString(orderData.deliveryDatetime)
            } else {
                Utils.parseTimestampToString(orderData.deliveryDatetime)
                    ?: Utils.parseTimestampToString(orderData.approxDeliveryDatetime)!!
            }

        with(this.binding) {
            orderIdTextView.text = orderData.orderId.toString()
            companyAutoCompleteTextView.setText(clientData.company)
            directionTextInputLayout.setText(clientData.direction)
            cifTextInputLayout.setText(clientData.cif)
            phoneTextInputLayoutPhone1.setText(phone1.value.toString())
            phoneTextInputLayoutPhone2.setText(phone2.value.toString())
            totalPriceTextInputLayout.setText(priceStr)
            orderDateTextInputLayout.setText(
                Utils.parseTimestampToString(orderData.orderDatetime) ?: ""
            )
            deliveryDateTextInputLayout.setText(deliveryDatetimeField)
            deliveryNoteTextInputLayout.setText(orderData.deliveryNote?.toString() ?: "")
            deliveryDniTextInputLayout.setText(orderData.deliveryDni ?: "")
            lotTextInputLayout.setText(orderData.lot)
            paidCheckedTextView.isChecked = orderData.paid
            paymentMethodAutoCompleteTextView.setText(
                Utils.getKey(Constants.paymentMethod, orderData.paymentMethod.toInt())!!
            )
        }

    }

    private fun setRecyclerView() {

        val bdOrderModel = OrderUtils.orderDataToBDOrderModel(orderData)

        with(this.binding) {
            this.xlDozenTextInputLayout.setText(bdOrderModel.xlDozenQuantity.toString())
            this.xlDozenPriceTextInputLayout.text = (bdOrderModel.xlDozenPrice ?: "-").toString() + " €/ud"
            this.xlDozenTextInputLayout.isEnabled = false
            this.xlBoxTextInputLayout.setText(bdOrderModel.xlBoxQuantity.toString())
            this.xlBoxPriceTextInputLayout.text = (bdOrderModel.xlBoxPrice ?: "-").toString() + " €/ud"
            this.xlBoxTextInputLayout.isEnabled = false

            this.lDozenTextInputLayout.setText(bdOrderModel.lDozenQuantity.toString())
            this.lDozenPriceTextInputLayout.text = (bdOrderModel.lDozenPrice ?: "-").toString() + " €/ud"
            this.lDozenTextInputLayout.isEnabled = false
            this.lBoxTextInputLayout.setText(bdOrderModel.lBoxQuantity.toString())
            this.lBoxPriceTextInputLayout.text = (bdOrderModel.lBoxPrice ?: "-").toString() + " €/ud"
            this.lBoxTextInputLayout.isEnabled = false

            this.mDozenTextInputLayout.setText(bdOrderModel.mDozenQuantity.toString())
            this.mDozenPriceTextInputLayout.text = (bdOrderModel.mDozenPrice ?: "-").toString() + " €/ud"
            this.mDozenTextInputLayout.isEnabled = false
            this.mBoxTextInputLayout.setText(bdOrderModel.mBoxQuantity.toString())
            this.mBoxPriceTextInputLayout.text = (bdOrderModel.mBoxPrice ?: "-").toString() + " €/ud"
            this.mBoxTextInputLayout.isEnabled = false

            this.sDozenTextInputLayout.setText(bdOrderModel.sDozenQuantity.toString())
            this.sDozenPriceTextInputLayout.text = (bdOrderModel.sDozenPrice ?: "-").toString() + " €/ud"
            this.sDozenTextInputLayout.isEnabled = false
            this.sBoxTextInputLayout.setText(bdOrderModel.sBoxQuantity.toString())
            this.sBoxPriceTextInputLayout.text = (bdOrderModel.sBoxPrice ?: "-").toString() + " €/ud"
            this.sBoxTextInputLayout.isEnabled = false
        }

    }

}
