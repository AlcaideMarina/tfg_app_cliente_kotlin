package com.example.hueverianietoclientes.ui.components.hngridview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hueverianietoclientes.R
import com.example.hueverianietoclientes.domain.model.GridItemModel


class HNGridViewAdapter(private val gridItemModelList: List<GridItemModel>) :
    RecyclerView.Adapter<HNGridViewHolder>() {

    override fun getItemCount(): Int = gridItemModelList.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HNGridViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        // let's start by considering number of columns
        var width = parent.measuredWidth / 2
        // then, let's remove recyclerview padding
        width -= parent.context.resources.getDimensionPixelSize(R.dimen.size32)
        return HNGridViewHolder(
            layoutInflater.inflate(R.layout.component_grid_item, parent, false),
            width
        )
    }

    override fun onBindViewHolder(holder: HNGridViewHolder, position: Int) {
        holder.render(gridItemModelList[position])
    }

}
