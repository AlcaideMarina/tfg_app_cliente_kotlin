package com.example.hueverianietoclientes.ui.views.main.fragment.orderdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.hueverianietoclientes.base.BaseFragment
import com.example.hueverianietoclientes.base.BaseState
import com.example.hueverianietoclientes.data.network.OrderData
import com.example.hueverianietoclientes.databinding.FragmentOrderDetailBinding
import com.example.hueverianietoclientes.ui.views.main.MainActivity

class OrderDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentOrderDetailBinding
    private lateinit var orderData: OrderData

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

        return this.binding.root
    }

    override fun configureUI() {
        //TODO("Not yet implemented")
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