package com.example.hueverianietoclientes.ui.components.hnordercontainer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hueverianietoclientes.R
import com.example.hueverianietoclientes.domain.model.OrderContainerModel

class HNOrderContainerAdapter(private val orderContainerModelList: List<OrderContainerModel>) :
    RecyclerView.Adapter<HNOrderContainerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HNOrderContainerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return HNOrderContainerViewHolder(
            layoutInflater.inflate(
                R.layout.component_order_container,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = orderContainerModelList.size

    override fun onBindViewHolder(holder: HNOrderContainerViewHolder, position: Int) {
        holder.render(orderContainerModelList[position])
    }

}
