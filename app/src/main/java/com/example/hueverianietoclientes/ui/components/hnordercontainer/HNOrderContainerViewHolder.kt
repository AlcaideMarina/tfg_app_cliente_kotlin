package com.example.hueverianietoclientes.ui.components.hnordercontainer

import android.icu.text.SimpleDateFormat
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.hueverianietoclientes.R
import com.example.hueverianietoclientes.databinding.ComponentOrderContainerBinding
import com.example.hueverianietoclientes.domain.model.OrderContainerModel
import com.example.hueverianietoclientes.utils.Constants
import com.example.hueverianietoclientes.utils.Utils

class HNOrderContainerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding: ComponentOrderContainerBinding = ComponentOrderContainerBinding.bind(view)

    fun render(orderContainerModel: OrderContainerModel) {

        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val dateStr = sdf.format(orderContainerModel.orderDate.toDate())

        //TODO: Esto no funciona
        val priceStr = if (orderContainerModel.price == (-1).toLong()) {
            "-"
        } else {
            orderContainerModel.price.toString()
        }
        // TODO: Transformar status
        var statusStr = Utils.getKey(Constants.orderStatus, orderContainerModel.status.toInt())
        if (statusStr == null) statusStr = R.string.unknown_status

        this.binding.dateText.text = dateStr
        this.binding.orderDeliveryText.text = "Pedido: " + orderContainerModel.orderId.toString()
        this.binding.orderSummaryText.text = orderContainerModel.orderSummary
        this.binding.priceText.text = "Precio: " + priceStr + " €"
        this.binding.deliveryInfoText.text = this.binding.root.context.getString(statusStr)
        if (orderContainerModel.deliveryDni == null) {
            this.binding.deliveryDniText.visibility = View.GONE
        } else {
            this.binding.deliveryDniText.text =
                "DNI de recogida: " + orderContainerModel.deliveryDni
        }

        this.binding.baseComponentOrderContainer.setOnClickListener(
            orderContainerModel.onClickListener
        )

    }

}
