package com.example.hueverianietoclientes.ui.views.main.fragment.billingdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.hueverianietoclientes.base.BaseFragment
import com.example.hueverianietoclientes.base.BaseState
import com.example.hueverianietoclientes.databinding.FragmentBillingDetailBinding
import com.example.hueverianietoclientes.domain.model.BillingModel
import com.example.hueverianietoclientes.ui.views.main.MainActivity
import kotlin.properties.Delegates

class BillingDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentBillingDetailBinding
    private lateinit var billingModel: BillingModel
    private var isCurrentMonth by Delegates.notNull<Boolean>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).configNav(true)
        this.binding = FragmentBillingDetailBinding.inflate(
            inflater, container, false
        )
        val args: BillingDetailFragmentArgs by navArgs()
        this.billingModel = args.billingModel
        this.isCurrentMonth = args.isCurrentMonth
        return this.binding.root
    }

    override fun configureUI() {
        with(this.binding) {
            this.currentMonthWarning.visibility = if (isCurrentMonth) View.VISIBLE else View.GONE
            cashTextView.text = billingModel.paymentByCash.toString() + " €"
            receiptTextView.text = billingModel.paymentByReceipt.toString() + " €"
            transferTextView.text = billingModel.paymentByTransfer.toString() + " €"
            paidTextView.text = billingModel.paid.toString() + " €"
            toBePaidTextView.text = billingModel.toBePaid.toString() + " €"
            totalPriceTextView.text = billingModel.totalPrice.toString() + " €"
        }
    }

    override fun setObservers() {
        // Not necessary
    }

    override fun setListeners() {
        // Not necessary
    }

    override fun updateUI(state: BaseState) {
        // Not necessary
    }

}
