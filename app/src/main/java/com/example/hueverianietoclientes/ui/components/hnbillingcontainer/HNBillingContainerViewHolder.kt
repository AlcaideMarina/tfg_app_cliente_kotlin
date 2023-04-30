package com.example.hueverianietoclientes.ui.components.hnbillingcontainer

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.hueverianietoclientes.databinding.ComponentBillingContainerBinding
import com.example.hueverianietoclientes.domain.model.BillingContainerModel
import com.example.hueverianietoclientes.utils.Utils

class HNBillingContainerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding : ComponentBillingContainerBinding = ComponentBillingContainerBinding.bind(view)

    fun render(billingContainerModel: BillingContainerModel) {
        this.binding.billingDateText.text = Utils.parseDateToString(
            billingContainerModel.initDate.toDate(), "MM/yyyy")
        this.binding.baseComponentBillingContainer.setOnClickListener(
            billingContainerModel.onClickListener
        )
    }

}
