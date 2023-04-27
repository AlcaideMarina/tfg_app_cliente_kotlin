package com.example.hueverianietoclientes.ui.components.hngridview

import android.content.ClipData.Item
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hueverianietoclientes.R
import com.example.hueverianietoclientes.domain.model.GridTextItemModel

class HNGridTextAdapter(
    private val gridItemModelList: List<GridTextItemModel>
) : RecyclerView.Adapter<HNGridTextViewHolder>() {

    val list = gridItemModelList

    override fun getItemCount(): Int = gridItemModelList.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HNGridTextViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return HNGridTextViewHolder(layoutInflater.inflate(R.layout.component_grid_text, parent, false))
    }

    override fun onBindViewHolder(holder: HNGridTextViewHolder, position: Int) {
        holder.render(gridItemModelList[position])
    }

    fun getItemWithPosition(position: Int) : GridTextItemModel {
        return this.gridItemModelList[position]
    }
}