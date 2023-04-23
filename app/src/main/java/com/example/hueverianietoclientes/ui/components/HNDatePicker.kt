package com.example.hueverianietoclientes.ui.components

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import com.example.hueverianietoclientes.R
import com.example.hueverianietoclientes.base.BaseComponent
import com.example.hueverianietoclientes.databinding.ComponentDatePickerBinding

class HNDatePicker : LinearLayout, BaseComponent {

    private val binding : ComponentDatePickerBinding = ComponentDatePickerBinding.bind(
        LayoutInflater
            .from(context)
            .inflate(R.layout.component_date_picker, this, true)
    )

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    )

    override fun getComponentContext(): Context {
        return context
    }

    override fun getView(): View {
        return this
    }

    fun setInputText(text: String) {
        this.binding.datePicker.setText(text)
    }

    fun setInputType(inputType: Int) {
        this.binding.datePicker.inputType = inputType
    }

    fun getDatePicker() : EditText {
        return this.binding.datePicker
    }

    fun setListener(listener: View.OnClickListener) {
        this.binding.datePicker.setOnClickListener(listener)
    }

}
