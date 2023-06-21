package com.example.hueverianietoclientes.ui.views.main.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.example.hueverianietoclientes.R
import com.example.hueverianietoclientes.base.BaseFragment
import com.example.hueverianietoclientes.base.BaseState
import com.example.hueverianietoclientes.data.network.ClientData
import com.example.hueverianietoclientes.databinding.FragmentHomeBinding
import com.example.hueverianietoclientes.domain.model.GridItemModel
import com.example.hueverianietoclientes.ui.components.hngridview.CustomGridLayoutManager
import com.example.hueverianietoclientes.ui.components.hngridview.HNGridViewAdapter
import com.example.hueverianietoclientes.ui.views.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var clientData: ClientData

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).configNav(false)
        clientData = (activity as MainActivity).clientData
        this.binding = FragmentHomeBinding
            .inflate(inflater, container, false)
        return this.binding.root
    }

    override fun configureUI() {
        lifecycleScope.launchWhenStarted {
            homeViewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }
    }

    override fun setObservers() {
        // It is not necessary, this is a static view
    }

    override fun setListeners() {
       this.binding.myProfileCard.setOnClickListener {
           this.homeViewModel.navigateToMyProfile(
               this.view,
               bundleOf("clientData" to clientData)
           )

       }
        this.binding.billingCard.setOnClickListener {
            this.homeViewModel.navigateToBilling(
                this.view,
                bundleOf(
                    "clientData" to clientData
                )
            )
        }
        this.binding.myOrdersCard.setOnClickListener {
            this.homeViewModel.navigateToMyOrders(
                this.view,
                bundleOf(
                    "clientData" to clientData,
                    "fromNewOrder" to false
                )
            )
        }
        this.binding.newOrderCard.setOnClickListener {
            this.homeViewModel.navigateToNewOrder(
                this.view,
                bundleOf("clientData" to clientData)
            )
        }
        this.binding.settingsCard.setOnClickListener {
            this.homeViewModel.navigateToSettings(
                this.view,
                bundleOf("clientData" to clientData)
            )
        }
    }

    override fun updateUI(state: BaseState) {
        // It is not necessary, this is a static view
    }

}
