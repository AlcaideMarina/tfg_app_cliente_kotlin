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
            if (gridTextItemModel.isTextLeft) {
                this.binding.leftTextViewGrid.visibility = View.VISIBLE
                this.binding.rightTextViewGrid.visibility = View.GONE
                this.binding.leftTextViewGrid.text = gridTextItemModel.text
            } else {
                this.binding.leftTextViewGrid.visibility = View.GONE
                this.binding.rightTextViewGrid.visibility = View.VISIBLE
                this.binding.rightTextViewGrid.text = gridTextItemModel.text
            }
            this.binding.textInputLayoutGrid.visibility = View.GONE
        } else {
            this.binding.leftTextViewGrid.visibility = View.GONE
            this.binding.rightTextViewGrid.visibility = View.GONE
            this.binding.textInputLayoutGrid.visibility = View.VISIBLE
            this.binding.textInputLayoutGrid.setInputType(InputType.TYPE_CLASS_NUMBER)
            this.binding.textInputLayoutGrid.getTextInputEditTextComponent().isEnabled = gridTextItemModel.isEnabled
            this.binding.textInputLayoutGrid.isEnabled = gridTextItemModel.isEnabled
            if(gridTextItemModel.isEnabled) {
                gridTextItemModel.response =
                    this.binding.textInputLayoutGrid.getTextInputEditTextComponent().text
            } else {
                this.binding.textInputLayoutGrid.getTextInputEditTextComponent().setText(
                    gridTextItemModel.response.toString())
            }
        }
    }
}