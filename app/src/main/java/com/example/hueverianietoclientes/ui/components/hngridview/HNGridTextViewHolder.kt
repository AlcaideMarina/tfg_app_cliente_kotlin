package com.example.hueverianietoclientes.ui.components.hngridview

import android.text.InputType
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.hueverianietoclientes.databinding.ComponentGridItemBinding
import com.example.hueverianietoclientes.databinding.ComponentGridTextBinding
import com.example.hueverianietoclientes.domain.model.GridItemModel
import com.example.hueverianietoclientes.domain.model.GridTextItemModel

class HNGridTextViewHolder (view: View) :
    RecyclerView.ViewHolder(view) {

    private val binding : ComponentGridTextBinding = ComponentGridTextBinding.bind(view)

    fun render(gridTextItemModel: GridTextItemModel) {
        if (gridTextItemModel.isTextView) {
            this.binding.textViewGrid.visibility = View.VISIBLE
            this.binding.textInputLayoutGrid.visibility = View.GONE
            this.binding.textViewGrid.text = gridTextItemModel.text
        } else {
            this.binding.textViewGrid.visibility = View.GONE
            this.binding.textInputLayoutGrid.visibility = View.VISIBLE
            this.binding.textInputLayoutGrid.setInputType(InputType.TYPE_CLASS_NUMBER)
        }
    }
}