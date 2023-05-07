package com.example.hueverianietoclientes.ui.views.main.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import com.example.hueverianietoclientes.R
import com.example.hueverianietoclientes.base.BaseFragment
import com.example.hueverianietoclientes.base.BaseState
import com.example.hueverianietoclientes.data.network.ClientData
import com.example.hueverianietoclientes.databinding.FragmentHomeBinding
import com.example.hueverianietoclientes.domain.model.GridItemModel
import com.example.hueverianietoclientes.domain.usecase.HomeUseCase
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
        setMenuRecyclerView()

        lifecycleScope.launchWhenStarted {
            homeViewModel.viewState.collect {viewState ->
                updateUI(viewState)
            }
        }
    }

    override fun setObservers() {
        // It is not necessary, this is a static view
    }

    override fun setListeners() {
        //TODO("Not yet implemented")
    }

    override fun updateUI(state: BaseState) {
        // It is not necessary, this is a static view
    }

    private fun setMenuRecyclerView() {
        val menuOptions: List<GridItemModel> = listOf(
            GridItemModel(
                "Mi perfil",
                AppCompatResources.getDrawable(
                    requireContext(), R.drawable.ic_launcher_foreground)!!
            ) {
                this.homeViewModel.navigateToMyProfile(
                    this.view,
                    bundleOf("clientData" to clientData))
              },
            GridItemModel(
                "Facturación",
                AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.ic_launcher_foreground)!!
            ) {
                this.homeViewModel.navigateToBilling(
                    this.view,
                    bundleOf(
                        "clientData" to clientData
                    )
                )
              },
            GridItemModel(
                "Mis pedidos",
                AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.ic_launcher_foreground)!!
            ) {
                this.homeViewModel.navigateToMyOrders(
                    this.view,
                    bundleOf(
                        "clientData" to clientData,
                        "fromNewOrder" to false
                    )
                )
              },
            GridItemModel(
                "Nuevo pedido",
                AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.ic_launcher_foreground)!!
            ) {
                this.homeViewModel.navigateToNewOrder(
                    this.view,
                    bundleOf("clientData" to clientData)
                )
              },
            GridItemModel(
                "Ajustes",
                AppCompatResources.getDrawable(requireContext(),
                    R.drawable.ic_launcher_foreground)!!
            ) {
                this.homeViewModel.navigateToSettings(
                    this.view,
                    bundleOf("clientData" to clientData)
                )
            }
        )
        val manager = CustomGridLayoutManager(this.context, 2)
        manager.setScrollEnabled(false)
        manager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (menuOptions.size % 2 != 0) {
                    if (position == menuOptions.size - 1) 2 else 1
                } else {
                    1
                }
            }
        }

        with(this.binding.lstItems) {
            layoutManager = manager
            adapter = HNGridViewAdapter(menuOptions)
        }
    }

}
