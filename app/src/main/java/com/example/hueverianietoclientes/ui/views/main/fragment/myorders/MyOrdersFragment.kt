package com.example.hueverianietoclientes.ui.views.main.fragment.myorders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.hueverianietoclientes.base.BaseFragment
import com.example.hueverianietoclientes.base.BaseState
import com.example.hueverianietoclientes.data.network.ClientData
import com.example.hueverianietoclientes.databinding.FragmentMyOrdersBinding
import com.example.hueverianietoclientes.ui.views.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyOrdersFragment : BaseFragment() {

    private lateinit var binding: FragmentMyOrdersBinding
    private lateinit var clientData: ClientData
    private val myOrdersViewModel : MyOrdersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).configNav(true)
        clientData = (activity as MainActivity).clientData
        this.binding = FragmentMyOrdersBinding.inflate(
            inflater, container, false
        )
        return this.binding.root
    }

    override fun configureUI() {
        this.myOrdersViewModel.getOrdersData(clientData.documentId)
        //TODO("Not yet implemented")
    }

    override fun setObservers() {
        //TODO("Not yet implemented")
    }

    override fun setListeners() {
        // It is not necessary
    }

    override fun updateUI(state: BaseState) {
       // It is not necessary
    }

}