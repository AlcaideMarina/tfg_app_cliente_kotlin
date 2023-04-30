package com.example.hueverianietoclientes.ui.views.main.fragment.billing

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hueverianietoclientes.base.BaseFragment
import com.example.hueverianietoclientes.base.BaseState
import com.example.hueverianietoclientes.data.network.ClientData
import com.example.hueverianietoclientes.databinding.FragmentBillingBinding
import com.example.hueverianietoclientes.domain.model.BillingContainerItemModel
import com.example.hueverianietoclientes.ui.components.hnbillingcontainer.HNBillingContainerAdapter
import com.example.hueverianietoclientes.ui.views.main.MainActivity
import com.example.hueverianietoclientes.ui.views.main.fragment.myorders.MyOrdersFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BillingFragment : BaseFragment() {

    private lateinit var binding : FragmentBillingBinding
    private lateinit var clientData: ClientData
    private val billingViewModel: BillingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).configNav(true)
        this.binding = FragmentBillingBinding.inflate(
            inflater, container, false
        )

        val args : BillingFragmentArgs by navArgs()
        this.clientData = args.clientData

        return this.binding.root
    }

    override fun configureUI() {
        this.billingViewModel.getBillingData(clientData.documentId)
        lifecycleScope.launchWhenStarted {
            billingViewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }
    }

    override fun setObservers() {
        this.billingViewModel.billingContainerList.observe(this) { billingContainerList ->
            if (billingContainerList == null) {
                // TODO: Error
            } else {
                val billingList = mutableListOf<BillingContainerItemModel>()
                for (item in billingContainerList) {
                    if (item != null) {
                        var billingContainerItemModel = BillingContainerItemModel(
                            item
                        ) {
                            // TODO: Navegación
                        }
                        billingList.add(billingContainerItemModel)
                    }
                }
                if (billingList.isEmpty()) {
                    // TODO: Vacío
                } else {
                    this.binding.billingRecyclerView.layoutManager = LinearLayoutManager(context)
                    this.binding.billingRecyclerView.adapter = HNBillingContainerAdapter(billingList)
                    this.binding.billingRecyclerView.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun setListeners() {
        //TODO("Not yet implemented")
    }

    override fun updateUI(state: BaseState) {
        try {
            with(state as BillingViewState) {
                with(binding) {
                    this.loadingComponent.isVisible = state.isLoading
                    if (state.error) {
                        //setPopUp(errorMap(Constants.loginBadFormattedEmailError))
                    } else if (state.isEmpty) {
                        //setPopUp(errorMap(Constants.loginBadFormattedEmailError))
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString(), e)
        }
        //TODO("Not yet implemented")
    }

    companion object {
        private val TAG = BillingFragment::class.java.simpleName
    }

}