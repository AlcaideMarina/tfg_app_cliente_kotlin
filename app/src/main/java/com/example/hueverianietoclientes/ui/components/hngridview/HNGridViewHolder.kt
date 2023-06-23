package com.example.hueverianietoclientes.ui.components.hngridview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.hueverianietoclientes.databinding.ComponentGridItemBinding
import com.example.hueverianietoclientes.domain.model.GridItemModel

class HNGridViewHolder(view: View, width: Int) :
    RecyclerView.ViewHolder(view) {

    private val binding: ComponentGridItemBinding = ComponentGridItemBinding.bind(view)
    private val w: Int = width

    fun render(gridItemModel: GridItemModel) {
        this.binding.tvCard.text = gridItemModel.label
        this.binding.ivCard.setImageDrawable(gridItemModel.icon)
        this.binding.card.setOnClickListener(gridItemModel.onClickListener)
        this.binding.cardFrameLayout.layoutParams.width = w
    }

}
