package com.example.hueverianietoclientes.ui.components.hngridview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hueverianietoclientes.R
import com.example.hueverianietoclientes.domain.model.GridItemModel
import com.example.hueverianietoclientes.domain.model.GridTextItemModel
import com.example.hueverianietoclientes.ui.components.hngridview.HNGridViewHolder

class HNGridTextAdapter(
    private val gridItemModelList: List<GridTextItemModel>
) : RecyclerView.Adapter<HNGridTextViewHolder>() {

    override fun getItemCount(): Int = gridItemModelList.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HNGridTextViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return HNGridTextViewHolder(layoutInflater.inflate(R.layout.component_grid_text, parent, false))
    }

    override fun onBindViewHolder(holder: HNGridTextViewHolder, position: Int) {
        holder.render(gridItemModelList[position])
    }
}