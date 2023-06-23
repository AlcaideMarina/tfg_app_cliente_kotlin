package com.example.hueverianietoclientes.ui.components.hngridview

import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.hueverianietoclientes.databinding.ComponentGridTextBinding
import com.example.hueverianietoclientes.domain.model.GridTextItemModel

class HNGridTextViewHolder(view: View) :
    RecyclerView.ViewHolder(view) {

    private val binding: ComponentGridTextBinding = ComponentGridTextBinding.bind(view)

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
            this.binding.textInputLayoutGrid.inputType = InputType.TYPE_CLASS_NUMBER
            this.binding.textInputLayoutGrid.isEnabled = gridTextItemModel.isEnabled
            if (gridTextItemModel.response == null || gridTextItemModel.response == "") {
                gridTextItemModel.response =
                    this.binding.textInputLayoutGrid.text.toString()
            } else {
                this.binding.textInputLayoutGrid.setText(
                    gridTextItemModel.response.toString()
                )
            }
            this.binding.textInputLayoutGrid.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {}
                override fun beforeTextChanged(
                    s: CharSequence, start: Int,
                    count: Int, after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence, start: Int,
                    before: Int, count: Int
                ) {
                    gridTextItemModel.response = s.toString()
                }
            })
        }
    }

}
