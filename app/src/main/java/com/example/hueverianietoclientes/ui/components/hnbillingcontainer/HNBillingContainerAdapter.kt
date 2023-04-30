package com.example.hueverianietoclientes.ui.components.hnbillingcontainer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hueverianietoclientes.R
import com.example.hueverianietoclientes.domain.model.BillingContainerModel

class HNBillingContainerAdapter (private val billingContainerModelList: List<BillingContainerModel>) :
    RecyclerView.Adapter<HNBillingContainerViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HNBillingContainerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return HNBillingContainerViewHolder(layoutInflater.inflate(R.layout.component_billing_container, parent, false))
    }

    override fun getItemCount(): Int = billingContainerModelList.size

    override fun onBindViewHolder(holder: HNBillingContainerViewHolder, position: Int) {
        holder.render(billingContainerModelList[position])
    }

}
