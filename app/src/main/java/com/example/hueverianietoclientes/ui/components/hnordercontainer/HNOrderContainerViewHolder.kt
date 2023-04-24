package com.example.hueverianietoclientes.ui.components.hnordercontainer

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.hueverianietoclientes.databinding.ComponentOrderContainerBinding
import com.example.hueverianietoclientes.domain.model.OrderContainerModel

class HNOrderContainerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding : ComponentOrderContainerBinding = ComponentOrderContainerBinding.bind(view)

    fun render(orderContainerModel: OrderContainerModel) {
        this.binding.dateText.text = orderContainerModel.orderDate.toString()
        this.binding.orderDeliveryText.text = orderContainerModel.orderId.toString()
        this.binding.companyText.text = orderContainerModel.company
        this.binding.priceText.text = orderContainerModel.price.toString()
        this.binding.deliveryInfoText.text = orderContainerModel.status.toString()
    }

}